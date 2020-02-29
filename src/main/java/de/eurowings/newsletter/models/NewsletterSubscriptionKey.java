package de.eurowings.newsletter.models;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Data;

/**
 * @author Ashok Pelluru
 */
@Embeddable
@Data
public class NewsletterSubscriptionKey implements Serializable {

    @Column(name = "subscriber_id")
    Long subscriberId;

    @Column(name = "newsletter_id")
    Long newsletterId;
}
