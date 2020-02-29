package de.eurowings.newsletter.controllers;

import de.eurowings.newsletter.vo.Error;
import de.eurowings.newsletter.models.NewsletterSubscription;
import de.eurowings.newsletter.vo.Result;
import de.eurowings.newsletter.vo.SubscriptionInputData;
import de.eurowings.newsletter.services.SubscriptionService;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Simple REST controller for basic newsletter operations.
 *
 * @author Ashok Pelluru
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/rest/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    /**
     * Method to subscribe newsletters
     *
     * @param subscriptionInputData contains subscriber and newsletter information
     * @return result of newsletter subscription
     */
    @PostMapping("/newsletter/subscribe")
    public ResponseEntity<Result> subscribe(@Valid @RequestBody SubscriptionInputData subscriptionInputData) {
        NewsletterSubscription newsletterSubscription;
        Result result = new Result();
        List<Error> errorList = new ArrayList<>();
        try {
            newsletterSubscription = subscriptionService.subscribe(subscriptionInputData);
            result.setNewsletterSubscription(newsletterSubscription);
            result.setHttpStatus(HttpStatus.CREATED);
        } catch (Exception ex) {
            errorList.add(new Error(ex.getClass().getSimpleName(), ex.getMessage()));
        }
        if (!errorList.isEmpty()) {
            result.setErrors(errorList);
            result.setHttpStatus(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, result.getHttpStatus());
    }

    /**
     * Method to unsubscribe newsletters
     *
     * @param subscriptionInputData contains subscriber email and newsletterId
     * @return result of unsubscription
     */
    @PutMapping("/newsletter/unsubscribe")
    public ResponseEntity unsubscribe(@Valid @RequestBody SubscriptionInputData subscriptionInputData) {
        NewsletterSubscription newsletterSubscription = null;
        Result result = new Result();
        List<Error> errorList = new ArrayList<>();
        try {
            newsletterSubscription = subscriptionService.unsubscribe(subscriptionInputData);
            result.setNewsletterSubscription(newsletterSubscription);
            result.setHttpStatus(HttpStatus.OK);
        } catch (Exception ex) {
            errorList.add(new Error(ex.getClass().getSimpleName(), ex.getMessage()));
        }
        if (!errorList.isEmpty()) {
            result.setErrors(errorList);
            result.setHttpStatus(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Object>(result, result.getHttpStatus());
    }

    /**
     * Method to get the status of subscription
     *
     * @param subscriptionInputData contains email and newsletterId
     * @return status of the newsletterSubscription for provided newsletter and subscriber
     */
    @PostMapping("/newsletter/subscribe/status")
    public ResponseEntity<Result> subscriptionStatus(@Valid @RequestBody SubscriptionInputData subscriptionInputData) {
        NewsletterSubscription newsletterSubscription;
        Result result = new Result();
        List<Error> errorList = new ArrayList<>();
        try {
            newsletterSubscription = subscriptionService.subscriptionStatus(subscriptionInputData);
            result.setNewsletterSubscription(newsletterSubscription);
            result.setHttpStatus(HttpStatus.OK);
        } catch (Exception ex) {
            errorList.add(new Error(ex.getClass().getSimpleName(), ex.getMessage()));
        }
        if (!errorList.isEmpty()) {
            result.setErrors(errorList);
            result.setHttpStatus(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, result.getHttpStatus());
    }
}
