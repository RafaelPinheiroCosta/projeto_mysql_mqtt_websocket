package com.senai.model.dao.mysql;


import com.senai.model.Aluno;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AlunoDAO{


    public void inserir(Aluno aluno) {
        try (Connection conn = ConexaoMySQL.conectar()) {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO aluno (nome, id_cartao) VALUES (?, ?)");
            stmt.setString(1, aluno.getNome());
            stmt.setString(2, aluno.getIdCartaoRfid());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void atualizar(Aluno aluno) {
        try (Connection conn = ConexaoMySQL.conectar()) {
            PreparedStatement stmt = conn.prepareStatement("UPDATE aluno SET nome = ?, id_cartao = ? WHERE id = ?");
            stmt.setString(1, aluno.getNome());
            stmt.setString(2, aluno.getIdCartaoRfid());
            stmt.setInt(3, aluno.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void remover(int id) {
        try (Connection conn = ConexaoMySQL.conectar()) {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM aluno WHERE id = ?");
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public Optional<Aluno> buscarPorId(int id) {
        try (Connection conn = ConexaoMySQL.conectar()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM aluno WHERE id = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(new Aluno(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("id_cartao")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<Aluno> buscarPorRfid(String rfid) {
        try (Connection conn = ConexaoMySQL.conectar()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM aluno WHERE id_cartao = ?");
            stmt.setString(1, rfid);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(new Aluno(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("id_cartao")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }


    public List<Aluno> listarTodos() {
        List<Aluno> lista = new ArrayList<>();
        try (Connection conn = ConexaoMySQL.conectar()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM aluno");
            while (rs.next()) {
                lista.add(new Aluno(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("id_cartao")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}

