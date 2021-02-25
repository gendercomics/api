package net.gendercomics.api.controller;

import java.security.Principal;
import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import net.gendercomics.api.data.service.RoleService;
import net.gendercomics.api.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = {"roles"})
@RestController
@CrossOrigin
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RoleController {

    private final RoleService _roleService;

    @ApiOperation("get all roles")
    @GetMapping(path = "/roles", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Role> getAllRoles() {
        return _roleService.findAll();
    }

    @ApiOperation("get a role")
    @GetMapping(path = "/roles/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Role getRole(@ApiParam @PathVariable("id") String id) {
        return _roleService.getRole(id);
    }

    /*** admin endpoints - secured, only authorized access allowed ***/

    @ApiOperation("insert a role")
    @PostMapping(path = "/roles")
    public Role insertRole(@ApiIgnore Principal principal, @ApiParam(required = true) @RequestBody Role role) {
        return _roleService.insert(role, principal.getName());
    }

    @ApiOperation("update a role")
    @PutMapping(path = "/roles/{id}")
    public Role saveRole(@ApiIgnore Principal principal, @ApiParam(required = true) @RequestBody Role role) {
        return _roleService.save(role, principal.getName());
    }

    @ApiOperation("delete a role")
    @DeleteMapping(path = "/roles/{id}")
    public void deletePerson(@ApiIgnore Principal principal, @ApiParam(required = true) @PathVariable String id) {
        _roleService.delete(id);
    }

}
