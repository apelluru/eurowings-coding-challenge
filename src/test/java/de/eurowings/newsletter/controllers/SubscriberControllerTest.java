package de.eurowings.newsletter.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import de.eurowings.newsletter.TestUtils;
import de.eurowings.newsletter.enums.SubscriptionDateCondition;
import de.eurowings.newsletter.enums.SubscriptionStatus;
import de.eurowings.newsletter.models.Subscriber;
import de.eurowings.newsletter.services.SubscriberService;
import de.eurowings.newsletter.vo.SubscribersInputData;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * @author Ashok Pelluru
 */
@WebMvcTest(SubscriberController.class)
public class SubscriberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SubscriberService subscriberService;

    @Test
    void getSubscriptionCustomersBeforeSubscriptionDate() throws Exception {
        //After subscriptionDate
        SubscribersInputData subscribersInputData = new SubscribersInputData();
        subscribersInputData.setSubscriptionStatus(SubscriptionStatus.SUBSCRIBED);
        subscribersInputData.setCondition(SubscriptionDateCondition.BEFORE);

        List<Subscriber> subscriberList = getSubscribers();
        when(subscriberService.getSubscribersBeforeSubscriptionDate(subscribersInputData)).thenReturn(subscriberList);

        mockMvc.perform(MockMvcRequestBuilders
            .post("/api/rest/v1/newsletter/customers")
            .content(TestUtils.asJsonString(subscribersInputData))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.subscribers[*].email").value("user1@gmail.com"));
    }

    @Test
    void getSubscriptionCustomersAfterSubscriptionDate() throws Exception {
        //Before subscriptionDate
        SubscribersInputData subscribersInputData = new SubscribersInputData();
        subscribersInputData.setSubscriptionStatus(SubscriptionStatus.SUBSCRIBED);
        subscribersInputData.setCondition(SubscriptionDateCondition.AFTER);

        List<Subscriber> subscriberList = getSubscribers();
        when(subscriberService.getSubscribersAfterSubscriptionDate(subscribersInputData)).thenReturn(subscriberList);

        mockMvc.perform(MockMvcRequestBuilders
            .post("/api/rest/v1/newsletter/customers")
            .content(TestUtils.asJsonString(subscribersInputData))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.subscribers[*].email").value("user1@gmail.com"));

    }

    @Test
    void getSubscriptionCustomersBetweenSubscriptionDate() throws Exception {
        //Before subscriptionDate
        SubscribersInputData subscribersInputData = new SubscribersInputData();
        subscribersInputData.setSubscriptionStatus(SubscriptionStatus.SUBSCRIBED);
        subscribersInputData.setCondition(SubscriptionDateCondition.BETWEEN);

        List<Subscriber> subscriberList = getSubscribers();
        when(subscriberService.getSubscribersBetweenSubscriptionDate(subscribersInputData)).thenReturn(subscriberList);

        mockMvc.perform(MockMvcRequestBuilders
            .post("/api/rest/v1/newsletter/customers")
            .content(TestUtils.asJsonString(subscribersInputData))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.subscribers[*].email").value("user1@gmail.com"));

    }

    private List<Subscriber> getSubscribers() {
        List<Subscriber> subscriberList = new ArrayList<>();
        Subscriber subscriber = new Subscriber();
        subscriber.setEmail("user1@gmail.com");
        subscriberList.add(subscriber);
        return subscriberList;
    }
}