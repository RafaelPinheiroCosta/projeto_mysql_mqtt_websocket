package com.senai.view;

import com.senai.controller.HorarioController;
import com.senai.model.Horario;

import java.time.LocalTime;
import java.util.Scanner;

public class HorarioView {
    private final Scanner scanner = new Scanner(System.in);
    private final HorarioController controller = new HorarioController();

    public static void main(String[] args) {
        HorarioView horarioView = new HorarioView();
        horarioView.menu();
    }

    public void menu() {
        String opcao;
        String menuHorario = """
                --- MENU DE HORÁRIOS ---
                
                    1. Cadastrar horário
                    2. Atualizar horário
                    3. Remover horário
                    4. Listar horários
                    0. Voltar
                    
                """;
        do {
            System.out.print(menuHorario);
            opcao = scanner.nextLine();

            switch (opcao) {
                case "1" -> cadastrar();
                case "2" -> atualizar();
                case "3" -> remover();
                case "4" -> listar();
                case "0" -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida.");
            }
        } while (!opcao.equals("0"));
    }

    private void cadastrar() {
        int idTurma = scannerPromptInt("ID da turma: ");
        int idProfessor = scannerPromptInt("ID do professor: ");
        System.out.println(controller.cadastrarHorario(idTurma, idProfessor));
    }

    private void atualizar() {
        int id = scannerPromptInt("ID do horário: ");
        int idTurma = scannerPromptInt("Novo ID da turma: ");
        int idProfessor = scannerPromptInt("Novo ID do professor: ");
        System.out.println(controller.atualizarHorario(id,idTurma, idProfessor));
    }

    private void remover() {
        int id = scannerPromptInt("ID do horário: ");
        System.out.println(controller.removerHorario(id));
    }

    public void listar() {
        for (Horario h : controller.listarHorarios()) {
            System.out.printf("ID: %d | Aluno ID: %d | Professor ID: %d ",
                    h.getId(), h.getIdTurma(), h.getIdProfessor());
        }
    }
    private int scannerPromptInt(String msg) {
        System.out.print(msg);
        return Integer.parseInt(scanner.nextLine());
    }
}

