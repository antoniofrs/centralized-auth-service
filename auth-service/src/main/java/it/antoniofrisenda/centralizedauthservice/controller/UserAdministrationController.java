package it.antoniofrisenda.centralizedauthservice.controller;

import it.antoniofrisenda.centralizedauthservice.dto.RoleDto;
import it.antoniofrisenda.centralizedauthservice.dto.UserDto;
import it.antoniofrisenda.centralizedauthservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/administration/user")
public class UserAdministrationController {

    private final UserService userService;

    @GetMapping("/username/{username}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto find(@PathVariable String username){
        return userService.findByUsername(username);
    }

    @PostMapping("/role")
    @ResponseStatus(HttpStatus.OK)
    public RoleDto addRole(@RequestBody RoleDto roleDto){
        return userService.addRole(roleDto);
    }

    @GetMapping("/role")
    @ResponseStatus(HttpStatus.OK)
    public List<RoleDto> findAllRoles(){
        return userService.findAllRoles();
    }

    @GetMapping("/role/{roleName}")
    @ResponseStatus(HttpStatus.OK)
    public RoleDto findRole(@PathVariable String roleName){
        roleName = roleName.toUpperCase();
        return userService.findRole(roleName);
    }

}
