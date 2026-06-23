package com.clinical.manager.health.checks.Service;



import lombok.RequiredArgsConstructor;

import org.springframework.messaging.simp.SimpMessagingTemplate;

import org.springframework.stereotype.Service;

/*
 * Sends real-time queue updates.
 */
@Service
@RequiredArgsConstructor
public class QueueNotificationService {

    /*
     * Sends WebSocket messages.
     */
    private final SimpMessagingTemplate messagingTemplate;

    /*
     * Broadcast queue updates.
     */
    public void sendQueueUpdate(
            Object payload) {

        messagingTemplate.convertAndSend(

                "/topic/queue",

                payload
        );
    }

    /*
     * Broadcast dashboard updates.
     */
    public void sendDashboardUpdate(
            Object payload) {

        messagingTemplate.convertAndSend(

                "/topic/dashboard",

                payload
        );
    }
}
