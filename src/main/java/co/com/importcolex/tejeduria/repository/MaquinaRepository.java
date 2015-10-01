package co.com.importcolex.tejeduria.repository;

import co.com.importcolex.tejeduria.domain.Maquina;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Maquina entity.
 */
public interface MaquinaRepository extends JpaRepository<Maquina,Long> {

}
