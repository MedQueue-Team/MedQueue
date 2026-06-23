package com.clinical.manager.health.checks.Config;



import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/*
 * Configures WebSocket messaging.
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig
        implements WebSocketMessageBrokerConfigurer {

    /*
     * Configure message broker.
     */
    @Override
    public void configureMessageBroker(

            MessageBrokerRegistry registry) {

        /*
         * Topic used for broadcasting.
         */
        registry.enableSimpleBroker("/topic");

        /*
         * Prefix for messages from clients.
         */
        registry.setApplicationDestinationPrefixes(
                "/app"
        );
    }

    /*
     * Register WebSocket endpoint.
     */
    @Override
    public void registerStompEndpoints(

            StompEndpointRegistry registry) {

        registry.addEndpoint("/ws")

                /*
                 * Allow frontend connections.
                 */
                .setAllowedOriginPatterns("*")

                /*
                 * Fallback for unsupported browsers.
                 */
                .withSockJS();
    }
}