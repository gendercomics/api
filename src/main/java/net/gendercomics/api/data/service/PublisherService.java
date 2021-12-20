package net.gendercomics.api.data.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.gendercomics.api.data.repository.PublisherRepository;
import net.gendercomics.api.model.MetaData;
import net.gendercomics.api.model.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class PublisherService {

    private final PublisherRepository _publisherRepository;

    public List<Publisher> findAll() {
        List<Publisher> publishers = _publisherRepository.findAll();
        Collections.sort(publishers);
        return publishers;
    }

    public Publisher getPublisher(String id) {
        return _publisherRepository.findById(id).orElse(null);
    }

    public Publisher insert(Publisher publisher, String userName) {
        log.debug("userName={} tries to insert publisher={}", userName, publisher.getName());
        publisher.setMetaData(new MetaData());
        publisher.getMetaData().setCreatedOn(new Date());
        publisher.getMetaData().setCreatedBy(userName);
        return _publisherRepository.insert(publisher);
    }

    public Publisher save(Publisher publisher, String userName) {
        if (publisher.getMetaData() == null) {
            publisher.setMetaData(new MetaData());
        }
        publisher.getMetaData().setChangedOn(new Date());
        publisher.getMetaData().setChangedBy(userName);
        return _publisherRepository.save(publisher);
    }

    public long getPublisherCount() {
        return _publisherRepository.count();
    }

    public void delete(String id) {
        _publisherRepository.deleteById(id);
    }
}
