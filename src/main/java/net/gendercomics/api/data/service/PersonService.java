package net.gendercomics.api.data.service;

import net.gendercomics.api.model.Person;

import java.util.List;

public interface PersonService {

    List<Person> findAll();

    Person insert(Person person, String userName);

    Person save(Person person, String userName);

    Person getPerson(String id);

    long getPersonCount();

    void delete(String id);
}
