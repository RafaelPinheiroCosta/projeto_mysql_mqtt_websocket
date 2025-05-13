package com.senai.model;
import java.time.LocalTime;

public class Horario {
    private int id;
    private int idAluno;
    private int idProfessor;
    private LocalTime horaInicio;

    public Horario(int id, int idAluno, int idProfessor, LocalTime horaInicio) {
        this.id = id;
        this.idAluno = idAluno;
        this.idProfessor = idProfessor;
        this.horaInicio = horaInicio;
    }

    public int getId() {
        return id;
    }

    public int getIdAluno() {
        return idAluno;
    }

    public int getIdProfessor() {
        return idProfessor;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdAluno(int idAluno) {
        this.idAluno = idAluno;
    }

    public void setIdProfessor(int idProfessor) {
        this.idProfessor = idProfessor;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }
}
