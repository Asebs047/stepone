-- RECREACIÓN DE BASE DE DATOS CORREGIDA

DROP DATABASE IF EXISTS DBStepOne;
CREATE DATABASE DBStepOne;
USE DBStepOne;

-- TABLAS PRINCIPALES
CREATE TABLE Zapatos(
    idZapato INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    marca VARCHAR(50) NOT NULL,
    talla DECIMAL(10,2) NOT NULL,
    color VARCHAR(32) NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    genero ENUM('Hombre', 'Mujer') NOT NULL,
    CONSTRAINT pk_zapatos PRIMARY KEY(idZapato)
);

CREATE TABLE Inventario(
    idZapato INT NOT NULL,
    cantidad INT NOT NULL,
    CONSTRAINT pk_inventario PRIMARY KEY(idZapato),
    CONSTRAINT fk_inventario_zapatos FOREIGN KEY(idZapato) REFERENCES Zapatos(idZapato)
);

CREATE TABLE IngresoInventario(
    idIngreso INT NOT NULL AUTO_INCREMENT,
    cantIngreso INT NOT NULL,
    fechaIngreso DATE NOT NULL,
    idZapato INT NOT NULL,
    CONSTRAINT pk_ingreso PRIMARY KEY(idIngreso),
    CONSTRAINT fk_ingreso_inventario FOREIGN KEY(idZapato) REFERENCES Inventario(idZapato)
);

CREATE TABLE Usuarios(
    idUsuario INT NOT NULL AUTO_INCREMENT,
    correo VARCHAR(64) NOT NULL,
    pass VARCHAR(64) NOT NULL,
    CONSTRAINT pk_usuario PRIMARY KEY(idUsuario)
);

CREATE TABLE Carritos(
    idCarrito INT NOT NULL AUTO_INCREMENT,
    idUsuario INT NOT NULL,
    total DECIMAL(10,2),
    CONSTRAINT pk_carrito PRIMARY KEY(idCarrito),
    CONSTRAINT fk_carritos_clientes FOREIGN KEY(idUsuario) REFERENCES Usuarios(idUsuario)
);

CREATE TABLE DetallesCarritos(
    idDetalle INT NOT NULL AUTO_INCREMENT,
    idCarrito INT NOT NULL,
    idZapato INT NOT NULL,
    cantidad INT NOT NULL,
    subTotal DECIMAL(10,2),
    CONSTRAINT pk_detalle PRIMARY KEY(idDetalle),
    CONSTRAINT fk_detalle_carritos FOREIGN KEY(idCarrito) REFERENCES Carritos(idCarrito),
    CONSTRAINT fk_detalle_zapatos FOREIGN KEY(idZapato) REFERENCES Zapatos(idZapato)
);

CREATE TABLE ConfirmarPedidos(
    idCarrito INT NOT NULL,
    fechaConfirmacion DATE NOT NULL,
    CONSTRAINT pk_confirmar PRIMARY KEY(idCarrito),
    CONSTRAINT fk_confirmar_carritos FOREIGN KEY(idCarrito) REFERENCES Carritos(idCarrito)
);

CREATE TABLE Facturas(
    idFactura INT NOT NULL AUTO_INCREMENT,
    idUsuario INT,
    idCarrito INT,
    fecha DATE,
    CONSTRAINT pk_factura PRIMARY KEY(idFactura),
    CONSTRAINT fk_factura_usuarios FOREIGN KEY(idUsuario) REFERENCES Usuarios(idUsuario),
    CONSTRAINT fk_factura_carritos FOREIGN KEY(idCarrito) REFERENCES Carritos(idCarrito)
);

CREATE TABLE Ventas(
    idVenta INT NOT NULL AUTO_INCREMENT,
    idFactura INT,
    CONSTRAINT pk_venta PRIMARY KEY(idVenta),
    CONSTRAINT fk_venta_factura FOREIGN KEY(idFactura) REFERENCES Facturas(idFactura)
);

-- INSERTS DE DATOS (NO SE REPITEN AQUÍ POR EXTENSIÓN)
-- Puedes copiar los INSERTS originales aquí sin cambios

-- PROCEDIMIENTOS ALMACENADOS

-- Ejemplos (uno por tipo)
DELIMITER //
CREATE PROCEDURE sp_InsertarZapato(
    IN p_nombre VARCHAR(100),
    IN p_marca VARCHAR(50),
    IN p_talla DECIMAL(10,2),
    IN p_color VARCHAR(32),
    IN p_precio DECIMAL(10,2),
    IN p_genero ENUM('Hombre', 'Mujer'))
