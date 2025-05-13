package com.senai.websocket;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.websocket.jakarta.server.config.JakartaWebSocketServletContainerInitializer;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint("/ws")
public class WebSocketSender {

    private static final Set<Session> sessions = new CopyOnWriteArraySet<>();

    @OnOpen
    public void onOpen(Session session) {
        sessions.add(session);
    }

    @OnClose
    public void onClose(Session session) {
        sessions.remove(session);
    }

    @OnMessage
    public void onMessage(String msg, Session session) {
        // Mensagem recebida do cliente (opcional de tratar)
    }

    public static void enviarMensagem(String mensagem) {
        for (Session session : sessions) {
            try {
                if (session.isOpen()) {
                    session.getBasicRemote().sendText(mensagem);
                }
            } catch (IOException e) {
                System.err.println("Erro ao enviar mensagem, removendo sessão.");
                sessions.remove(session);
            }
        }
    }
    public static void iniciarWebSocket() throws Exception {
        Server server = new Server(8080);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        JakartaWebSocketServletContainerInitializer.configure(context, (servletContext, wsContainer) -> {
            wsContainer.addEndpoint(WebSocketSender.class);
        });

        server.start();
        System.out.println("Servidor WebSocket Jetty iniciado em ws://localhost:8080/ws");
    }

}
