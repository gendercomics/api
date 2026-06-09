package net.gendercomics.api.data.service;

import net.gendercomics.api.model.Name;

import java.util.List;

public interface NameService {

    List<Name> findAll();

    List<Name> findSearchableNames();

    Name saveName(Name name);

    void deleteName(Name name);
}
