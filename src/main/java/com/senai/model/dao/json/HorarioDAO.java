package com.senai.model.dao.json;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.Gson;
import com.senai.model.Horario;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalTime;
import java.util.*;

public class HorarioDAO {
    private final String caminho = "horarios.json";
    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalTime.class, new LocalTimeAdapter())
            .create();

    private List<Horario> carregar() {
        try (FileReader reader = new FileReader(caminho)) {
            Type listType = new TypeToken<List<Horario>>() {}.getType();
            return gson.fromJson(reader, listType);
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    private void salvar(List<Horario> lista) {
        try (FileWriter writer = new FileWriter(caminho)) {
            gson.toJson(lista, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void inserir(Horario horario) {
        List<Horario> lista = carregar();
        int novoId = lista.stream().mapToInt(Horario::getId).max().orElse(0) + 1;
        horario.setId(novoId);
        lista.add(horario);
        salvar(lista);
    }

    public void atualizar(Horario horario) {
        List<Horario> lista = carregar();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == horario.getId()) {
                lista.set(i, horario);
                break;
            }
        }
        salvar(lista);
    }

    public void remover(int id) {
        List<Horario> lista = carregar();
        lista.removeIf(h -> h.getId() == id);
        salvar(lista);
    }

    public Optional<Horario> buscarHorarioDoAluno(int idAluno) {
        return carregar().stream().filter(h -> h.getIdAluno() == idAluno).findFirst();
    }

    public List<Horario> listarTodos() {
        return carregar();
    }
}

