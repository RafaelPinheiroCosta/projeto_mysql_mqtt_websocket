package com.senai.model.dao.json;

import com.google.gson.reflect.TypeToken;
import com.google.gson.Gson;
import com.senai.model.Administrador;
import com.senai.model.Aluno;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

public class AlunoDAO {
    private final String caminho = "alunos.json";
    private final Gson gson = new Gson();
    private final List<Aluno> alunos;

    public AlunoDAO(){
        alunos = carregar();
    }

    private List<Aluno> carregar() {
        try (FileReader reader = new FileReader(caminho)) {
            Type listType = new TypeToken<List<Aluno>>() {}.getType();
            return gson.fromJson(reader, listType);
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    private void salvar(List<Aluno> lista) {
        try (FileWriter writer = new FileWriter(caminho)) {
            gson.toJson(lista, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void inserir(Aluno aluno) {
        int novoId = alunos.stream().mapToInt(Aluno::getId).max().orElse(0) + 1;
        aluno.setId(novoId);
        alunos.add(aluno);
        salvar(alunos);
    }

    public void atualizar(Aluno aluno) {
        for (int i = 0; i < alunos.size(); i++) {
            if (alunos.get(i).getId() == aluno.getId()) {
                alunos.set(i, aluno);
                break;
            }
        }
        salvar(alunos);
    }

    public void remover(int id) {
        alunos.removeIf(a -> a.getId() == id);
        salvar(alunos);
    }

    public Optional<Aluno> buscarPorId(int id) {
        return alunos.stream().filter(a -> a.getId() == id).findFirst();
    }

    public Optional<Aluno> buscarPorRfid(String rfid) {
        return alunos.stream().filter(a -> rfid.equals(a.getIdCartaoRfid())).findFirst();
    }
    public Optional<Aluno> buscarPorLogin(String login) {
        return alunos.stream().filter(a -> a.getLogin().equals(login)).findFirst();
    }

    public List<Aluno> listarTodos() {
        return alunos;
    }
}

