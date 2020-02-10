package net.gendercomics.api.data.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.gendercomics.api.data.repository.RoleRepository;
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

}
