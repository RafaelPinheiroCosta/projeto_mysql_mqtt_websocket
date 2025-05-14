package com.senai.view;

import com.senai.model.Administrador;
import com.senai.model.Aluno;
import com.senai.model.Professor;
import com.senai.model.Usuario;
import com.senai.model.dao.json.AdministradorDAO;
import com.senai.util.CriptografiaUtil;
import com.senai.websocket.WebSocketClienteConsole;

import java.util.Optional;
import java.util.Scanner;

import static com.senai.mqtt.MqttSubscriber.iniciarMqtt;
import static com.senai.websocket.WebSocketSender.iniciarWebSocket;

public class MenuPrincipal {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        iniciarMqtt();
        iniciarWebSocket();
        criarAdm();
        logar();
    }

    public static void logar(){
        Optional<Usuario> usuarioLogado = new LoginView().exibirLogin();
        usuarioLogado.ifPresent(MenuPrincipal::redirecionarMenu);
    }

    public static void criarAdm(){
        Administrador administrador = new Administrador(1,"Rafael","adm", CriptografiaUtil.hash("1234"));
        AdministradorDAO administradorDAO = new AdministradorDAO();
        administradorDAO.inserir(administrador);
    }

    private static void redirecionarMenu(Usuario usuario) {
        switch (usuario.getTipo()) {
            case "Administrador" -> menuAdministrador((Administrador) usuario);
            case "Professor"     -> menuProfessor((Professor) usuario);
            case "Aluno"         -> menuAluno((Aluno) usuario);
            default -> System.out.println("Tipo de usuário desconhecido.");
        }
    }

    private static void menuAdministrador(Administrador administrador) {
        UsuarioView usuarioView = new UsuarioView();
        System.out.printf("Bem vind@ %s \n",administrador.getNome());
        executarMenu("""               
                    ===== MENU ADMINISTRADOR =====
                    1. Gerenciar Usuários (Aluno/Professor)
                    2. Deslogar
                    0. Sair
                    """,
                opcao -> {
            switch (opcao) {
                case "1" -> usuarioView.menu();
                case "2" -> logar();
                case "0" -> {
                    System.out.println("Saindo...");
                    System.exit(0);
                }
                default  -> System.out.println("Opção inválida.");
            }
        });
    }

    private static void menuProfessor(Professor professor) {

        System.out.printf("Bem vind@ %s \n",professor.getNome());
        HorarioView horarioView = new HorarioView();
        executarMenu("""
                    ===== MENU PROFESSOR =====
                    1. Gerenciar Horários
                    2. Receber notificações de atraso
                    3. Deslogar
                    0. Sair
                    """,
                opcao -> {
            switch (opcao) {
                case "1" -> horarioView.menu();
                case "2" -> WebSocketClienteConsole.conectar();
                case "3" -> {
                    WebSocketClienteConsole.desconectar();
                    logar();
                }
                case "0" -> {
                    System.out.println("Saindo...");
                    WebSocketClienteConsole.desconectar();
                    System.exit(0);
                }
                default  -> System.out.println("Opção inválida.");
            }
        });
    }

    private static void menuAluno(Aluno aluno) {

        System.out.printf("Bem vind@ %s \n",aluno.getNome());
        HorarioView horarioView = new HorarioView();
        UsuarioView usuarioView = new UsuarioView();
        executarMenu("""
                    ===== MENU ALUNO =====
                    1. Visualizar Horários
                    2. Mudar RFID
                    3. Deslogar
                    0. Sair
                    """,
                opcao -> {
            switch (opcao) {
                case "1" -> horarioView.listar();
                case "2" -> usuarioView.mudarRfid(aluno);
                case "3" -> logar();
                case "0" -> {
                    System.out.println("Saindo...");
                    System.exit(0);
                }
                default  -> System.out.println("Opção inválida.");
            }
        });
    }

    private static void executarMenu(String titulo, java.util.function.Consumer<String> acoes) {
        String opcao;
        do {
            System.out.print(titulo);
            opcao = scanner.nextLine();
            acoes.accept(opcao);
        } while (!opcao.equals("0"));
    }
}
