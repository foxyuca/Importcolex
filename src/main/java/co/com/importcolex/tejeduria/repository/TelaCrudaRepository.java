package co.com.importcolex.tejeduria.repository;

import co.com.importcolex.tejeduria.domain.TelaCruda;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TelaCruda entity.
 */
public interface TelaCrudaRepository extends JpaRepository<TelaCruda,Long> {

}
