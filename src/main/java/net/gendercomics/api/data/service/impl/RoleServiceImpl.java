package net.gendercomics.api.data.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.gendercomics.api.data.repository.RoleRepository;
import net.gendercomics.api.data.service.RoleService;
import net.gendercomics.api.model.MetaData;
import net.gendercomics.api.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class RoleServiceImpl implements RoleService {

    private final RoleRepository _roleRepository;

    @Override
    public List<Role> findAll() {
        return _roleRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    @Override
    public Role getRole(String id) {
        return _roleRepository.findById(id).orElse(null);
    }

    @Override
    public Role insert(Role role, String userName) {
        log.debug("userName={} tries to insert role", userName);
        role.setMetaData(new MetaData());
        role.getMetaData().setCreatedOn(new Date());
        role.getMetaData().setCreatedBy(userName);
        return _roleRepository.insert(role);
    }

    @Override
    public Role save(Role role, String userName) {
        if (role.getMetaData() == null) {
            role.setMetaData(new MetaData());
        }
        role.getMetaData().setChangedOn(new Date());
        role.getMetaData().setChangedBy(userName);
        return _roleRepository.save(role);
    }

    @Override
    public long getRoleCount() {
        return _roleRepository.count();
    }

    @Override
    public void delete(String id) {
        _roleRepository.deleteById(id);
    }
}
