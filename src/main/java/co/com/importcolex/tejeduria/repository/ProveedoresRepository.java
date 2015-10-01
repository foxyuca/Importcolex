package co.com.importcolex.tejeduria.repository;

import co.com.importcolex.tejeduria.domain.Proveedores;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Proveedores entity.
 */
public interface ProveedoresRepository extends JpaRepository<Proveedores,Long> {

}
