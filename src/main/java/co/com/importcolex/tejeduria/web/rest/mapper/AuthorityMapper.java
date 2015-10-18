package co.com.importcolex.tejeduria.web.rest.mapper;

import org.mapstruct.Mapper;

import co.com.importcolex.tejeduria.domain.Authority;
import co.com.importcolex.tejeduria.web.rest.dto.AuthorityDTO;

@Mapper(componentModel = "spring", uses = {})
public interface AuthorityMapper {

	AuthorityDTO authorityToAuthorityDTO(Authority authority);
	
	
	Authority authorityDTOToAuthority(AuthorityDTO authorityDTO);
}


