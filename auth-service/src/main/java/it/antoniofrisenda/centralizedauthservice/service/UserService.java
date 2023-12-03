package it.antoniofrisenda.centralizedauthservice.service;

import it.antoniofrisenda.centralizedauthservice.dto.RoleDto;
import it.antoniofrisenda.centralizedauthservice.dto.UserDto;
import it.antoniofrisenda.centralizedauthservice.exception.RoleNotFoundException;
import it.antoniofrisenda.centralizedauthservice.mapper.RoleMapper;
import it.antoniofrisenda.centralizedauthservice.mapper.UserMapper;
import it.antoniofrisenda.centralizedauthservice.model.Role;
import it.antoniofrisenda.centralizedauthservice.repository.RoleRepository;
import it.antoniofrisenda.centralizedauthservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public UserDto findByUsername(String username){
        return userRepository.findByUsername(username)
                .map(userMapper::fromUser)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    public RoleDto addRole(RoleDto roleDto){

        log.debug("Adding role {}", roleDto.getName());

        String roleId = roleRepository.findByName(roleDto.getName())
                .map(Role::getId)
                .orElse(null);

        Role role = roleMapper.fromDto(roleDto, roleId);

        return roleMapper.toDto(roleRepository.save(role));

    }

    public List<RoleDto> findAllRoles() {
        return roleRepository.findAll().stream()
                .map(roleMapper::toDto)
                .collect(Collectors.toList());
    }

    public RoleDto findRole(String roleName) {
        return roleRepository.findByName(roleName)
                .map(roleMapper::toDto)
                .orElseThrow(() -> new RoleNotFoundException(roleName));
    }
}
