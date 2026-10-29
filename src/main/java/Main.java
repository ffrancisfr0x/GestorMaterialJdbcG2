import data.DAO;
import data.EquipoMysqlDAO;
import models.Equipo;

import java.util.List;
import java.util.Optional;

public class Main {

    public static void main(String[] args) {
        DAO<Equipo> dao = new EquipoMysqlDAO();

        // 1. SAVE
        System.out.println("=== SAVE ===");
        Equipo nuevo = new Equipo("GTA VI", "Acción", true);
        Equipo guardado = dao.save(nuevo);
        System.out.println("Guardado: " + guardado);

        Long id = guardado.getId();

        // 2. FIND
        System.out.println("\n=== FIND ===");
        Optional<Equipo> encontrado = dao.find(id);
        System.out.println("Encontrado: " + encontrado);

        // 3. FIND ALL
        System.out.println("\n=== FIND ALL ===");
        List<Equipo> todos = dao.findAll();
        todos.forEach(System.out::println);

        // 4. UPDATE
        System.out.println("\n=== UPDATE ===");
        encontrado.get().setDisponible(false);
        encontrado.get().setCategoria("Aventura");
        dao.update(encontrado.get());
        System.out.println("Tras actualizar: " + dao.find(id));

        // 5. DELETE
        System.out.println("\n=== DELETE ===");
        dao.delete(id);
        Optional<Equipo> borrado = dao.find(id);
        System.out.println(borrado == null
                ? "Equipo con id " + id + " eliminado correctamente."
                : "ERROR: el equipo sigue existiendo: " + borrado);

        // 6. Estado final
        System.out.println("\n=== ESTADO FINAL ===");
        System.out.println("Total de equipos: " + dao.findAll().size());
    }
}