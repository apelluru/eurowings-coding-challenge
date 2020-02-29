package de.eurowings.newsletter.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 * @author Ashok Pelluru
 */
@Data
@JsonInclude(Include.NON_NULL)
@Entity
public class Subscriber {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long subscriberId;
    @Size(max = 50, message = "First name must be less than 50 characters")
    private String firstname;
    @Size(max = 50, message = "Last name must be less than 50 characters")
    private String lastname;

    @Email(message = "Email should be valid")
    private String email;

    @JsonIgnore
    @OneToMany(mappedBy = "subscriber")
    private List<NewsletterSubscription> newsletterSubscriptions;
}
