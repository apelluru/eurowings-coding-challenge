package de.eurowings.newsletter.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import de.eurowings.newsletter.enums.SubscriptionStatus;
import de.eurowings.newsletter.exceptions.NewsletterNotFoundException;
import de.eurowings.newsletter.exceptions.SubscriberNotFoundException;
import de.eurowings.newsletter.models.Newsletter;
import de.eurowings.newsletter.models.NewsletterSubscription;
import de.eurowings.newsletter.models.Subscriber;
import de.eurowings.newsletter.vo.SubscriptionInputData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Ashok Pelluru
 */
@SpringBootTest
public class SubscriptionServiceTest {

    @Autowired
    SubscriptionService subscriptionService;

    @Test
    void subscribe() throws Exception {
        SubscriptionInputData subscriptionInputData = getSubscriptionInputData();
        NewsletterSubscription subscription = subscriptionService.subscribe(subscriptionInputData);

        assertEquals(SubscriptionStatus.SUBSCRIBED.name(), subscription.getSubscriptionStatus().name());
    }

    @Test
    void subscribe_with_invalid_newsletter() {
        SubscriptionInputData subscriptionInputData = getSubscriptionInputData();
        subscriptionInputData.getNewsletterData().setNewsletterId((long) 2001);

        NewsletterNotFoundException newsletterNotFoundException = assertThrows(NewsletterNotFoundException.class, () -> {
            subscriptionService.subscribe(subscriptionInputData);
        });
        assertEquals("Newsletter doesn't exist", newsletterNotFoundException.getMessage());
    }

    @Test
    void unsubscribe() throws Exception {
        subscribe();
        SubscriptionInputData subscriptionInputData = getSubscriptionInputData();
        NewsletterSubscription subscription = subscriptionService.unsubscribe(subscriptionInputData);

        assertEquals(SubscriptionStatus.UNSUBSCRIBED.name(), subscription.getSubscriptionStatus().name());
    }

    @Test
    void unsubscribe_with_invalid_newsletter() throws Exception {
        subscribe();
        SubscriptionInputData subscriptionInputData = getSubscriptionInputData();
        subscriptionInputData.getNewsletterData().setNewsletterId((long) 2001);
        NewsletterNotFoundException newsletterNotFoundException = assertThrows(NewsletterNotFoundException.class, () -> {
            subscriptionService.unsubscribe(subscriptionInputData);
        });
        assertEquals("Newsletter doesn't exist", newsletterNotFoundException.getMessage());
    }

    @Test
    void unsubscribe_with_invalid_subscriber() throws Exception {
        subscribe();
        SubscriptionInputData subscriptionInputData = getSubscriptionInputData();
        subscriptionInputData.getSubscriberData().setEmail("user21@gmail.com");
        SubscriberNotFoundException subscriberNotFoundException = assertThrows(SubscriberNotFoundException.class, () -> {
            subscriptionService.unsubscribe(subscriptionInputData);
        });
        assertEquals("Subscriber doesn't exist", subscriberNotFoundException.getMessage());
    }

    @Test
    void subscriptionStatus() throws Exception {
        subscribe();
        SubscriptionInputData subscriptionInputData = getSubscriptionInputData();
        NewsletterSubscription subscription = subscriptionService.subscriptionStatus(subscriptionInputData);

        assertEquals(SubscriptionStatus.SUBSCRIBED.name(), subscription.getSubscriptionStatus().name());
    }

    private SubscriptionInputData getSubscriptionInputData() {
        SubscriptionInputData subscriptionInputData = new SubscriptionInputData();
        Subscriber subscriber = new Subscriber();
        subscriber.setEmail("user1@gmail.com");
        subscriptionInputData.setSubscriberData(subscriber);
        Newsletter newsletter = new Newsletter();
        newsletter.setNewsletterId((long) 1001);
        subscriptionInputData.setNewsletterData(newsletter);
        return subscriptionInputData;
    }
}