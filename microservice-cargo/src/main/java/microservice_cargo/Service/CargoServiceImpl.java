package microservice_cargo.Service;

import microservice_cargo.Model.*;
import microservice_cargo.Repository.CargoRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class CargoServiceImpl implements CargoService {

    @Autowired
    private CargoRepository cargoRepository;

    @Override
    public List<Cargo> listarCargos() {
        return cargoRepository.listarCargos();
    }

    @Override
    public List<Cargo> listarCargosConEmpleados() { // NUEVO
        return cargoRepository.listarCargosConEmpleados();
    }

    @Override
    public void insertarCargo(Cargo cargo) {
        cargoRepository.insertarCargo(cargo);
    }

    @Override
    public void eliminarCargoLogico(int id) {
        cargoRepository.eliminarCargoLogico(id);
    }

    @Override
    public void modificarCargo(Cargo cargo) {
        cargoRepository.modificarCargo(cargo);
    }
}