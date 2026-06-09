package net.gendercomics.api.data.service;

import net.gendercomics.api.model.Role;

import java.util.List;

public interface RoleService {

    List<Role> findAll();

    Role getRole(String id);

    Role insert(Role role, String userName);

    Role save(Role role, String userName);

    long getRoleCount();

    void delete(String id);
}
