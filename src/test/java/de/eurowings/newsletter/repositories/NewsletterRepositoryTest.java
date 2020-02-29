package de.eurowings.newsletter.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.eurowings.newsletter.models.Newsletter;
import java.util.Optional;
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
public class NewsletterRepositoryTest {

    @Autowired
    private NewsletterRepository newsletterRepository;

    @Test
    void findById() {
        Optional<Newsletter> optionalNewsletter = newsletterRepository.findById(Long.valueOf(1001));
        assertEquals("Basic", optionalNewsletter.get().getSubscriptionName());
    }
}