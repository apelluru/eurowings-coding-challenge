package de.eurowings.newsletter.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import de.eurowings.newsletter.enums.SubscriptionStatus;
import de.eurowings.newsletter.models.Newsletter;
import de.eurowings.newsletter.models.NewsletterSubscription;
import de.eurowings.newsletter.models.Subscriber;
import de.eurowings.newsletter.vo.SubscribersInputData;
import de.eurowings.newsletter.vo.SubscriptionInputData;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Ashok Pelluru
 */
@SpringBootTest
public class SubscriberServiceTest {

    @Autowired
    SubscriberService subscriberService;

    @Autowired
    SubscriptionService subscriptionService;

    @Test
    void subscribe() throws Exception {
        //Subscriber 1
        SubscriptionInputData subscriptionInputData = getSubscriptionInputData("user1@gmail.com");
        subscriptionInputData.setSubScriptionDate(new GregorianCalendar(2020, Calendar.FEBRUARY, 21).getTime());
        NewsletterSubscription subscription = subscriptionService.subscribe(subscriptionInputData);
        assertEquals(SubscriptionStatus.SUBSCRIBED.name(), subscription.getSubscriptionStatus().name());

        //Subscriber 2
        subscriptionInputData = getSubscriptionInputData("user2@gmail.com");
        subscriptionInputData.setSubScriptionDate(new GregorianCalendar(2020, Calendar.FEBRUARY, 22).getTime());
        subscription = subscriptionService.subscribe(subscriptionInputData);
        assertEquals(SubscriptionStatus.SUBSCRIBED.name(), subscription.getSubscriptionStatus().name());

        //Subscriber 3
        subscriptionInputData = getSubscriptionInputData("user3@gmail.com");
        subscriptionInputData.setSubScriptionDate(new GregorianCalendar(2020, Calendar.FEBRUARY, 23).getTime());
        subscription = subscriptionService.subscribe(subscriptionInputData);
        assertEquals(SubscriptionStatus.SUBSCRIBED.name(), subscription.getSubscriptionStatus().name());
    }

    @Test
    void getSubscribersBeforeSubscriptionDate() throws Exception {
        subscribe();
        SubscribersInputData subscribersInputData = getSubscriptionInputData(new GregorianCalendar(2020, Calendar.FEBRUARY, 22).getTime(), null);
        List<Subscriber> subscribers = subscriberService.getSubscribersBeforeSubscriptionDate(subscribersInputData);

        assertFalse(subscribers.isEmpty());
    }

    @Test
    void getSubscribersAfterSubscriptionDate() throws Exception {

        subscribe();
        SubscribersInputData subscribersInputData = getSubscriptionInputData(new GregorianCalendar(2020, Calendar.FEBRUARY, 22).getTime(), null);
        List<Subscriber> subscribers = subscriberService.getSubscribersAfterSubscriptionDate(subscribersInputData);

        assertFalse(subscribers.isEmpty());
    }

    @Test
    void getSubscribersBetweenSubscriptionDate() throws Exception {
        subscribe();
        SubscribersInputData subscribersInputData = getSubscriptionInputData(new GregorianCalendar(2020, Calendar.FEBRUARY, 21).getTime(),
            new GregorianCalendar(2020, Calendar.FEBRUARY, 23).getTime());
        List<Subscriber> subscribers = subscriberService.getSubscribersBetweenSubscriptionDate(subscribersInputData);

        assertFalse(subscribers.isEmpty());
    }

    private SubscriptionInputData getSubscriptionInputData(String email) {
        SubscriptionInputData subscriptionInputData = new SubscriptionInputData();
        Subscriber subscriber = new Subscriber();
        subscriber.setEmail(email);
        subscriptionInputData.setSubscriberData(subscriber);
        Newsletter newsletter = new Newsletter();
        newsletter.setNewsletterId((long) 1001);
        subscriptionInputData.setNewsletterData(newsletter);
        return subscriptionInputData;
    }

    private SubscribersInputData getSubscriptionInputData(Date startDate, Date endDate) {
        SubscribersInputData subscribersInputData = new SubscribersInputData();
        if (endDate != null) {
            subscribersInputData.setSubscriptionDateEnd(endDate);
        }
        subscribersInputData.setSubscriptionDateStart(startDate);
        subscribersInputData.setSubscriptionStatus(SubscriptionStatus.SUBSCRIBED);
        subscribersInputData.setNewsletterId((long) 1001);

        return subscribersInputData;
    }
}