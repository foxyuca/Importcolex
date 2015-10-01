package co.com.importcolex.tejeduria.repository;

import co.com.importcolex.tejeduria.domain.Clientes;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Clientes entity.
 */
public interface ClientesRepository extends JpaRepository<Clientes,Long> {

}
