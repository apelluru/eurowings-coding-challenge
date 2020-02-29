package de.eurowings.newsletter.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.eurowings.newsletter.models.Subscriber;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author Ashok Pelluru
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest
public class SubscriberRepositoryTest {

    @Autowired
    SubscriberRepository subscriberRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void findByEmail() {
        Subscriber subscriber = new Subscriber();
        subscriber.setEmail("ashokmca07@gmail.com");
        subscriber.setFirstname("Ashok");

        Subscriber savedSubscriber = subscriberRepository.save(subscriber);
        subscriberRepository.flush();
        entityManager.clear();

        assertEquals(subscriber.getEmail(), savedSubscriber.getEmail());
    }
}