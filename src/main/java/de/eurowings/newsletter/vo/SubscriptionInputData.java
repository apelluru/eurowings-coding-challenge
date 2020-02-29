package de.eurowings.newsletter.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import de.eurowings.newsletter.models.Newsletter;
import de.eurowings.newsletter.models.Subscriber;
import java.util.Date;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author Ashok Pelluru
 */
@Data
@JsonInclude(Include.NON_NULL)
public class SubscriptionInputData {

    @NotNull
    private Subscriber subscriberData;
    @NotNull
    private Newsletter newsletterData;

    //temp
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date subScriptionDate;
}

