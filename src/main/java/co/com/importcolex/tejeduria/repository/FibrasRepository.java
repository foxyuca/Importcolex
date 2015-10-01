package co.com.importcolex.tejeduria.repository;

import co.com.importcolex.tejeduria.domain.Fibras;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Fibras entity.
 */
public interface FibrasRepository extends JpaRepository<Fibras,Long> {

}
