package de.eurowings.newsletter.builders;

import de.eurowings.newsletter.enums.SubscriptionStatus;
import de.eurowings.newsletter.models.Newsletter;
import de.eurowings.newsletter.models.NewsletterSubscription;
import de.eurowings.newsletter.models.NewsletterSubscriptionKey;
import de.eurowings.newsletter.models.Subscriber;
import java.util.Date;

/**
 *
 * @author Ashok Pelluru
 */
public class NewsletterSubscriptionBuilder {

    /**
     * @param subscriber details
     * @param newsletter details
     * @return newsletterSubscription object
     */
    public static NewsletterSubscription buildNewsletterSubscription(final Subscriber subscriber, final Newsletter newsletter) {
        NewsletterSubscription newsletterSubscription = new NewsletterSubscription();

        NewsletterSubscriptionKey newsletterSubscriptionKey = new NewsletterSubscriptionKey();
        newsletterSubscriptionKey.setNewsletterId(subscriber.getSubscriberId());
        newsletterSubscriptionKey.setNewsletterId(newsletter.getNewsletterId());

        newsletterSubscription.setId(newsletterSubscriptionKey);
        newsletterSubscription.setSubscriber(subscriber);
        newsletterSubscription.setNewsletter(newsletter);
        newsletterSubscription.setSubscriptionDate(new Date());
        newsletterSubscription.setSubscriptionStatus(SubscriptionStatus.SUBSCRIBED);
        return newsletterSubscription;
    }
}
