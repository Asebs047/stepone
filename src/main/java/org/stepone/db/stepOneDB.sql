drop database if exists DBStepOne;
create database DBStepOne;
use DBStepOne;					

-- TABLAS

create table Zapatos(
	idZapato int not null auto_increment,
    nombre varchar(100) not null,
    descripcion text not null,
    marca varchar(50) not null,
    talla decimal(10,2) not null,
    color varchar(32) not null,
    precio decimal(10,2) not null,
    genero enum('Hombre', 'Mujer', 'Niño', 'Niña', 'Unisex') not null,
    imagen_url varchar(255) not null,
    constraint pk_zapatos primary key(idZapato)
);

create table Inventario(
	idZapato int not null,
    cantidadStok int not null,
    constraint pk_inventario primary key(idZapato),
    constraint fk_inventario_zapatos foreign key(idZapato)
		references Zapatos(idZapato)
);

create table IngresoInventario(
	idIngreso int not null auto_increment,
    cantIngreso int not null,
    fechaIngreso date not null,
    idZapato int not null,
    constraint pk_ingreso primary key(idIngreso),
    constraint fk_ingreso_inventario foreign key(idZapato)
		references Inventario(idZapato)
);

create table Usuarios(
	idUsuario int not null auto_increment,
    correo varchar(64) not null,
    pass varchar(64) not null,
    tipo enum('admin','user'),
    constraint pk_usuario primary key(idUsuario)
);

create table Carritos(
	idCarrito int not null auto_increment,
    idUsuario int not null,
    total decimal(10,2),
    constraint pk_carrito primary key(idCarrito),
    constraint fk_carritos_clientes foreign key(idUsuario)
		references Usuarios(idUsuario)
);

create table DetallesCarritos(
	idDetalle int not null auto_increment,
    idCarrito int,
    idZapato int,
    cantidad int not null,
    subTotal decimal(10,2),
    constraint pk_detalle primary key(idDetalle),
    constraint fk_detalle_carritos foreign key(idCarrito)
		references Carritos(idCarrito),
	constraint fk_detalle_zapatos foreign key(idZapato)
		references Zapatos(idZapato)
);

create table ConfrmarPedidos(
	idCarrito int,
	constraint pk_confirmar primary key(idCarrito),
    constraint fk_confirmar_carritos foreign key(idCarrito)
		references Carritos(idCarrito),
	constraint uq_confirmar_carrito unique (idCarrito)
);

create table Facturas(
	idFactura int not null auto_increment,
    idUsuario int, 
    idCarrito int,
    fecha date,
    constraint pk_factura primary key(idFactura),
    constraint fk_factura_usuarios foreign key(idUsuario)
		references Usuarios(idUsuario),
	constraint fk_factura_carritos foreign key(idCarrito)
		references Carritos(idCarrito)
);

create table Ventas(
	idVenta int not null auto_increment,
    idFactura int,
    constraint pk_venta primary key(idVenta),
    constraint fk_venta_factura foreign key(idFactura)
		references Facturas(idFactura)
);


-- PROCEDIMIENTOS ALMACENADOS (CRUD simplificados, sin parámetros para campos autogenerados)

-- Zapatos
delimiter //
create procedure sp_InsertarZapato(
    in p_nombre varchar(100),
    in p_descripcion text,
    in p_marca varchar(50),
    in p_talla decimal(10,2),
    in p_color varchar(32),
    in p_precio decimal(10,2),
    in p_genero enum('Hombre', 'Mujer', 'Niño', 'Niña', 'Unisex'),
    in p_imagen_url varchar(255))
begin
    insert into Zapatos(nombre, descripcion, marca, talla, color, precio, genero, imagen_url)
    values(p_nombre, p_descripcion, p_marca, p_talla, p_color, p_precio, p_genero, p_imagen_url);
end //
delimiter ;

