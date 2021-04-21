package net.gendercomics.api.data.service;

import net.gendercomics.api.data.repository.RoleRepository;
import net.gendercomics.api.model.MetaData;
import net.gendercomics.api.model.Role;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {RoleServiceTest.TestContextConfiguration.class, RoleService.class})
public class RoleServiceTest {

    @Autowired
    private RoleService _roleService;

    @Autowired
    private RoleRepository _roleRepository;

    @Test
    public void findAll() {
        List<Role> roleList = new ArrayList<>();
        roleList.add(new Role());
        roleList.get(0).setId("id");
        roleList.get(0).setName("testRole");

        when(_roleRepository.findAll((Sort) any())).thenReturn(roleList);

        List<Role> roles = _roleService.findAll();
        assertNotNull(roles);
        assertEquals("id", roles.get(0).getId());
    }

    @Test
    public void insert() {
        Role role = new Role();
        role.setName("role");
        role.setMetaData(new MetaData());
        role.getMetaData().setCreatedOn(new Date());

        when(_roleRepository.insert((Role) any())).thenReturn(role);

        Role roleInserted = _roleService.insert(role, "user");
        assertEquals("role", role.getName());
        assertEquals("user", roleInserted.getMetaData().getCreatedBy());
    }

    @Test
    public void save() {
        Role role = new Role();
        role.setName("role");

        when(_roleRepository.save(any())).thenReturn(role);

        Role roleSaved = _roleService.save(role, "user");
        assertNotNull(roleSaved.getMetaData());
        assertEquals("user", roleSaved.getMetaData().getChangedBy());
    }

    @Test
    public void getRole() {
        Role role = new Role();
        role.setId("id");

        when(_roleRepository.findById("id")).thenReturn(Optional.of(role));

        Role fetchedRole = _roleService.getRole("id");
        assertNotNull(fetchedRole);
        assertEquals("id", fetchedRole.getId());
    }

    @TestConfiguration
    static class TestContextConfiguration {

        @Bean
        public RoleRepository roleRepository() {
            return mock(RoleRepository.class);
        }
    }
}