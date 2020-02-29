package de.eurowings.newsletter.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import de.eurowings.newsletter.enums.SubscriptionDateCondition;
import de.eurowings.newsletter.enums.SubscriptionStatus;
import java.util.Date;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Past;
import lombok.Data;

/**
 * @author Ashok Pelluru
 */
@Data
public class SubscribersInputData {

    private Long newsletterId;

    @Enumerated(EnumType.STRING)
    private SubscriptionStatus subscriptionStatus;

    @Past(message = "Should not be future date")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date subscriptionDateStart;
    @Past(message = "Should not be future date")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date subscriptionDateEnd;

    @Enumerated(EnumType.STRING)
    private SubscriptionDateCondition condition;
}