delimiter //
create procedure sp_ActualizarZapato(
    in p_id int,
    in p_nombre varchar(100),
    in p_descripcion text,
    in p_marca varchar(50),
    in p_talla decimal(10,2),
    in p_color varchar(32),
    in p_precio decimal(10,2),
    in p_genero enum('Hombre', 'Mujer', 'Niño', 'Niña', 'Unisex'),
    in p_imagen_url varchar(255))
begin
    update Zapatos
    set nombre = p_nombre,
        descripcion = p_descripcion,
        marca = p_marca,
        talla = p_talla,
        color = p_color,
        precio = p_precio,
        genero = p_genero,
        imagen_url = p_imagen_url
    where idZapato = p_id;
end //
delimiter ;

delimiter //
create procedure sp_EliminarZapato(in p_id int)
begin
    delete from Zapatos where idZapato = p_id;
end //
delimiter ;

-- Inventario
delimiter //
create procedure sp_InsertarInventario(
    in p_idZapato int,
    in p_cantidadStok int)
begin
    insert into Inventario(idZapato, cantidadStok)
    values(p_idZapato, p_cantidadStok);
end //
delimiter ;

delimiter //
create procedure sp_ActualizarInventario(
    in p_idZapato int,
    in p_cantidadStok int)
begin
    update Inventario
    set cantidadStok = p_cantidadStok
    where idZapato = p_idZapato;
end //
delimiter ;

delimiter //
create procedure sp_EliminarInventario(in p_idZapato int)
begin
    delete from Inventario where idZapato = p_idZapato;
end //
delimiter ;

-- IngresoInventario
delimiter //
create procedure sp_InsertarIngresoInventario(
    in p_cantIngreso int,
    in p_fechaIngreso date,
    in p_idZapato int)
begin
    insert into IngresoInventario(cantIngreso, fechaIngreso, idZapato)
    values(p_cantIngreso, p_fechaIngreso, p_idZapato);
end //
delimiter ;

delimiter //
create procedure sp_ActualizarIngresoInventario(
    in p_idIngreso int,
    in p_cantIngreso int,
    in p_fechaIngreso date,
    in p_idZapato int)
begin
    update IngresoInventario
    set cantIngreso = p_cantIngreso,
        fechaIngreso = p_fechaIngreso,
        idZapato = p_idZapato
    where idIngreso = p_idIngreso;
end //
delimiter ;

delimiter //
create procedure sp_EliminarIngresoInventario(in p_idIngreso int)
begin
    delete from IngresoInventario where idIngreso = p_idIngreso;
end //
delimiter ;

-- Usuarios
delimiter //
create procedure sp_InsertarUsuario(
    in p_nombre varchar(32),
    in p_apellido varchar(32),
    in p_correo varchar(64),
    in p_pass varchar(64),
    in p_tipo enum('admin','user'),
    in p_nunero int,
    in p_direccion text)
begin
    insert into Usuarios(nombre, apellido, correo, pass, tipo, nunero, direccion)
    values(p_nombre, p_apellido, p_correo, p_pass, p_tipo, p_nunero, p_direccion);
end //
delimiter ;

delimiter //
create procedure sp_ActualizarUsuario(
    in p_id int,
    in p_nombre varchar(32),
    in p_apellido varchar(32),
    in p_correo varchar(64),
    in p_pass varchar(64),
    in p_tipo enum('admin','user'),
    in p_nunero int,
    in p_direccion text)
begin
    update Usuarios
    set nombre = p_nombre,
        apellido = p_apellido,
        correo = p_correo,
        pass = p_pass,
        tipo = p_tipo,
        nunero = p_nunero,
        direccion = p_direccion
    where idUsuario = p_id;
end //
delimiter ;

delimiter //
create procedure sp_EliminarUsuario(in p_id int)
begin
    delete from Usuarios where idUsuario = p_id;
end //
delimiter ;

-- Carritos
delimiter //
create procedure sp_InsertarCarrito(
    in p_idUsuario int)
begin
    insert into Carritos(idUsuario, total)
    values(p_idUsuario, 0);
end //
delimiter ;

delimiter //
create procedure sp_ActualizarCarrito(
    in p_idCarrito int,
    in p_idUsuario int)
