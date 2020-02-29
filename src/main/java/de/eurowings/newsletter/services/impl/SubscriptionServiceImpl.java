package de.eurowings.newsletter.services.impl;

import de.eurowings.newsletter.builders.NewsletterSubscriptionBuilder;
import de.eurowings.newsletter.enums.SubscriptionStatus;
import de.eurowings.newsletter.exceptions.NewsletterException;
import de.eurowings.newsletter.exceptions.NewsletterNotFoundException;
import de.eurowings.newsletter.exceptions.SubscriberNotFoundException;
import de.eurowings.newsletter.models.Newsletter;
import de.eurowings.newsletter.models.NewsletterSubscription;
import de.eurowings.newsletter.models.Subscriber;
import de.eurowings.newsletter.repositories.NewsletterRepository;
import de.eurowings.newsletter.repositories.NewsletterSubscriptionRepository;
import de.eurowings.newsletter.repositories.SubscriberRepository;
import de.eurowings.newsletter.services.SubscriptionService;
import de.eurowings.newsletter.vo.SubscriptionInputData;
import java.util.Date;
import java.util.Optional;
import javax.annotation.Resource;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

/**
 * Subscription service for newsletters like subscribe/unsubscribe.
 *
 * @author Ashok Pelluru
 */
@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    @Resource
    private SubscriberRepository subscriberRepository;
    @Resource
    private NewsletterRepository newsletterRepository;
    @Resource
    private NewsletterSubscriptionRepository newsletterSubscriptionRepository;

    /**
     * @param subscriptionInputData contains basic data which is required to subscribe
     * @return the NewsletterSubscription object
     * @throws NewsletterException to handle the special cases like http status/messages
     */
    @Transactional
    @Override
    public NewsletterSubscription subscribe(SubscriptionInputData subscriptionInputData) throws NewsletterException {
        NewsletterSubscription newsletterSubscription;
        Subscriber subscriber = subscriptionInputData.getSubscriberData();
        Newsletter newsletter = subscriptionInputData.getNewsletterData();

        subscriber = createOrGetSubscriber(subscriber);
        newsletter = getNewsletter(newsletter);
        Optional<NewsletterSubscription> optionalNewsletterSubscription = newsletterSubscriptionRepository.findBySubscriberAndNewsletter(subscriber, newsletter);
        if (!optionalNewsletterSubscription.isPresent()) {
            newsletterSubscription = NewsletterSubscriptionBuilder.buildNewsletterSubscription(subscriber, newsletter);
            //TODO: Just added for demo purpose only. Because its required to subscribe with past dates.
            if (subscriptionInputData.getSubScriptionDate() != null) {
                newsletterSubscription.setSubscriptionDate(subscriptionInputData.getSubScriptionDate());
            }
            newsletterSubscription = newsletterSubscriptionRepository.save(newsletterSubscription);

        } else {
            newsletterSubscription = optionalNewsletterSubscription.get();
            SubscriptionStatus subscriptionStatus = newsletterSubscription.getSubscriptionStatus();
            if (!subscriptionStatus.equals(SubscriptionStatus.SUBSCRIBED)) {
                newsletterSubscription.setSubscriptionStatus(SubscriptionStatus.SUBSCRIBED);
                newsletterSubscription.setSubscriptionDate(new Date());
                newsletterSubscription = newsletterSubscriptionRepository.save(newsletterSubscription);
            }
        }
        return newsletterSubscription;
    }

    /**
     * @param subscriptionInputData contains basic data which is required to unsubscribe
     * @return the NewsletterSubscription object
     * @throws NewsletterException to handle the special cases like http status/messages
     */
    @Transactional
    @Override
    public NewsletterSubscription unsubscribe(SubscriptionInputData subscriptionInputData) throws NewsletterException {
        NewsletterSubscription newsletterSubscription = subscriptionStatus(subscriptionInputData);
        if (newsletterSubscription != null && newsletterSubscription.getSubscriptionStatus().equals(SubscriptionStatus.SUBSCRIBED)) {
            newsletterSubscription.setSubscriptionStatus(SubscriptionStatus.UNSUBSCRIBED);
            newsletterSubscription.setUnsubscriptionDate(new Date());
            newsletterSubscription = newsletterSubscriptionRepository.save(newsletterSubscription);
        }
        return newsletterSubscription;
    }

    /**
     * @param subscriptionInputData contains basic data which is required to check the status
     * @return the NewsletterSubscription object
     * @throws NewsletterException to handle the special cases like http status/messages
     */
    @Override
    public NewsletterSubscription subscriptionStatus(SubscriptionInputData subscriptionInputData) throws NewsletterException {
        NewsletterSubscription newsletterSubscription = new NewsletterSubscription();
        Subscriber subscriber = subscriptionInputData.getSubscriberData();
        Newsletter newsletter = subscriptionInputData.getNewsletterData();

        subscriber = getSubscriber(subscriber);
        newsletter = getNewsletter(newsletter);

        Optional<NewsletterSubscription> optionalNewsletterSubscription = newsletterSubscriptionRepository.findBySubscriberAndNewsletter(subscriber, newsletter);
        if (optionalNewsletterSubscription.isPresent()) {
            newsletterSubscription = optionalNewsletterSubscription.get();
        } else {
            newsletterSubscription.setSubscriber(subscriber);
            newsletterSubscription.setNewsletter(newsletter);
            newsletterSubscription.setSubscriptionStatus(SubscriptionStatus.NOT_SUBSCRIBED);
        }
        return newsletterSubscription;
    }

    private Subscriber getSubscriber(Subscriber subscriber) throws SubscriberNotFoundException {
        Optional<Subscriber> optionalSubscriber = subscriberRepository.findByEmail(subscriber.getEmail());
        if (!optionalSubscriber.isPresent()) {
            throw new SubscriberNotFoundException("Subscriber doesn't exist");
        } else {
            subscriber = optionalSubscriber.get();
        }
        return subscriber;
    }

    private Newsletter getNewsletter(Newsletter newsletter) throws NewsletterNotFoundException {
        Optional<Newsletter> byId = newsletterRepository.findById(newsletter.getNewsletterId());
        if (!byId.isPresent()) {
            throw new NewsletterNotFoundException("Newsletter doesn't exist");
        } else {
            newsletter = byId.get();
        }
        return newsletter;
    }

    private Subscriber createOrGetSubscriber(Subscriber subscriber) {
        Optional<Subscriber> optionalSubscriber = subscriberRepository.findByEmail(subscriber.getEmail());
        if (!optionalSubscriber.isPresent()) {
            subscriber = subscriberRepository.save(subscriber);
        } else {
            subscriber = optionalSubscriber.get();
        }
        return subscriber;
    }
}
