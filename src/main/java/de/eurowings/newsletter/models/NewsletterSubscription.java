package de.eurowings.newsletter.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import de.eurowings.newsletter.enums.SubscriptionStatus;
import java.util.Date;
import java.util.List;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import lombok.Data;

/**
 * Entity model for newsletter subscriptions (like subscriptionDate and status like subscribed/unsubscribed)
 *
 * @author Ashok Pelluru
 */
@Data
@Entity
public class NewsletterSubscription {

    @ManyToOne
    @MapsId("subscriberId")
    @JoinColumn(name = "subscriber_id")
    Subscriber subscriber;

    @ManyToOne
    @MapsId("newsletterId")
    @JoinColumn(name = "newsletter_id")
    Newsletter newsletter;

    @JsonIgnore
    @EmbeddedId
    private NewsletterSubscriptionKey id;

    private Date subscriptionDate;
    private Date unsubscriptionDate;

    @Enumerated(EnumType.STRING)
    private SubscriptionStatus subscriptionStatus;
}
