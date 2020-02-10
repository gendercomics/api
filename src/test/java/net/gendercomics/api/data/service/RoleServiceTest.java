package net.gendercomics.api.data.service;

import java.util.ArrayList;
import java.util.List;

import net.gendercomics.api.data.repository.RoleRepository;
import net.gendercomics.api.model.Role;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
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

    @TestConfiguration
    static class TestContextConfiguration {

        @Bean
        public RoleRepository roleRepository() {
            return mock(RoleRepository.class);
        }
    }
}