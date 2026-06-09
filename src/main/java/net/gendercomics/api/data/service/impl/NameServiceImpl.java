package net.gendercomics.api.data.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.gendercomics.api.data.repository.NameRepository;
import net.gendercomics.api.data.service.MongoDbService;
import net.gendercomics.api.data.service.NameService;
import net.gendercomics.api.model.Name;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class NameServiceImpl implements NameService {

    private final MongoDbService _mongoDbService;
    private final NameRepository _nameRepository;

    @Override
    public List<Name> findAll() {
        List<Name> names = _nameRepository.findAll();
        Collections.sort(names);
        return names;
    }

    @Override
    public List<Name> findSearchableNames() {
        List<Name> names = _nameRepository.findIsSearchable();
        Collections.sort(names);
        return names;
    }

    @Override
    public Name saveName(Name name) {
        name.setEmptyStringsNull();
        return name.getId() == null ? _nameRepository.insert(name) : _nameRepository.save(name);
    }

    @Override
    public void deleteName(Name name) {
        _nameRepository.delete(name);
    }
}
