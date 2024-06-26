package cz.fel.cvut.behelpdesk.service;

import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import com.microsoft.graph.models.*;
import com.microsoft.graph.requests.GraphServiceClient;
import cz.fel.cvut.behelpdesk.dao.EmailRequest;
import okhttp3.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class EmailService {

    private static ClientSecretCredential clientSecretCredential;
    private static GraphServiceClient<Request> appClient;

    @Value("${spring.security.oauth2.client.registration.azure.client-id}")
    private String clientId;

    @Value("${microsoft.azure.tenant-id}")
    private String tenantId;

    @Value("${spring.security.oauth2.client.registration.azure.client-secret}")
    private String clientSecret;

    @Value("${spring.mail.username}")
    private String sender;

    Logger log = LoggerFactory.getLogger(getClass());

    @Async
    public void sendEmail(EmailRequest emailRequest) {

        // establish the connection with M-Graph
        try {
            initializeGraphForAppOnlyAuth();
        } catch (Exception e) {
            log.error("Error initializing Graph for user auth");
            log.error(e.getMessage());
        }

        try {
            sendMailGraphAPI(emailRequest);
            log.info("Mail sent successfully.");
        } catch (Exception e) {
            log.error("Error sending mail");
            log.error(e.getMessage());
        }

    }

    private void initializeGraphForAppOnlyAuth() throws Exception {

        log.info("initializing Graph for user auth");

        clientSecretCredential = new ClientSecretCredentialBuilder().clientId(clientId).tenantId(tenantId)
                .clientSecret(clientSecret).build();

        if (appClient == null) {
            // Use the .default scope when using app-only auth
            final TokenCredentialAuthProvider authProvider = new TokenCredentialAuthProvider(
                    List.of("https://graph.microsoft.com/.default"), clientSecretCredential);

            appClient = GraphServiceClient.builder().authenticationProvider(authProvider).buildClient();
        }

    }

    private void sendMailGraphAPI(EmailRequest emailRequest) throws Exception {

        log.info("Preparing email");

        // Ensure client isn't null
        if (appClient  == null) {
            throw new Exception("Graph has not been initialized for user auth");
        }

        Message message = new Message();

        message.subject = emailRequest.getSubject();

        ItemBody body = new ItemBody();
        body.contentType = BodyType.HTML;
        body.content = emailRequest.getMessage();
        message.body = body;

        LinkedList<Recipient> toRecipientsList = new LinkedList<>();
        for (String recipient : emailRequest.getRecipients()) {
            Recipient toRecipients = new Recipient();
            EmailAddress emailAddress = new EmailAddress();
            emailAddress.address = recipient;
            toRecipients.emailAddress = emailAddress;
            toRecipientsList.add(toRecipients);
        }

        message.toRecipients = toRecipientsList;

        // Send the message
        log.info("sending email");
        appClient.users(sender)
                .sendMail(UserSendMailParameterSet.newBuilder()
                        .withMessage(message)
                        .build())
                .buildRequest()
                .post();

    }

}