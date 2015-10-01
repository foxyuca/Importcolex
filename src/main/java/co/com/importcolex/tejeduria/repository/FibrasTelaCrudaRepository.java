package co.com.importcolex.tejeduria.repository;

import co.com.importcolex.tejeduria.domain.FibrasTelaCruda;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the FibrasTelaCruda entity.
 */
public interface FibrasTelaCrudaRepository extends JpaRepository<FibrasTelaCruda,Long> {

}
