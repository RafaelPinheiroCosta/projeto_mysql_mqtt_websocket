package com.senai.model.dao.json;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.senai.model.Aluno;
import com.senai.model.Curso;
import com.senai.model.SubTurma;
import com.senai.model.Turma;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TurmaDAO {
    private final String caminho = "turmas.json";
    private final Gson gson = new Gson();
    private final List<Turma> turmas;

    public TurmaDAO(){
        turmas = carregar();
    }

    private List<Turma> carregar() {
        try (FileReader reader = new FileReader(caminho)) {
            Type listType = new TypeToken<List<Turma>>() {}.getType();
            return gson.fromJson(reader, listType);
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    private void salvar(List<Turma> lista) {
        try (FileWriter writer = new FileWriter(caminho)) {
            gson.toJson(lista, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void inserir(Turma turma) {
        int novoId = turmas.stream().mapToInt(Turma::getId).max().orElse(0) + 1;
        turma.setId(novoId);

        turma.setSubturmas(new ArrayList<>());

        SubTurma subTurma = new SubTurma();
        subTurma.setAlunos(new ArrayList<>());

        turma.getSubturmas().add(subTurma);
        turmas.add(turma);
        salvar(turmas);
    }

    public void atualizar(Turma turma) {
        for (int i = 0; i < turmas.size(); i++) {
            if (turmas.get(i).getId() == turma.getId()) {
                turmas.set(i, turma);
                break;
            }
        }
        salvar(turmas);
    }

    public void remover(int id) {
        turmas.removeIf(t -> t.getId() == id);
        salvar(turmas);
    }

    public Optional<Turma> buscarPorId(int id) {
        return turmas.stream().filter(t -> t.getId() == id).findFirst();
    }

    public Optional<Turma> buscarPorAluno(Aluno aluno) {
        return turmas.stream()
                .filter(t ->
                        t.getSubturmas().stream()
                                .anyMatch(subTurma ->
                                        subTurma.getAlunos().stream()
                                                .anyMatch(a -> a.equals(aluno))
                                )
                ).findFirst();
    }

    public List<Turma> listarTodos() {
        return turmas;
    }

    public void adicionarAlunosNaSubturma(SubTurma subTurma, List<Aluno> alunos) {
        turmas.stream()
                .filter(turma ->
                        turma.getSubturmas().contains(subTurma)
                ).findFirst().flatMap(
                        turma -> turma.getSubturmas().stream()
                        .filter(
                                s -> s.equals(subTurma)
                        ).findFirst()
                ).ifPresent(
                        s -> s.getAlunos().addAll(alunos)
                );
    }
}
