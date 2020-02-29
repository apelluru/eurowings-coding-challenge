package de.eurowings.newsletter.controllers;

import de.eurowings.newsletter.enums.SubscriptionDateCondition;
import de.eurowings.newsletter.models.Subscriber;
import de.eurowings.newsletter.services.SubscriberService;
import de.eurowings.newsletter.vo.Error;
import de.eurowings.newsletter.vo.Result;
import de.eurowings.newsletter.vo.SubscribersInputData;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Simple REST controller for customer operations.
 *
 * @author Ashok Pelluru
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/rest/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class SubscriberController {

    private SubscriberService subscriberService;

    SubscriberController(SubscriberService subscriberService) {
        this.subscriberService = subscriberService;
    }

    /**
     * Method to get the subscription customers based on conditions
     *
     * @param subscribersInputData like newsletterId, status, start and end dates.
     * @return result
     */
    @PostMapping("/newsletter/customers")
    public ResponseEntity<Result> getSubscriptionCustomers(@Valid @RequestBody SubscribersInputData subscribersInputData) {
        Result result = new Result();
        List<Error> errorList = new ArrayList<>();
        try {
            List<Subscriber> subscribers = null;
            String condtion = subscribersInputData.getCondition().name();
            if (condtion.equals(SubscriptionDateCondition.BEFORE.name())) {
                subscribers = subscriberService.getSubscribersBeforeSubscriptionDate(subscribersInputData);
            } else if (condtion.equals(SubscriptionDateCondition.AFTER.name())) {
                subscribers = subscriberService.getSubscribersAfterSubscriptionDate(subscribersInputData);
            } else if (condtion.equals(SubscriptionDateCondition.BETWEEN.name())) {
                subscribers = subscriberService.getSubscribersBetweenSubscriptionDate(subscribersInputData);
            } else {
                result.setHttpStatus(HttpStatus.BAD_REQUEST);
            }
            if (subscribers != null) {
                result.setSubscribers(subscribers);
                result.setHttpStatus(HttpStatus.OK);
            }
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
