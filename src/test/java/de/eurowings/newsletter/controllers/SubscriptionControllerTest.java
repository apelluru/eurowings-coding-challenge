package de.eurowings.newsletter.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import de.eurowings.newsletter.TestUtils;
import de.eurowings.newsletter.enums.SubscriptionStatus;
import de.eurowings.newsletter.models.Newsletter;
import de.eurowings.newsletter.models.NewsletterSubscription;
import de.eurowings.newsletter.models.Subscriber;
import de.eurowings.newsletter.vo.SubscriptionInputData;
import de.eurowings.newsletter.services.SubscriptionService;
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
@WebMvcTest(SubscriptionController.class)
public class SubscriptionControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private SubscriptionService subscriptionService;

    @Test
    void subscribe() throws Exception {
        SubscriptionInputData subscriptionInputData = getSubscriptionInputData();
        NewsletterSubscription newsletterSubscription = new NewsletterSubscription();
        newsletterSubscription.setSubscriptionStatus(SubscriptionStatus.SUBSCRIBED);
        when(subscriptionService.subscribe(subscriptionInputData)).thenReturn(newsletterSubscription);

        mvc.perform(MockMvcRequestBuilders
            .post("/api/rest/v1/newsletter/subscribe")
            .content(TestUtils.asJsonString(subscriptionInputData))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(MockMvcResultMatchers.jsonPath("$.newsletterSubscription.subscriptionStatus").value(SubscriptionStatus.SUBSCRIBED.name()));
    }

    @Test
    void unsubscribe() throws Exception {
        SubscriptionInputData subscriptionInputData = getSubscriptionInputData();
        NewsletterSubscription newsletterSubscription = new NewsletterSubscription();
        newsletterSubscription.setSubscriptionStatus(SubscriptionStatus.UNSUBSCRIBED);
        when(subscriptionService.unsubscribe(subscriptionInputData)).thenReturn(newsletterSubscription);

        mvc.perform(MockMvcRequestBuilders
            .put("/api/rest/v1/newsletter/unsubscribe")
            .content(TestUtils.asJsonString(subscriptionInputData))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.newsletterSubscription.subscriptionStatus").value(SubscriptionStatus.UNSUBSCRIBED.name()));
    }

    @Test
    void subscriptionStatus() throws Exception {
        SubscriptionInputData subscriptionInputData = getSubscriptionInputData();
        NewsletterSubscription newsletterSubscription = new NewsletterSubscription();
        newsletterSubscription.setSubscriptionStatus(SubscriptionStatus.SUBSCRIBED);
        when(subscriptionService.subscriptionStatus(subscriptionInputData)).thenReturn(newsletterSubscription);

        mvc.perform(MockMvcRequestBuilders
            .post("/api/rest/v1/newsletter/subscribe/status")
            .content(TestUtils.asJsonString(subscriptionInputData))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.newsletterSubscription.subscriptionStatus").value(SubscriptionStatus.SUBSCRIBED.name()));
    }

    private SubscriptionInputData getSubscriptionInputData() {
        SubscriptionInputData subscriptionInputData = new SubscriptionInputData();
        Subscriber subscriber = new Subscriber();
        subscriber.setEmail("user1@gmail.com");
        subscriptionInputData.setSubscriberData(subscriber);
        Newsletter newsletter = new Newsletter();
        newsletter.setNewsletterId((long) 1001);
        subscriptionInputData.setNewsletterData(newsletter);
        return subscriptionInputData;
    }
}