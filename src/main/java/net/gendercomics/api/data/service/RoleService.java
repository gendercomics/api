package net.gendercomics.api.data.service;

import java.util.Date;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.gendercomics.api.data.repository.RoleRepository;
import net.gendercomics.api.model.MetaData;
import net.gendercomics.api.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class RoleService {

    private final RoleRepository _roleRepository;

    public List<Role> findAll() {
        List<Role> roles = _roleRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
        log.debug("#roles={}", roles.size());
        return roles;
    }

    public Role getRole(String id) {
        return _roleRepository.findById(id).orElse(null);
    }

    public Role insert(Role role, String userName) {
        log.debug("userName={} tries to insert role", userName);
        role.setMetaData(new MetaData());
        role.getMetaData().setCreatedOn(new Date());
        role.getMetaData().setCreatedBy(userName);
        return _roleRepository.insert(role);
    }

    public Role save(Role role, String userName) {
        if (role.getMetaData() == null) {
            role.setMetaData(new MetaData());
        }
        role.getMetaData().setChangedOn(new Date());
        role.getMetaData().setChangedBy(userName);
        return _roleRepository.save(role);
    }

    public long getRoleCount() {
        return _roleRepository.count();
    }
}
