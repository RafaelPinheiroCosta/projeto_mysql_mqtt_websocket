package com.senai.view;

import java.util.Scanner;

import static com.senai.mqtt.MqttSubscriber.iniciarMqtt;
import static com.senai.websocket.WebSocketSender.iniciarWebSocket;

public class MenuPrincipal {
    public static void main(String[] args) throws Exception {

        iniciarMqtt();
        iniciarWebSocket();

        Scanner scanner = new Scanner(System.in);
        UsuarioView usuarioView = new UsuarioView();
        HorarioView horarioView = new HorarioView();

        String menuPrincipal = """
                    ===== MENU PRINCIPAL =====
                1. Gerenciar Usuários (Aluno/Professor)
                2. Gerenciar Horários
                0. Sair
                """;
        String opcao;
        do {

            System.out.print(menuPrincipal);
            opcao = scanner.nextLine();

            switch (opcao) {
                case "1" -> usuarioView.menu();
                case "2" -> horarioView.menu();
                case "0" -> {
                    System.out.println("Saindo...");
                }
                default -> System.out.println("Opção inválida.");
            }
        }while (!opcao.equals("0"));
    }
}

