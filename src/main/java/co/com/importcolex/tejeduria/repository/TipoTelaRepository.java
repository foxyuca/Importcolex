package co.com.importcolex.tejeduria.repository;

import co.com.importcolex.tejeduria.domain.TipoTela;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TipoTela entity.
 */
public interface TipoTelaRepository extends JpaRepository<TipoTela,Long> {

}
