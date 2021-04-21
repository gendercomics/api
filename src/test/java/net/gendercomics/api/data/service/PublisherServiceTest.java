package net.gendercomics.api.data.service;

import java.util.ArrayList;
import java.util.List;

import net.gendercomics.api.data.repository.PublisherRepository;
import net.gendercomics.api.model.Publisher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {PublisherServiceTest.TestContextConfiguration.class, PublisherService.class})
public class PublisherServiceTest {

    @Autowired
    private PublisherService _publisherService;

    @Autowired
    private PublisherRepository _publisherRepository;

    @Test
    public void findAll() {
        List<Publisher> publishers = new ArrayList<>();
        publishers.add(new Publisher());
        publishers.get(0).setName("publisher");

        when(_publisherRepository.findAll()).thenReturn(publishers);

        List<Publisher> publisherList = _publisherService.findAll();
        assertNotNull(publisherList);
        assertEquals("publisher", publisherList.get(0).getName());
    }

    @Test
    public void getPublisher() {
        Publisher publisher = new Publisher();
        publisher.setId("id");

        when(_publisherRepository.findById("id")).thenReturn(java.util.Optional.of(publisher));

        Publisher fetchedPublisher = _publisherService.getPublisher("id");
        assertEquals("id", fetchedPublisher.getId());
    }

    @Test
    public void insert() {
        Publisher publisher = new Publisher();
        when(_publisherRepository.insert((Publisher) any())).thenReturn(publisher);

        Publisher insertedPublisher = _publisherService.insert(publisher, "userName");
        assertNotNull(insertedPublisher.getMetaData().getCreatedOn());
        assertEquals("userName", insertedPublisher.getMetaData().getCreatedBy());
    }

    @Test
    public void save() {
        Publisher publisher = new Publisher();
        when(_publisherRepository.save(any())).thenReturn(publisher);

        Publisher savedPublisher = _publisherService.save(publisher, "userName");
        assertNotNull(publisher.getMetaData().getChangedOn());
        assertEquals("userName", savedPublisher.getMetaData().getChangedBy());
    }

    @TestConfiguration
    static class TestContextConfiguration {

        @Bean
        public PublisherRepository publisherRepository() {
            return mock(PublisherRepository.class);
        }
    }
}