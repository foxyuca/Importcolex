package co.com.importcolex.tejeduria.repository;

import co.com.importcolex.tejeduria.domain.OrdenCompra;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the OrdenCompra entity.
 */
public interface OrdenCompraRepository extends JpaRepository<OrdenCompra,Long> {

}
