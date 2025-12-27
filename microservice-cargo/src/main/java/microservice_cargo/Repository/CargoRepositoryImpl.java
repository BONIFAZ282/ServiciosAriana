package microservice_cargo.Repository;

import microservice_cargo.Model.Cargo;
import microservice_cargo.Repository.RowMapper.CargoRowMapper;
import microservice_cargo.Repository.StoredProcedure.StoredProcedureC;
import microservice_cargo.Repository.Translator.CargoTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CargoRepositoryImpl implements CargoRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Cargo> listarCargos() {
        String sql = StoredProcedureC.SEL_CARGO;
        List<CargoTranslator> lista = jdbcTemplate.query(sql, new CargoRowMapper());
        return lista.stream()
                .map(CargoTranslator::toCargoDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<Cargo> listarCargosConEmpleados() { // NUEVO
        String sql = StoredProcedureC.SEL_CARGO_CON_EMPLEADOS;
        List<CargoTranslator> lista = jdbcTemplate.query(sql, new CargoRowMapper());
        return lista.stream()
                .map(CargoTranslator::toCargoDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void insertarCargo(Cargo cargo) {
        jdbcTemplate.update(StoredProcedureC.INS_CARGONUEVO,
                cargo.getNameCargue(),
                cargo.getDescriptioncargue(),
                cargo.getSalary());
    }

    @Override
    public void eliminarCargoLogico(int id) {
        jdbcTemplate.update(StoredProcedureC.UPD_ELIMARCARGOLOGICO, id);
    }

    @Override
    public void modificarCargo(Cargo cargo) {
        jdbcTemplate.update(StoredProcedureC.UPD_MODIFICARCARGO,
                cargo.getIdcharge(),
                cargo.getNameCargue(),
                cargo.getDescriptioncargue(),
                cargo.getSalary());
    }
}