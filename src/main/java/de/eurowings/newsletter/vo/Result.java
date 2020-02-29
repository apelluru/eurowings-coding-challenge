package de.eurowings.newsletter.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import de.eurowings.newsletter.models.NewsletterSubscription;
import de.eurowings.newsletter.models.Subscriber;
import java.util.List;
import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * @author Ashok Pelluru
 */
@Data
public class Result {

    @JsonIgnore
    private HttpStatus httpStatus;

    @JsonInclude(Include.NON_NULL)
    private List<Error> errors;

    @JsonInclude(Include.NON_NULL)
    private NewsletterSubscription newsletterSubscription;

    @JsonInclude(Include.NON_NULL)
    private List<Subscriber> subscribers;

    public int getStatus() {
        return httpStatus.value();
    }
}
