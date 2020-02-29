package de.eurowings.newsletter.services.impl;

import de.eurowings.newsletter.exceptions.NewsletterException;
import de.eurowings.newsletter.exceptions.NewsletterNotFoundException;
import de.eurowings.newsletter.models.Newsletter;
import de.eurowings.newsletter.models.NewsletterSubscription;
import de.eurowings.newsletter.models.Subscriber;
import de.eurowings.newsletter.vo.SubscribersInputData;
import de.eurowings.newsletter.repositories.NewsletterRepository;
import de.eurowings.newsletter.repositories.NewsletterSubscriptionRepository;
import de.eurowings.newsletter.services.SubscriberService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * SubscriberService to fetch the subscribers based on date and subscription status
 *
 * @author Ashok Pelluru
 */
@Service
public class SubscriberServiceImpl implements SubscriberService {

    @Resource
    private NewsletterRepository newsletterRepository;
    @Resource
    private NewsletterSubscriptionRepository newsletterSubscriptionRepository;

    /**
     * @param subscribersInputData contains input data like date and status
     * @return list of subscribers
     * @throws NewsletterException to handle the exception cases
     */
    @Override
    public List<Subscriber> getSubscribersBeforeSubscriptionDate(SubscribersInputData subscribersInputData) throws NewsletterException {
        Newsletter newsletter = getNewsletter(subscribersInputData);
        List<NewsletterSubscription> newsletterSubscriptions = newsletterSubscriptionRepository
            .findAllByNewsletterAndSubscriptionDateBeforeAndSubscriptionStatusIs(newsletter, subscribersInputData.getSubscriptionDateStart(),
                subscribersInputData.getSubscriptionStatus());
        return extractSubscribers(newsletterSubscriptions);
    }

    /**
     * @param subscribersInputData contains input data like date and status
     * @return list of subscribers
     * @throws NewsletterException to handle the exception cases
     */
    @Override
    public List<Subscriber> getSubscribersAfterSubscriptionDate(SubscribersInputData subscribersInputData) throws NewsletterException {
        Newsletter newsletter = getNewsletter(subscribersInputData);
        List<NewsletterSubscription> newsletterSubscriptions = newsletterSubscriptionRepository
            .findAllByNewsletterAndSubscriptionDateAfterAndSubscriptionStatusIs(newsletter, subscribersInputData.getSubscriptionDateStart(),
                subscribersInputData.getSubscriptionStatus());
        return extractSubscribers(newsletterSubscriptions);
    }

    /**
     * @param subscribersInputData contains input data like date and status
     * @return list of subscribers
     * @throws NewsletterException to handle the exception cases
     */
    @Override
    public List<Subscriber> getSubscribersBetweenSubscriptionDate(SubscribersInputData subscribersInputData) throws NewsletterException {

        Newsletter newsletter = getNewsletter(subscribersInputData);
        List<NewsletterSubscription> newsletterSubscriptions = newsletterSubscriptionRepository
            .findAllByNewsletterAndSubscriptionDateBetweenAndSubscriptionStatusIs(newsletter, subscribersInputData.getSubscriptionDateStart(),
                subscribersInputData.getSubscriptionDateEnd(),
                subscribersInputData.getSubscriptionStatus());
        return extractSubscribers(newsletterSubscriptions);
    }

    private List<Subscriber> extractSubscribers(List<NewsletterSubscription> newsletterSubscriptions) {
        List<Subscriber> subscribers = new ArrayList<>();
        if (!newsletterSubscriptions.isEmpty()) {
            for (NewsletterSubscription newsletterSubscription : newsletterSubscriptions) {
                subscribers.add(newsletterSubscription.getSubscriber());
            }
        }
        return subscribers;
    }

    private Newsletter getNewsletter(SubscribersInputData subscribersInputData) throws NewsletterNotFoundException {
        Long newsletterId = subscribersInputData.getNewsletterId();
        Optional<Newsletter> optionalNewsletter = newsletterRepository.findById(newsletterId);
        if (!optionalNewsletter.isPresent()) {
            throw new NewsletterNotFoundException("Newsletters doesn't exist");
        }
        return optionalNewsletter.get();
    }
}
