package de.eurowings.newsletter.repositories;

import de.eurowings.newsletter.enums.SubscriptionStatus;
import de.eurowings.newsletter.models.Newsletter;
import de.eurowings.newsletter.models.NewsletterSubscription;
import de.eurowings.newsletter.models.Subscriber;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Ashok Pelluru
 */
@Repository
public interface NewsletterSubscriptionRepository extends JpaRepository<NewsletterSubscription, Long> {

    Optional<NewsletterSubscription> findBySubscriberAndNewsletter(Subscriber subscriber, Newsletter newsletter);

    List<NewsletterSubscription> findAllByNewsletterAndSubscriptionDateBeforeAndSubscriptionStatusIs(Newsletter newsletter, Date subscriptionDate,
        SubscriptionStatus subscriptionStatus);

    List<NewsletterSubscription> findAllByNewsletterAndSubscriptionDateAfterAndSubscriptionStatusIs(Newsletter newsletter, Date subscriptionDate,
        SubscriptionStatus subscriptionStatus);

    List<NewsletterSubscription> findAllByNewsletterAndSubscriptionDateBetweenAndSubscriptionStatusIs(Newsletter newsletter, Date subscriptionDateStart, Date subscriptionDateEnd,
        SubscriptionStatus subscriptionStatus);
}
