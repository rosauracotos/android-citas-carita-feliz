package com.example.citascaritafeliz.model;

public class Medico {

    private Integer id;
    private String nombrecompleto;
    private String especialidad;
    private String dni;

    public Medico() {
    }

    public Medico(Integer id, String nombrecompleto, String especialidad, String dni) {
        this.id = id;
        this.nombrecompleto = nombrecompleto;
        this.especialidad = especialidad;
        this.dni = dni;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombrecompleto() {
        return nombrecompleto;
    }

    public void setNombrecompleto(String nombrecompleto) {
        this.nombrecompleto = nombrecompleto;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    @Override
    public String toString() {
        return "Medico{" +
                "id=" + id +
                ", nombrecompleto='" + nombrecompleto + '\'' +
                ", especialidad='" + especialidad + '\'' +
                ", dni='" + dni + '\'' +
                '}';
    }
}
