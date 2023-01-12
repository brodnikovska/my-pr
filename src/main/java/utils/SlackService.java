package utils;

import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.webhook.Payload;
import com.github.seratch.jslack.api.webhook.WebhookResponse;

import java.io.IOException;

public abstract class SlackService{
    private static final String SLACK_URL = "https://hooks.slack.com/services/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";

    public void postNotification(String message) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(message);
        Payload payload = Payload.builder().text(stringBuilder.toString()).build();
        try {
            WebhookResponse webhookResponse = Slack.getInstance().send(SLACK_URL, payload);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
