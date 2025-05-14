package com.senai.view;


import com.senai.controller.LoginController;
import com.senai.model.Usuario;
import com.senai.util.CriptografiaUtil;

import java.util.Optional;
import java.util.Scanner;

public class LoginView {
    private final Scanner scanner = new Scanner(System.in);
    private final LoginController controller = new LoginController();

    public Optional<Usuario> exibirLogin() {
        System.out.println("\n===== LOGIN DO SISTEMA =====");
        System.out.print("Login: ");
        String login = scanner.nextLine();

        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        Optional<Usuario> usuario = controller.autenticar(login, CriptografiaUtil.hash(senha));
        if (usuario.isEmpty()) {
            System.out.println("\nCredenciais inválidas. Tente novamente.\n");
        }
        return usuario;
    }
}
