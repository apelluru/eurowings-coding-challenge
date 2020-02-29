package de.eurowings.newsletter.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Data;

/**
 * Entity model for newsletters
 *
 * @author Ashok Pelluru
 */
@Entity
@Data
@JsonInclude(Include.NON_NULL)
public class Newsletter {

    @Id
    private Long newsletterId;
    private boolean active;
    private String subscriptionName;
    private String description;
    private Date createdDate;
    private Date publicationDate;

    @JsonIgnore
    @OneToMany(mappedBy = "newsletter")
    private List<NewsletterSubscription> subscriberNewsletters;
}
