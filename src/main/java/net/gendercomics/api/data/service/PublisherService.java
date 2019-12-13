package net.gendercomics.api.data.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.gendercomics.api.data.repository.PublisherRepository;
import net.gendercomics.api.model.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class PublisherService {

    private final PublisherRepository _publisherRepository;

    public List<Publisher> findAll() {
        List<Publisher> publishers = _publisherRepository.findAll();
        log.debug("#publisher={}", publishers.size());
        return publishers;
    }

    public Publisher insert(Publisher publisher, String userName) {
        log.debug("userName={} tries to insert publisher={}", userName, publisher.getName());
        return _publisherRepository.insert(publisher);
    }
}
