package co.com.importcolex.tejeduria.repository;

import co.com.importcolex.tejeduria.domain.Operario;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Operario entity.
 */
public interface OperarioRepository extends JpaRepository<Operario,Long> {

}