begin
    update Carritos
    set idUsuario = p_idUsuario
    where idCarrito = p_idCarrito;
end //
delimiter ;

delimiter //
create procedure sp_EliminarCarrito(in p_idCarrito int)
begin
    delete from Carritos where idCarrito = p_idCarrito;
end //
delimiter ;

-- DetallesCarritos
delimiter //
create procedure sp_InsertarDetalleCarrito(
    in p_idCarrito int,
    in p_idZapato int,
    in p_cantidad int)
begin
    insert into DetallesCarritos(idCarrito, idZapato, cantidad)
    values(p_idCarrito, p_idZapato, p_cantidad);
end //
delimiter ;

delimiter //
create procedure sp_ActualizarDetalleCarrito(
    in p_idDetalle int,
    in p_idCarrito int,
    in p_idZapato int,
    in p_cantidad int)
begin
    update DetallesCarritos
    set idCarrito = p_idCarrito,
        idZapato = p_idZapato,
        cantidad = p_cantidad
    where idDetalle = p_idDetalle;
end //
delimiter ;

delimiter //
create procedure sp_EliminarDetalleCarrito(in p_idDetalle int)
begin
    delete from DetallesCarritos where idDetalle = p_idDetalle;
end //
delimiter ;

-- ConfirmarPedidos
delimiter //
create procedure sp_InsertarConfirmarPedido(
    in p_idCarrito int)
begin
    insert into ConfrmarPedidos(idCarrito)
    values(p_idCarrito);
end //
delimiter ;

delimiter //
create procedure sp_EliminarConfirmarPedido(in p_idCarrito int)
begin
    delete from ConfrmarPedidos where idCarrito = p_idCarrito;
end //
delimiter ;

-- Facturas
delimiter //
create procedure sp_InsertarFactura(
    in p_idUsuario int,
    in p_idCarrito int)
begin
    insert into Facturas(idUsuario, idCarrito, fecha)
    values(p_idUsuario, p_idCarrito, CURDATE());
end //
delimiter ;

delimiter //
create procedure sp_ActualizarFactura(
    in p_idFactura int,
    in p_idUsuario int,
    in p_idCarrito int,
    in p_fecha date)
begin
    update Facturas
    set idUsuario = p_idUsuario,
        idCarrito = p_idCarrito,
        fecha = p_fecha
    where idFactura = p_idFactura;
end //
delimiter ;

delimiter //
create procedure sp_EliminarFactura(in p_idFactura int)
begin
    delete from Facturas where idFactura = p_idFactura;
end //
delimiter ;

-- Ventas
delimiter //
create procedure sp_InsertarVenta(
    in p_idFactura int)
begin
    insert into Ventas(idFactura)
    values(p_idFactura);
end //
delimiter ;

delimiter //
create procedure sp_EliminarVenta(in p_idVenta int)
begin
    delete from Ventas where idVenta = p_idVenta;
end //
delimiter ;


-- TRIGGERS

-- 1. Trigger para actualizar o insertar inventario al ingresar nuevo ingreso inventario
delimiter //
create trigger tr_actualizar_o_insertar_stock_ingreso
after insert on IngresoInventario
for each row
begin
    if exists (select 1 from Inventario where idZapato = NEW.idZapato) then
        update Inventario
        set cantidadStok = cantidadStok + NEW.cantIngreso
        where idZapato = NEW.idZapato;
    else
        insert into Inventario(idZapato, cantidadStok)
        values (NEW.idZapato, NEW.cantIngreso);
    end if;
end //
delimiter ;

-- 2. Trigger para calcular subTotal antes de insertar DetalleCarrito
delimiter //
create trigger tr_calcular_subtotal
before insert on DetallesCarritos
for each row
begin
    declare v_precio decimal(10,2);

    select precio into v_precio
    from Zapatos
    where idZapato = NEW.idZapato;

    set NEW.subTotal = v_precio * NEW.cantidad;
end //
delimiter ;