BEGIN
    INSERT INTO Zapatos(nombre, marca, talla, color, precio, genero)
    VALUES(p_nombre, p_marca, p_talla, p_color, p_precio, p_genero);
END //

CREATE PROCEDURE sp_ActualizarZapato(
    IN p_id INT,
    IN p_nombre VARCHAR(100),
    IN p_marca VARCHAR(50),
    IN p_talla DECIMAL(10,2),
    IN p_color VARCHAR(32),
    IN p_precio DECIMAL(10,2),
    IN p_genero ENUM('Hombre', 'Mujer'))
BEGIN
    UPDATE Zapatos
    SET nombre = p_nombre,
        marca = p_marca,
        talla = p_talla,
        color = p_color,
        precio = p_precio,
        genero = p_genero
    WHERE idZapato = p_id;
END //

CREATE PROCEDURE sp_EliminarZapato(IN p_id INT)
BEGIN
    DELETE FROM Zapatos WHERE idZapato = p_id;
END //

-- Resto de procedimientos CRUD igual que tu script, usando nombres corregidos

-- SP_ConfirmarPedido (corregido)
CREATE PROCEDURE sp_ConfirmarPedido(IN p_idCarrito INT)
BEGIN
    DECLARE done INT DEFAULT 0;
    DECLARE v_idZapato INT;
    DECLARE v_cantidad INT;
    DECLARE v_stock INT;
    DECLARE v_mensaje VARCHAR(255);
    DECLARE v_idUsuario INT;
    DECLARE v_idFactura INT;

    DECLARE cur CURSOR FOR
        SELECT idZapato, cantidad FROM DetallesCarritos WHERE idCarrito = p_idCarrito;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;

    IF EXISTS (SELECT 1 FROM ConfirmarPedidos WHERE idCarrito = p_idCarrito) THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Error: Pedido ya confirmado.';
    END IF;

    OPEN cur;
    loop_fetch: LOOP
        FETCH cur INTO v_idZapato, v_cantidad;
        IF done THEN LEAVE loop_fetch; END IF;
        SELECT cantidad INTO v_stock FROM Inventario WHERE idZapato = v_idZapato;
        IF v_stock < v_cantidad THEN
            SET v_mensaje = CONCAT('Error: Stock insuficiente para zapato ID ', v_idZapato);
            SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = v_mensaje;
        END IF;
    END LOOP;
    CLOSE cur;

    INSERT INTO ConfirmarPedidos(idCarrito, fechaConfirmacion)
    VALUES(p_idCarrito, CURDATE());

    SET done = 0;
    OPEN cur;
    loop_fetch2: LOOP
        FETCH cur INTO v_idZapato, v_cantidad;
        IF done THEN LEAVE loop_fetch2; END IF;
        UPDATE Inventario SET cantidad = cantidad - v_cantidad WHERE idZapato = v_idZapato;
    END LOOP;
    CLOSE cur;

    SELECT idUsuario INTO v_idUsuario FROM Carritos WHERE idCarrito = p_idCarrito;
    INSERT INTO Facturas(idUsuario, idCarrito, fecha) VALUES(v_idUsuario, p_idCarrito, CURDATE());
    SET v_idFactura = LAST_INSERT_ID();
    INSERT INTO Ventas(idFactura) VALUES(v_idFactura);
END //
DELIMITER ;

DELIMITER //

-- Trigger antes de insertar en DetallesCarritos para calcular subTotal
CREATE TRIGGER trg_before_insert_detalle
BEFORE INSERT ON DetallesCarritos
FOR EACH ROW
BEGIN
  DECLARE v_precio DECIMAL(10,2);
  SELECT precio INTO v_precio FROM Zapatos WHERE idZapato = NEW.idZapato;
  SET NEW.subTotal = v_precio * NEW.cantidad;
END;
//

-- Trigger antes de actualizar en DetallesCarritos para recalcular subTotal
CREATE TRIGGER trg_before_update_detalle
BEFORE UPDATE ON DetallesCarritos
FOR EACH ROW
BEGIN
  DECLARE v_precio DECIMAL(10,2);
  SELECT precio INTO v_precio FROM Zapatos WHERE idZapato = NEW.idZapato;
  SET NEW.subTotal = v_precio * NEW.cantidad;
END;
//

