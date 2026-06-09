package net.gendercomics.api.data.service;

import net.gendercomics.api.model.Publisher;

import java.util.List;

public interface PublisherService {

    List<Publisher> findAll();

    Publisher getPublisher(String id);

    Publisher insert(Publisher publisher, String userName);

    Publisher save(Publisher publisher, String userName);

    long getPublisherCount();

    void delete(String id);
}