-- 3. Trigger para calcular subTotal antes de actualizar DetalleCarrito
delimiter //
create trigger tr_calcular_subtotal_update
before update on DetallesCarritos
for each row
begin
    declare v_precio decimal(10,2);

    select precio into v_precio
    from Zapatos
    where idZapato = NEW.idZapato;

    set NEW.subTotal = v_precio * NEW.cantidad;
end //
delimiter ;

-- 4. Trigger para actualizar total en Carritos después de insertar DetalleCarrito
delimiter //
create trigger tr_actualizar_total_carrito
after insert on DetallesCarritos
for each row
begin
    declare v_total decimal(10,2);

    select sum(subTotal) into v_total
    from DetallesCarritos
    where idCarrito = NEW.idCarrito;

    update Carritos
    set total = v_total
    where idCarrito = NEW.idCarrito;
end //
delimiter ;

-- 5. Trigger para actualizar total en Carritos después de actualizar DetalleCarrito
delimiter //
create trigger tr_actualizar_total_carrito_update
after update on DetallesCarritos
for each row
begin
    declare v_total decimal(10,2);
    
    select sum(subTotal) into v_total
    from DetallesCarritos
    where idCarrito = NEW.idCarrito;

    update Carritos
    set total = v_total
    where idCarrito = NEW.idCarrito;
end //
delimiter ;

-- 6. Trigger para actualizar total en Carritos después de eliminar DetalleCarrito
delimiter //
create trigger tr_actualizar_total_carrito_delete
after delete on DetallesCarritos
for each row
begin
    declare v_total decimal(10,2);

    select ifnull(sum(subTotal), 0) into v_total
    from DetallesCarritos
    where idCarrito = OLD.idCarrito;

    update Carritos
    set total = v_total
    where idCarrito = OLD.idCarrito;
end //
delimiter ;

delimiter //
create procedure sp_ConfirmarPedido(
    in p_idCarrito int
)
begin
    declare done int default 0;
    declare v_idZapato int;
    declare v_cantidad int;
    declare v_stock int;
    declare v_mensaje varchar(255);
    declare v_idUsuario int;
    declare v_idFactura int;
    
    -- Cursor para recorrer detalles del carrito
    declare cur cursor for
        select idZapato, cantidad
        from DetallesCarritos
        where idCarrito = p_idCarrito;
    
    declare continue handler for not found set done = 1;

    -- Validar si pedido ya fue confirmado
    if exists (select 1 from ConfrmarPedidos where idCarrito = p_idCarrito) then
        signal sqlstate '45000' set message_text = 'Error: Pedido ya confirmado.';
    end if;

    open cur;

    -- Validar stock disponible
    loop_fetch: loop
        fetch cur into v_idZapato, v_cantidad;
        if done then
            leave loop_fetch;
        end if;

        select cantidadStok into v_stock from Inventario where idZapato = v_idZapato;

        if v_stock < v_cantidad then
            set v_mensaje = concat('Error: Stock insuficiente para zapato ID ', v_idZapato, '. Stock actual: ', v_stock, ', requerido: ', v_cantidad);
            signal sqlstate '45000' set message_text = v_mensaje;
        end if;
    end loop;

    close cur;

    -- Insertar confirmación de pedido
    insert into ConfrmarPedidos(idCarrito) values(p_idCarrito);

    -- Reducir stock
    set done = 0;
    open cur;

    loop_fetch2: loop
        fetch cur into v_idZapato, v_cantidad;
        if done then
            leave loop_fetch2;
        end if;

        update Inventario
        set cantidadStok = cantidadStok - v_cantidad
        where idZapato = v_idZapato;
    end loop;

    close cur;

    -- Crear factura y venta

    select idUsuario into v_idUsuario from Carritos where idCarrito = p_idCarrito;

    insert into Facturas(idUsuario, idCarrito, fecha) values(v_idUsuario, p_idCarrito, CURDATE());
    set v_idFactura = last_insert_id();

    insert into Ventas(idFactura) values(v_idFactura);

end //
delimiter ;
