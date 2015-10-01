package co.com.importcolex.tejeduria.repository;

import co.com.importcolex.tejeduria.domain.InventarioFibras;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the InventarioFibras entity.
 */
public interface InventarioFibrasRepository extends JpaRepository<InventarioFibras,Long> {

}
