package com.vinhdd.sbom.api.service.impl;

import com.vinhdd.sbom.api.dto.websocket.NotificationDto;
import com.vinhdd.sbom.api.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public void sendNotification(String destination, NotificationDto notification) {
        log.info("Sending notification...");
        messagingTemplate.convertAndSend(destination, notification);
    }
}
