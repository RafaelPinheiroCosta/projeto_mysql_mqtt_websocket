package com.senai.model.dao.mysql;

import com.senai.model.Professor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProfessorDAO  {

    public void inserir(Professor professor) {
        try (Connection conn = ConexaoMySQL.conectar()) {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO professor (nome,login, senha disciplina) VALUES (?, ?, ?, ?)");
            stmt.setString(1, professor.getNome());
            stmt.setString(2, professor.getLogin());
            stmt.setString(3, professor.getSenha());
            stmt.setString(4, professor.getDisciplina());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizar(Professor professor) {
        try (Connection conn = ConexaoMySQL.conectar()) {
            PreparedStatement stmt = conn.prepareStatement("UPDATE professor SET nome = ?,login = ?,senha = ?, disciplina = ? WHERE id = ?");
            stmt.setString(1, professor.getNome());
            stmt.setString(2, professor.getLogin());
            stmt.setString(3, professor.getSenha());
            stmt.setString(4, professor.getDisciplina());
            stmt.setInt(5, professor.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void remover(int id) {
        try (Connection conn = ConexaoMySQL.conectar()) {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM professor WHERE id = ?");
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Optional<Professor> buscarPorId(int id) {
        try (Connection conn = ConexaoMySQL.conectar()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM professor WHERE id = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(new Professor(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("login"),
                        rs.getString("senha"),
                        rs.getString("disciplina")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public List<Professor> listarTodos() {
        List<Professor> lista = new ArrayList<>();
        try (Connection conn = ConexaoMySQL.conectar()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM professor");
            while (rs.next()) {
                lista.add(new Professor(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("disciplina"),
                        rs.getString("login"),
                        rs.getString("senha")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}

