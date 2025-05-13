package com.senai.model.dao.json;

import com.google.gson.reflect.TypeToken;
import com.google.gson.Gson;
import com.senai.model.Professor;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

public class ProfessorDAO {
    private final String caminho = "professores.json";
    private final Gson gson = new Gson();

    private List<Professor> carregar() {
        try (FileReader reader = new FileReader(caminho)) {
            Type listType = new TypeToken<List<Professor>>() {}.getType();
            return gson.fromJson(reader, listType);
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    private void salvar(List<Professor> lista) {
        try (FileWriter writer = new FileWriter(caminho)) {
            gson.toJson(lista, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void inserir(Professor professor) {
        List<Professor> lista = carregar();
        int novoId = lista.stream().mapToInt(Professor::getId).max().orElse(0) + 1;
        professor.setId(novoId);
        lista.add(professor);
        salvar(lista);
    }

    public void atualizar(Professor professor) {
        List<Professor> lista = carregar();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == professor.getId()) {
                lista.set(i, professor);
                break;
            }
        }
        salvar(lista);
    }

    public void remover(int id) {
        List<Professor> lista = carregar();
        lista.removeIf(p -> p.getId() == id);
        salvar(lista);
    }

    public Optional<Professor> buscarPorId(int id) {
        return carregar().stream().filter(p -> p.getId() == id).findFirst();
    }

    public List<Professor> listarTodos() {
        return carregar();
    }
}

