package de.eurowings.newsletter.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import de.eurowings.newsletter.builders.NewsletterSubscriptionBuilder;
import de.eurowings.newsletter.enums.SubscriptionStatus;
import de.eurowings.newsletter.models.Newsletter;
import de.eurowings.newsletter.models.NewsletterSubscription;
import de.eurowings.newsletter.models.Subscriber;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
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
public class NewsletterSubscriptionRepositoryTest {

    @Autowired
    SubscriberRepository subscriberRepository;
    @PersistenceContext
    EntityManager entityManager;
    @Autowired
    NewsletterRepository newsletterRepository;
    @Autowired
    NewsletterSubscriptionRepository newsletterSubscriptionRepository;

    Newsletter newsletter;

    @BeforeEach
    void setUpData() {
        Optional<Newsletter> optionalNewsletter = newsletterRepository.findById(Long.valueOf(1001));
        newsletter = optionalNewsletter.get();

        //Customer 1
        Subscriber subscriber = new Subscriber();
        subscriber.setEmail("user1@gmail.com");
        subscriber.setFirstname("User1");
        subscriber = subscriberRepository.save(subscriber);
        NewsletterSubscription newsletterSubscription = NewsletterSubscriptionBuilder.buildNewsletterSubscription(subscriber, newsletter);
        newsletterSubscription.setSubscriptionDate(new GregorianCalendar(2020, Calendar.FEBRUARY, 21).getTime());
        newsletterSubscriptionRepository.save(newsletterSubscription);

        //Customer 2
        subscriber = new Subscriber();
        subscriber.setEmail("user2@gmail.com");
        subscriber.setFirstname("User2");
        subscriber = subscriberRepository.save(subscriber);
        newsletterSubscription = NewsletterSubscriptionBuilder.buildNewsletterSubscription(subscriber, newsletter);
        newsletterSubscription.setSubscriptionDate(new GregorianCalendar(2020, Calendar.FEBRUARY, 22).getTime());
        newsletterSubscriptionRepository.save(newsletterSubscription);

        //Customer 3
        subscriber = new Subscriber();
        subscriber.setEmail("user3@gmail.com");
        subscriber.setFirstname("User3");
        subscriber = subscriberRepository.save(subscriber);
        newsletterSubscription = NewsletterSubscriptionBuilder.buildNewsletterSubscription(subscriber, newsletter);
        newsletterSubscription.setSubscriptionDate(new GregorianCalendar(2020, Calendar.FEBRUARY, 23).getTime());
        newsletterSubscriptionRepository.save(newsletterSubscription);

        subscriberRepository.flush();
        newsletterSubscriptionRepository.flush();
        entityManager.clear();
    }

    @Test
    void findBySubscriberAndNewsletter() {
        Optional<Subscriber> optionalSubscriber = subscriberRepository.findByEmail("user1@gmail.com");
        Optional<NewsletterSubscription> newsletterSubscription = newsletterSubscriptionRepository.findBySubscriberAndNewsletter(optionalSubscriber.get(), newsletter);

        assertEquals(SubscriptionStatus.SUBSCRIBED.name(), newsletterSubscription.get().getSubscriptionStatus().name());
    }

    @Test
    void findAllByNewsletterAndSubscriptionDateBeforeAndSubscriptionStatusIs() {
        List<NewsletterSubscription> newsletterSubscriptionList = newsletterSubscriptionRepository
            .findAllByNewsletterAndSubscriptionDateBeforeAndSubscriptionStatusIs(newsletter, new GregorianCalendar(2020, Calendar.FEBRUARY, 22).getTime(),
                SubscriptionStatus.SUBSCRIBED);

        assertFalse(newsletterSubscriptionList.isEmpty());
        NewsletterSubscription newsletterSubscription = newsletterSubscriptionList.get(0);

        assertEquals("user1@gmail.com", newsletterSubscription.getSubscriber().getEmail());
    }

    @Test
    void findAllByNewsletterAndSubscriptionDateAfterAndSubscriptionStatusIs() {
        List<NewsletterSubscription> newsletterSubscriptionList = newsletterSubscriptionRepository
            .findAllByNewsletterAndSubscriptionDateAfterAndSubscriptionStatusIs(newsletter, new GregorianCalendar(2020, Calendar.FEBRUARY, 22).getTime(),
                SubscriptionStatus.SUBSCRIBED);

        assertFalse(newsletterSubscriptionList.isEmpty());
        NewsletterSubscription newsletterSubscription = newsletterSubscriptionList.get(0);

        assertEquals("user3@gmail.com", newsletterSubscription.getSubscriber().getEmail());
    }

    @Test
    void findAllByNewsletterAndSubscriptionDateBetweenAndSubscriptionStatusIs() {
        List<NewsletterSubscription> newsletterSubscriptionList = newsletterSubscriptionRepository
            .findAllByNewsletterAndSubscriptionDateBetweenAndSubscriptionStatusIs(newsletter, new GregorianCalendar(2020, Calendar.FEBRUARY, 21).getTime(),
                new GregorianCalendar(2020, Calendar.FEBRUARY, 23).getTime(),
                SubscriptionStatus.SUBSCRIBED);

        assertFalse(newsletterSubscriptionList.isEmpty());
        assertFalse(newsletterSubscriptionList.size() < 2);
        NewsletterSubscription newsletterSubscription = newsletterSubscriptionList.get(1);

        assertEquals("user2@gmail.com", newsletterSubscription.getSubscriber().getEmail());
    }
}