-- Trigger después de insertar o actualizar en DetallesCarritos para actualizar total del carrito
CREATE TRIGGER trg_after_insert_detalle
AFTER INSERT ON DetallesCarritos
FOR EACH ROW
BEGIN
  UPDATE Carritos
  SET total = (SELECT IFNULL(SUM(subTotal), 0) FROM DetallesCarritos WHERE idCarrito = NEW.idCarrito)
  WHERE idCarrito = NEW.idCarrito;
END;
//

CREATE TRIGGER trg_after_update_detalle
AFTER UPDATE ON DetallesCarritos
FOR EACH ROW
BEGIN
  UPDATE Carritos
  SET total = (SELECT IFNULL(SUM(subTotal), 0) FROM DetallesCarritos WHERE idCarrito = NEW.idCarrito)
  WHERE idCarrito = NEW.idCarrito;
END;
//

-- Trigger después de borrar detalle para actualizar total del carrito
CREATE TRIGGER trg_after_delete_detalle
AFTER DELETE ON DetallesCarritos
FOR EACH ROW
BEGIN
  UPDATE Carritos
  SET total = (SELECT IFNULL(SUM(subTotal), 0) FROM DetallesCarritos WHERE idCarrito = OLD.idCarrito)
  WHERE idCarrito = OLD.idCarrito;
END;
//

DELIMITER ;


INSERT INTO Zapatos (nombre, marca, talla, color, precio, genero) VALUES
('Zapatos deportivos Courtside para hombre', 'Nike', 38.5, 'Negro', 340.00, 'Hombre'),
('Zapatos deportivos Quixstep para hombre', 'Adidas', 40.0, 'Blanco', 425.00, 'Hombre'), 
('Zapatos casuales Stitch tipo Oxford para hombre', 'Puma', 36.5, 'Rojo', 415.00, 'Hombre'), 
('Zapatos deportivos Trekker para hombre', 'Reebok', 37.0, 'Azul', 330.00, 'Hombre'), 
('Zapatos deportivos Concur XP para hombre', 'Vans', 39.0, 'Gris', 425.00, 'Hombre'), 
('Zapatos deportivos Command para hombre', 'Nike', 41.0, 'Verde', 370.00, 'Hombre'), 
('Zapatos de vestir Taunton para hombre', 'Adidas', 40.5, 'Negro', 415.00, 'Hombre'), 
('Sandalias DiamondBlack para hombre', 'Puma', 38.0, 'Blanco', 325.00, 'Hombre'), 
('Zapatos deportivos Ambrose para hombre', 'Reebok', 36.0, 'Rojo', 460.00, 'Mujer'), 
('Zapatos deportivos Command para mujer', 'Vans', 42.0, 'Azul', 370.00, 'Mujer'), 
('Zapatos deportivos Crossspeed para hombre', 'Nike', 37.5, 'Gris', 495.00, 'Hombre'), 
('Zapatos casuales tipo mocasín para hombre', 'Adidas', 39.5, 'Verde', 415.00, 'Hombre'),
('Zapatos deportivos para mujer', 'Puma', 38.5, 'Negro', 440.00, 'Mujer'), 
('Sandalias para hombre', 'Reebok', 40.0, 'Blanco', 235.50, 'Hombre'), 
('Tenis Dazzle Sport para mujer', 'Vans', 36.0, 'Rojo', 200.00, 'Mujer'), 
('Zapatos deportivos Command para mujer', 'Nike', 41.5, 'Azul', 370.00, 'Mujer'), 
('Sandalias Ryan con plataforma tipo cuña para mujer', 'Adidas', 37.0, 'Gris', 280.00, 'Mujer'), 
('Sandalias Herdon para mujer', 'Puma', 42.0, 'Verde', 130.00, 'Mujer'), 
('Sandalias Felicia con tacón tipo cuña para mujer', 'Reebok', 39.0, 'Negro', 220.00, 'Mujer'), 
('Botines Dierks con cordones para mujer', 'Vans', 38.0, 'Blanco', 200.00, 'Mujer'), 
('Zapatos deportivos Rush para mujer', 'Nike', 36.5, 'Rojo', 230.00, 'Mujer'), 
('Botines Rylan para mujer', 'Adidas', 40.0, 'Azul', 250.00, 'Mujer'), 
('Zapatos deportivos Stance para mujer', 'Puma', 37.5, 'Gris', 300.00, 'Mujer'), 
('Botines Prance para mujer', 'Reebok', 41.0, 'Verde', 300.00, 'Mujer'),
('Zapatos casuales Rudy tipo mocasín para mujer', 'Vans', 39.5, 'Negro', 300.00, 'Mujer'), 
('Sandalias planas Zeal para mujer', 'Nike', 38.0, 'Blanco', 325.00, 'Mujer'), 
('Sandalias Riley con taco tipo cuña para mujer', 'Adidas', 40.5, 'Rojo', 325.00, 'Mujer'), 
('Sandalias Riley con taco tipo cuña para mujer', 'Puma', 36.5, 'Azul', 325.00, 'Mujer'),
('Sandalias Abe para hombre', 'Reebok', 42.0, 'Gris', 310.00, 'Mujer'), 
('Zapatos casuales Naples tipo Oxford para hombre', 'Vans', 37.0, 'Verde', 675.00, 'Hombre'); 

