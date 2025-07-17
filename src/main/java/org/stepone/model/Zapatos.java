
package org.stepone.model;

/**
 *
 * @author Klopez
 */
public class Zapatos {
    private int id;
    private String nombre;
    private String marca;
    private Double talla;
    private String color;
    private Double Precio;
    private String genero;

    public Zapatos(String nombre, String marca, Double talla, String color, Double Precio, String genero) {
        this.nombre = nombre;
        this.marca = marca;
        this.talla = talla;
        this.color = color;
        this.Precio = Precio;
        this.genero = genero;
    }

    public Zapatos(int id, String nombre, String marca, Double talla, String color, Double Precio, String genero) {
        this.id = id;
        this.nombre = nombre;
        this.marca = marca;
        this.talla = talla;
        this.color = color;
        this.Precio = Precio;
        this.genero = genero;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Double getTalla() {
        return talla;
    }

    public void setTalla(Double talla) {
        this.talla = talla;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Double getPrecio() {
        return Precio;
    }

    public void setPrecio(Double Precio) {
        this.Precio = Precio;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }
    
    

    
    
}
