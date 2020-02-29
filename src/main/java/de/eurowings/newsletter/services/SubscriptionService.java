package de.eurowings.newsletter.services;

import de.eurowings.newsletter.models.NewsletterSubscription;
import de.eurowings.newsletter.vo.SubscriptionInputData;

/**
 * @author Ashok Pelluru
 */
public interface SubscriptionService {

    NewsletterSubscription subscribe(SubscriptionInputData subscriptionInputData) throws Exception;

    NewsletterSubscription unsubscribe(SubscriptionInputData subscriptionInputData) throws Exception;

    NewsletterSubscription subscriptionStatus(SubscriptionInputData subscriptionInputData) throws Exception;
}
