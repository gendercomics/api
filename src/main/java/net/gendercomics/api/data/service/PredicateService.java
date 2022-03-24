package net.gendercomics.api.data.service;

import net.gendercomics.api.model.Predicate;

import java.util.List;

public interface PredicateService {

    Predicate save(Predicate predicate, String userName);

    Predicate save(String de, String en, String userName);

    List<Predicate> findAll();
}
