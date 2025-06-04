package com.senai.controller;

import com.senai.model.Turma;
import com.senai.model.dao.json.AlunoDAO;
import com.senai.model.dao.json.HorarioDAO;
import com.senai.model.dao.json.ProfessorDAO;
import com.senai.model.Aluno;
import com.senai.model.Horario;
import com.senai.model.Professor;
import com.senai.model.dao.json.TurmaDAO;
import com.senai.websocket.WebSocketSender;

import java.time.LocalTime;
import java.util.Optional;

public class ControleDeAcessoController {
    private final AlunoDAO alunoDAO = new AlunoDAO();
    private final HorarioDAO horarioDAO = new HorarioDAO();
    private final ProfessorDAO professorDAO = new ProfessorDAO();
    private final TurmaDAO turmaDAO = new TurmaDAO();


    public String processarEntrada(String rfid) {
        Optional<Aluno> alunoOpt = alunoDAO.buscarPorRfid(rfid);
        if (alunoOpt.isEmpty()) {
            return "[ACESSO NEGADO] Aluno não encontrado para RFID: " + rfid;
        }

        Aluno aluno = alunoOpt.get();
        Optional<Horario> horarioOpt = horarioDAO.buscarHorarioDoAluno(aluno.getId());

        if (horarioOpt.isEmpty()) {
            return "[ACESSO] Aluno: " + aluno.getNome() + " - Nenhum horário atribuído.";
        }

        Horario horario = horarioOpt.get();

        Optional<Turma> turmaOpt = turmaDAO.buscarPorAluno(aluno);

        if (turmaOpt.isEmpty()) {
            return "[ACESSO] Aluno: " + aluno.getNome() + " - Nenhuma turma atribuída.";
        }
        LocalTime horarioEntrada = turmaOpt.get().getHorarioEntrada();
        int tolerancia = turmaOpt.get().getCurso().getTolerancia();

        boolean atrasado = aluno.estaAtrasado(horarioEntrada,tolerancia);

        if (atrasado) {
            Optional<Professor> professorOpt = professorDAO.buscarPorId(horario.getIdProfessor());

            professorOpt.ifPresent(professor -> {
                String msg = "[ATRASO] Aluno " + aluno.getNome() + " chegou atrasado.";
                WebSocketSender.enviarMensagem(msg);
            });
            return "[ATRASO DETECTADO] Aluno: " + aluno.getNome();
        }

        return "[ENTRADA AUTORIZADA] Aluno: " + aluno.getNome();
    }
}

