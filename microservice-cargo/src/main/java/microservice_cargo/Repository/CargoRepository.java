package microservice_cargo.Repository;
import microservice_cargo.Model.*;

import java.util.List;

public interface CargoRepository {

    List<Cargo> listarCargos();
    List<Cargo> listarCargosConEmpleados(); // NUEVO
    void insertarCargo(Cargo cargo);
    void eliminarCargoLogico(int id);
    void modificarCargo(Cargo cargo);
}