package com.senai.websocket;

import jakarta.websocket.*;

import java.net.URI;
import java.util.Timer;
import java.util.TimerTask;

@ClientEndpoint
public class WebSocketClienteConsole {

    private static Session session;
    private static Timer pingTimer;

    @OnOpen
    public void onOpen(Session s) {
        session = s;
        System.out.println("Conectado ao servidor WebSocket.");
    }

    @OnMessage
    public void onMessage(String message) {
        System.out.println("Mensagem recebida via WebSocket: " + message);
    }

    @OnClose
    public void onClose(Session session, CloseReason reason) {
        System.out.println("Conexão encerrada: " + reason);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.err.println("Erro no WebSocket: " + throwable.getMessage());
    }

    public static void conectar() {
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            URI uri = new URI("ws://localhost:8080/ws");
            container.connectToServer(WebSocketClienteConsole.class, uri);

            // iniciar pings a cada 20s
            pingTimer = new Timer(true);
            pingTimer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    if (session != null && session.isOpen()) {
                        try {
                            session.getBasicRemote().sendText("ping");
                        } catch (Exception ignored) {}
                    }
                }
            }, 20_000, 20_000);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void desconectar() {
        try {
            if (pingTimer != null) {
                pingTimer.cancel();
            }
            if (session != null && session.isOpen()) {
                session.close();
                System.out.println("Desconectado do WebSocket.");
            }
        } catch (Exception e) {
            System.err.println("Erro ao fechar WebSocket: " + e.getMessage());
        }
    }
}

