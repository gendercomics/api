package net.gendercomics.api.data.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.gendercomics.api.data.repository.PredicateRepository;
import net.gendercomics.api.data.service.PredicateService;
import net.gendercomics.api.model.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class PredicateServiceImpl implements PredicateService {

    private final PredicateRepository _predicateRepository;

    @Override
    public Predicate save(Predicate predicate) {
        return _predicateRepository.save(predicate);
    }

    @Override
    public List<Predicate> findAll() {
        List<Predicate> predicateList = _predicateRepository.findAll();
        //Collections.sort(predicateList);
        return predicateList;
    }

}
