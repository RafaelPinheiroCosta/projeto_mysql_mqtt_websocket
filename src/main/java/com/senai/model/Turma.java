package com.senai.model;

import java.time.LocalTime;
import java.util.List;

public class Turma {
    private int id;
    private List<SubTurma> subturmas;
    private LocalTime horarioEntrada;
    private Curso curso;

    public List<SubTurma> getSubturmas() {
        return subturmas;
    }

    public void setSubturmas(List<SubTurma> subturmas) {
        this.subturmas = subturmas;
    }

    public LocalTime getHorarioEntrada() {
        return horarioEntrada;
    }

    public void setHorarioEntrada(LocalTime horarioEntrada) {
        this.horarioEntrada = horarioEntrada;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
