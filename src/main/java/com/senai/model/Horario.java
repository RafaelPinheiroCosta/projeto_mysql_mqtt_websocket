package com.senai.model;
import java.time.LocalTime;

public class Horario {
    private int id;
    private int idTurma;
    private int idProfessor;

    public Horario(int id, int idAluno, int idProfessor) {
        this.id = id;
        this.idTurma = idAluno;
        this.idProfessor = idProfessor;
    }

    public int getId() {
        return id;
    }

    public int getIdTurma() {
        return idTurma;
    }

    public int getIdProfessor() {
        return idProfessor;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setIdTurma(int idTurma) {
        this.idTurma = idTurma;
    }

    public void setIdProfessor(int idProfessor) {
        this.idProfessor = idProfessor;
    }

}
