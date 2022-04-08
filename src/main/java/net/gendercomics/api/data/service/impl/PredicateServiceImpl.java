package net.gendercomics.api.data.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.gendercomics.api.data.repository.PredicateRepository;
import net.gendercomics.api.data.service.PredicateService;
import net.gendercomics.api.model.Language;
import net.gendercomics.api.model.MetaData;
import net.gendercomics.api.model.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class PredicateServiceImpl implements PredicateService {

    private final PredicateRepository _predicateRepository;

    @Override
    public Predicate save(Predicate predicate, String userName) {
        if (predicate.getId() == null) {
            predicate.setMetaData(new MetaData());
            predicate.getMetaData().setCreatedOn(new Date());
            predicate.getMetaData().setCreatedBy(userName);
            return _predicateRepository.insert(predicate);
        } else {
            predicate.getMetaData().setChangedOn(new Date());
            predicate.getMetaData().setChangedBy(userName);
            return _predicateRepository.save(predicate);
        }
    }

    @Override
    public Predicate save(String de, String en, String userName) {
        Predicate predicate = new Predicate();
        predicate.setValues(new HashMap<>());
        predicate.getValues().put(Language.de, de);
        predicate.getValues().put(Language.en, en);

        return save(predicate, userName);
    }

    @Override
    public List<Predicate> findAll() {
        List<Predicate> predicateList = _predicateRepository.findAll();
        //Collections.sort(predicateList);
        return predicateList;
    }

    @Override
    public void delete(String predicateId) {
        _predicateRepository.deleteById(predicateId);
    }
}
