package net.gendercomics.api.controller;

import java.security.Principal;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import net.gendercomics.api.data.service.RoleService;
import net.gendercomics.api.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Tag(name = "roles")
@RestController
@CrossOrigin
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RoleController {

    private final RoleService _roleService;

    @Operation(summary = "get all roles")
    @GetMapping(path = "/roles", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Role> getAllRoles() {
        return _roleService.findAll();
    }

    @Operation(summary = "get a role")
    @GetMapping(path = "/roles/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Role getRole(@Parameter @PathVariable("id") String id) {
        return _roleService.getRole(id);
    }

    /*** admin endpoints - secured, only authorized access allowed ***/

    @Operation(summary = "insert a role")
    @PostMapping(path = "/roles")
    public Role insertRole(@Parameter(hidden = true) Principal principal, @Parameter(required = true) @RequestBody Role role) {
        return _roleService.insert(role, principal.getName());
    }

    @Operation(summary = "update a role")
    @PutMapping(path = "/roles/{id}")
    public Role saveRole(@Parameter(hidden = true) Principal principal, @Parameter(required = true) @RequestBody Role role) {
        return _roleService.save(role, principal.getName());
    }

    @Operation(summary = "delete a role")
    @DeleteMapping(path = "/roles/{id}")
    public void deletePerson(@Parameter(hidden = true) Principal principal, @Parameter(required = true) @PathVariable String id) {
        _roleService.delete(id);
    }

}