INSERT INTO Inventario (idZapato, cantidad) VALUES
(1, 20), 
(2, 15), 
(3, 30), 
(4, 10), 
(5, 25),
(6, 8), 
(7, 18), 
(8, 12), 
(9, 16), 
(10, 5),
(11, 22), 
(12, 13), 
(13, 7), 
(14, 9), 
(15, 14),
(16, 6), 
(17, 11), 
(18, 17), 
(19, 23), 
(20, 4),
(21, 21), 
(22, 19), 
(23, 3), 
(24, 2), 
(25, 26),
(26, 29), 
(27, 24), 
(28, 1), 
(29, 27), 
(30, 20);

INSERT INTO IngresoInventario (cantIngreso, fechaIngreso, idZapato) VALUES
(10, '2024-01-01', 1), 
(15, '2024-01-02', 2), 
(8, '2024-01-03', 3),
(12, '2024-01-04', 4), 
(6, '2024-01-05', 5), 
(20, '2024-01-06', 6),
(7, '2024-01-07', 7), 
(14, '2024-01-08', 8), 
(9, '2024-01-09', 9),
(13, '2024-01-10', 10), 
(11, '2024-01-11', 11), 
(18, '2024-01-12', 12),
(5, '2024-01-13', 13), 
(16, '2024-01-14', 14), 
(4, '2024-01-15', 15),
(10, '2024-01-16', 16), 
(15, '2024-01-17', 17), 
(8, '2024-01-18', 18),
(12, '2024-01-19', 19), 
(6, '2024-01-20', 20), 
(20, '2024-01-21', 21),
(7, '2024-01-22', 22), 
(14, '2024-01-23', 23), 
(9, '2024-01-24', 24),
(13, '2024-01-25', 25), 
(11, '2024-01-26', 26), 
(18, '2024-01-27', 27),
(5, '2024-01-28', 28), 
(16, '2024-01-29', 29), 
(4, '2024-01-30', 30);

-- Insertar Usuarios
INSERT INTO Usuarios (correo, pass) VALUES
('juan.perez@email.com', 'juan123'),
('maria.lopez@email.com', 'maria123'),
('carlos.ramirez@email.com', 'carlos123');

-- Insertar Carritos (cada usuario tendrá un carrito)
INSERT INTO Carritos (idUsuario, total) VALUES
(1, 0), -- Juan
(2, 0), -- Maria
(3, 0); -- Carlos

-- Insertar DetallesCarritos con más de un producto en cada carrito

-- Carrito 1 (Juan) - 2 productos
INSERT INTO DetallesCarritos (idCarrito, idZapato, cantidad) VALUES
(1, 1, 2),  -- 2 pares de Zapatos id=1
(1, 5, 1);  -- 1 par de Zapatos id=5

-- Carrito 2 (Maria) - 3 productos
INSERT INTO DetallesCarritos (idCarrito, idZapato, cantidad) VALUES
(2, 10, 1),
(2, 15, 2),
(2, 25, 1);

-- Carrito 3 (Carlos) - 2 productos
INSERT INTO DetallesCarritos (idCarrito, idZapato, cantidad) VALUES
(3, 3, 1),
(3, 8, 3);

-- Confirmar Pedidos para los carritos 1 y 2
CALL sp_ConfirmarPedido(1);

SELECT * FROM Facturas;

SELECT * FROM DetallesCarritos WHERE idCarrito = 1;

SELECT idZapato, cantidad FROM Inventario WHERE idZapato IN (
  SELECT idZapato FROM DetallesCarritos WHERE idCarrito = 1
);
