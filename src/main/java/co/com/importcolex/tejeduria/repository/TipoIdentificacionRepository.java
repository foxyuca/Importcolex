package co.com.importcolex.tejeduria.repository;

import co.com.importcolex.tejeduria.domain.TipoIdentificacion;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TipoIdentificacion entity.
 */
public interface TipoIdentificacionRepository extends JpaRepository<TipoIdentificacion,Long> {

}
