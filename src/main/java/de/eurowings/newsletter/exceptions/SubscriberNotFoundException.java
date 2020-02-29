package de.eurowings.newsletter.exceptions;

/**
 * @author Ashok Pelluru
 */
public class SubscriberNotFoundException extends NewsletterException {

    public SubscriberNotFoundException(String message) {
        super(message);
    }
}
