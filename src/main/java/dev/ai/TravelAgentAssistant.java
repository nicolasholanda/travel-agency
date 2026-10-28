package dev.ai;

import io.quarkiverse.langchain4j.RegisterAiService;

@RegisterAiService
public interface TravelAgentAssistant {

    /**
     * Generates a response to the user's message.
     *
     * @param userMessage The message from the user.
     * @return The generated response.
     */
    String chat(String userMessage);
}
