package luis.ipvg.certamennota2.Clases;

import java.time.LocalDateTime;

public class FormGeo {

    private String idForm;
    private String titulo; //obligatorio
    private String descripcion;
    private String categoria; //obligatorio
    private LocalDateTime formDateTime;
    private Double longitud;
    private Double latitud;

    public FormGeo() {
        this.descripcion = "-";

    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public LocalDateTime getFormDateTime() {
        return formDateTime;
    }

    public void setFormDateTime(LocalDateTime formDateTime) {
        this.formDateTime = formDateTime;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public String getIdForm() {
        return idForm;
    }

    public void setIdForm(String idForm) {
        this.idForm = idForm;
    }

    @Override
    public String toString() {
        return "FormGeo{" +
                "idForm='" + idForm + '\'' +
                ", titulo='" + titulo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", categoria='" + categoria + '\'' +
                ", formDateTime=" + formDateTime +
                ", longitud=" + longitud +
                ", latitud=" + latitud +
                '}';
    }
}
