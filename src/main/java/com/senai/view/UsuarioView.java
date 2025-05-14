package com.senai.view;

import com.senai.controller.UsuarioController;
import com.senai.model.Aluno;
import com.senai.model.Professor;

import java.util.Scanner;

public class UsuarioView {
    private final Scanner scanner = new Scanner(System.in);
    private final UsuarioController controller = new UsuarioController();

    public void menu() {
        String opcao;
        String menuUsuario = """
                    --- MENU DE USUÁRIOS ---
                1. Cadastrar usuário
                2. Atualizar usuário
                3. Remover usuário
                4. Listar usuários
                5. Atribuir/Alterar RFID
                0. Voltar
                """;
        do {

            System.out.print(menuUsuario);
            opcao = scanner.nextLine();

            switch (opcao) {
                case "1" -> cadastrar();
                case "2" -> atualizar();
                case "3" -> remover();
                case "4" -> listar();
                case "5" -> atribuirRfid();
                case "0" -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida.");
            }
        } while (!opcao.equals("0"));
    }

    private void cadastrar() {
        String tipo = scannerPrompt("Tipo (1=Aluno, 2=Professor): ");
        String nome = scannerPrompt("Nome: ");
        String login = scannerPrompt("Login: ");
        String senha = scannerPrompt("Senha: ");
        String dadoExtra = tipo.equals("1") ? scannerPrompt("ID do cartão RFID: ") : scannerPrompt("Disciplina: ");
        System.out.println(controller.cadastrarUsuario(tipo, nome, dadoExtra,login,senha));
    }

    private void atualizar() {
        String tipo = scannerPrompt("Tipo (1=Aluno, 2=Professor): ");
        int id = scannerPromptInt("ID: ");
        String nome = scannerPrompt("Novo nome: ");
        String login = scannerPrompt("Novo Login: ");
        String senha = scannerPrompt("Nova Senha: ");
        String dadoExtra = tipo.equals("1") ? scannerPrompt("Novo RFID: ") : scannerPrompt("Nova disciplina: ");
        System.out.println(controller.atualizarUsuario(tipo, id, nome, dadoExtra,login,senha));
    }

    private void remover() {
        System.out.print("Tipo (1=Aluno, 2=Professor): ");
        String tipo = scanner.nextLine();
        int id = scannerPromptInt("ID: ");
        System.out.println(controller.removerUsuario(tipo, id));
    }

    private void listar() {
        System.out.println("--- Alunos ---");
        for (Aluno a : controller.listarAlunos()) {
            System.out.printf("ID: %d | Nome: %s | RFID: %s\n", a.getId(), a.getNome(), a.getIdCartaoRfid());
        }
        System.out.println("--- Professores ---");
        for (Professor p : controller.listarProfessores()) {
            System.out.printf("ID: %d | Nome: %s | Disciplina: %s\n", p.getId(), p.getNome(), p.getDisciplina());
        }
    }

    public void atribuirRfid() {
        int id = scannerPromptInt("ID do aluno: ");
        String rfid = scannerPrompt("Novo RFID: ");
        System.out.println(controller.atribuirRfid(id, rfid));
    }

    public void mudarRfid(Aluno aluno) {
        String rfid = scannerPrompt("Novo RFID: ");
        System.out.println(controller.atribuirRfid(aluno.getId(), rfid));
    }

    private String scannerPrompt(String msg) {
        System.out.print(msg);
        return scanner.nextLine();
    }

    private int scannerPromptInt(String msg) {
        System.out.print(msg);
        return Integer.parseInt(scanner.nextLine());
    }
}

