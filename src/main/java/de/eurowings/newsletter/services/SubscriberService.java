package de.eurowings.newsletter.services;

import de.eurowings.newsletter.exceptions.NewsletterException;
import de.eurowings.newsletter.models.Subscriber;
import de.eurowings.newsletter.vo.SubscribersInputData;
import java.util.List;

/**
 * @author Ashok Pelluru
 */
public interface SubscriberService {

    List<Subscriber> getSubscribersBeforeSubscriptionDate(SubscribersInputData subscribersInputData) throws NewsletterException;

    List<Subscriber> getSubscribersAfterSubscriptionDate(SubscribersInputData subscribersInputData) throws NewsletterException;

    List<Subscriber> getSubscribersBetweenSubscriptionDate(SubscribersInputData subscribersInputData) throws NewsletterException;
}
