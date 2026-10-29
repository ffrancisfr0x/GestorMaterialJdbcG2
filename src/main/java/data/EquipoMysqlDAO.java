package data;

import models.Equipo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EquipoMysqlDAO implements DAO<Equipo> {

    private static final String URL = "jdbc:mysql://localhost:3307/ad";
    private static final String USER = "user";
    private static final String PWD = "pwd";

    private static final String SQL_FIND = "select * from equipo where id = ?";
    private static final String SQL_FIND_ALL = "select * from equipo";
    private static final String SQL_INSERT = "insert into equipo (nombre, categoria, disponible) values (?, ?, ?)";
    private static final String SQL_UPDATE = "update equipo set nombre = ?, categoria = ?, disponible = ? where id = ?";
    private static final String SQL_DELETE = "delete from equipo where id = ?";
    private static final String SQL_FIND_BY_NAME = "select * from equipo where nombre = ?";
    private static final String SQL_FIND_BY_CATEGORIA = "select * from equipo where categoria = ?";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PWD);
    }

    private Equipo mapRow(ResultSet rs) throws SQLException {
        return new Equipo(
                rs.getLong("id"),
                rs.getString("nombre"),
                rs.getString("categoria"),
                rs.getBoolean("disponible")
        );
    }

    @Override
    public Optional<Equipo> find(Long id) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_FIND)) {

            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? Optional.of(mapRow(rs)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar el equipo con id " + id, e);
        }
    }

    @Override
    public List<Equipo> findAll() {
        List<Equipo> equipos = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_FIND_ALL);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                equipos.add(mapRow(rs));
            }
            return equipos;
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener los equipos", e);
        }
    }

    @Override
    public Equipo save(Equipo entity) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, entity.getNombre());
            stmt.setString(2, entity.getCategoria());
            stmt.setObject(3, entity.getDisponible(), Types.BOOLEAN);
            stmt.executeUpdate();

            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    entity.setId(keys.getLong(1));
                }
            }
            return entity;
        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar el equipo " + entity.getNombre(), e);
        }
    }

    @Override
    public void update(Equipo entity) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_UPDATE)) {

            stmt.setString(1, entity.getNombre());
            stmt.setString(2, entity.getCategoria());
            stmt.setObject(3, entity.getDisponible(), Types.BOOLEAN);
            stmt.setLong(4, entity.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar el equipo con id " + entity.getId(), e);
        }
    }

    @Override
    public void delete(Long id) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_DELETE)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar el equipo con id " + id, e);
        }
    }

    public Optional<Equipo> findByName(String nombre) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_FIND_BY_NAME)) {

            stmt.setString(1, nombre);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? Optional.of(mapRow(rs)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar el equipo por nombre " + nombre, e);
        }
    }

    public Optional<Equipo> findByCategoria(String categoria) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_FIND_BY_CATEGORIA)) {

            stmt.setString(1, categoria);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? Optional.of(mapRow(rs)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar el equipo por categoría " + categoria, e);
        }
    }
}