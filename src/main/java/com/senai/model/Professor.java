package com.senai.model;

public class Professor extends Usuario {
    private String disciplina;

    public Professor(int id, String nome, String disciplina, String login, String senha) {
        super(id, nome, login, senha);
        this.disciplina = disciplina;
    }

    public String getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }
    @Override
    public String getTipo() {
        return "Professor";
    }
}
