package com.senai.model;

import java.time.LocalTime;

public class Curso {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;
    private LocalTime tolerancia;

    public int getTolerancia() {
        return tolerancia;
    }

    public void setTolerancia(LocalTime tolerancia) {
        this.tolerancia = tolerancia;
    }
}
