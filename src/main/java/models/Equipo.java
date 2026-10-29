package models;

public class Equipo {
    private Long id;
    private String nombre;
    private String categoria;
    private Boolean disponible;

    public Equipo() {
    }

    public Equipo(String nombre, String categoria, Boolean disponible) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.disponible = disponible;
    }

    public Equipo(Long id, String nombre, String categoria, Boolean disponible) {
        this(nombre, categoria, disponible);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Boolean getDisponible() {
        return disponible;
    }

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }

    @Override
    public String toString() {
        return "Equipo{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", categoria='" + categoria + '\'' +
                ", disponible=" + disponible +
                '}';
    }
}