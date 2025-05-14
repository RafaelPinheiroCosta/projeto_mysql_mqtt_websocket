package com.senai.controller;

import com.senai.model.dao.json.AlunoDAO;
import com.senai.model.dao.json.ProfessorDAO;
import com.senai.model.Aluno;
import com.senai.model.Professor;
import com.senai.util.CriptografiaUtil;

import java.util.List;

public class UsuarioController {
    private final AlunoDAO alunoDAO = new AlunoDAO();
    private final ProfessorDAO professorDAO = new ProfessorDAO();

    public String cadastrarUsuario(String tipo, String nome, String dadoExtra, String login, String senha) {
        if (tipo.equals("1")) {
            alunoDAO.inserir(new Aluno(0, nome, dadoExtra,login, CriptografiaUtil.hash(senha)));
            return "Aluno cadastrado com sucesso.";
        } else if (tipo.equals("2")) {
            professorDAO.inserir(new Professor(0, nome, dadoExtra,login,CriptografiaUtil.hash(senha)));
            return "Professor cadastrado com sucesso.";
        } else {
            return "Tipo inválido.";
        }
    }

    public String atualizarUsuario(String tipo, int id, String nome, String dadoExtra, String login, String senha) {
        if (tipo.equals("1")) {
            alunoDAO.atualizar(new Aluno(id, nome, dadoExtra,login,CriptografiaUtil.hash(senha)));
            return "Aluno atualizado.";
        } else if (tipo.equals("2")) {
            professorDAO.atualizar(new Professor(id, nome, dadoExtra,login,CriptografiaUtil.hash(senha)));
            return "Professor atualizado.";
        } else {
            return "Tipo inválido.";
        }
    }

    public String removerUsuario(String tipo, int id) {
        if (tipo.equals("1")) {
            alunoDAO.remover(id);
            return "Aluno removido.";
        } else if (tipo.equals("2")) {
            professorDAO.remover(id);
            return "Professor removido.";
        } else {
            return "Tipo inválido.";
        }
    }

    public List<Aluno> listarAlunos() {
        return alunoDAO.listarTodos();
    }

    public List<Professor> listarProfessores() {
        return professorDAO.listarTodos();
    }

    public String atribuirRfid(int id, String rfid) {
        return alunoDAO.buscarPorId(id).map(aluno -> {
            aluno.setIdCartaoRfid(rfid);
            alunoDAO.atualizar(aluno);
            return "RFID atribuído com sucesso.";
        }).orElse("Aluno não encontrado.");
    }
}

