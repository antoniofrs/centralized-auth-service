package it.antoniofrisenda.centralizedauthservice.mapper;

import it.antoniofrisenda.centralizedauthservice.dto.RoleDto;
import it.antoniofrisenda.centralizedauthservice.model.Role;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class RoleMapper {

    public Role fromDto(RoleDto roleDto, String roleId) {
        return Role.builder()
                .id(roleId)
                .name(roleDto.getName())
                .authorities(roleDto.getAuthorities())
                .build();
    }

    public Role fromDto(RoleDto roleDto) {
        return fromDto(roleDto, null);
    }

    public RoleDto toDto(Role role) {

        if (role == null) {
            return null;
        }

        return RoleDto.builder()
                .name(role.getName())
                .authorities(role.getAuthorities())
                .build();
    }

}
