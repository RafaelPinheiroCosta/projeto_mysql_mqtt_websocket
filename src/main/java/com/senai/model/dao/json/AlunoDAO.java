package com.senai.model.dao.json;

import com.google.gson.reflect.TypeToken;
import com.google.gson.Gson;
import com.senai.model.Aluno;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

public class AlunoDAO {
    private final String caminho = "alunos.json";
    private final Gson gson = new Gson();

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
        List<Aluno> lista = carregar();
        int novoId = lista.stream().mapToInt(Aluno::getId).max().orElse(0) + 1;
        aluno.setId(novoId);
        lista.add(aluno);
        salvar(lista);
    }

    public void atualizar(Aluno aluno) {
        List<Aluno> lista = carregar();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == aluno.getId()) {
                lista.set(i, aluno);
                break;
            }
        }
        salvar(lista);
    }

    public void remover(int id) {
        List<Aluno> lista = carregar();
        lista.removeIf(a -> a.getId() == id);
        salvar(lista);
    }

    public Optional<Aluno> buscarPorId(int id) {
        return carregar().stream().filter(a -> a.getId() == id).findFirst();
    }

    public Optional<Aluno> buscarPorRfid(String rfid) {
        return carregar().stream().filter(a -> rfid.equals(a.getIdCartaoRfid())).findFirst();
    }

    public List<Aluno> listarTodos() {
        return carregar();
    }
}

