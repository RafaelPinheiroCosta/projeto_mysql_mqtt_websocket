package com.senai.model.dao.mysql;


import com.senai.model.Horario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HorarioDAO {

    public void inserir(Horario horario) {
        try (Connection conn = ConexaoMySQL.conectar()) {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO horario (id_aluno, id_professor, hora_inicio) VALUES (?, ?, ?)");
            stmt.setInt(1, horario.getIdAluno());
            stmt.setInt(2, horario.getIdProfessor());
            stmt.setTime(3, Time.valueOf(horario.getHoraInicio()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizar(Horario horario) {
        try (Connection conn = ConexaoMySQL.conectar()) {
            PreparedStatement stmt = conn.prepareStatement("UPDATE horario SET id_aluno = ?, id_professor = ?, hora_inicio = ? WHERE id = ?");
            stmt.setInt(1, horario.getIdAluno());
            stmt.setInt(2, horario.getIdProfessor());
            stmt.setTime(3, Time.valueOf(horario.getHoraInicio()));
            stmt.setInt(4, horario.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void remover(int id) {
        try (Connection conn = ConexaoMySQL.conectar()) {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM horario WHERE id = ?");
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Optional<Horario> buscarHorarioDoAluno(int idAluno) {
        try (Connection conn = ConexaoMySQL.conectar()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM horario WHERE id_aluno = ?");
            stmt.setInt(1, idAluno);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(new Horario(
                        rs.getInt("id"),
                        rs.getInt("id_aluno"),
                        rs.getInt("id_professor"),
                        rs.getTime("hora_inicio").toLocalTime()
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public List<Horario> listarTodos() {
        List<Horario> lista = new ArrayList<>();
        try (Connection conn = ConexaoMySQL.conectar()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM horario");
            while (rs.next()) {
                lista.add(new Horario(
                        rs.getInt("id"),
                        rs.getInt("id_aluno"),
                        rs.getInt("id_professor"),
                        rs.getTime("hora_inicio").toLocalTime()
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}

