package com.vinhdd.sbom.api.service;

import com.vinhdd.sbom.api.dto.websocket.NotificationDto;

public interface NotificationService {
    void sendNotification(String destination, NotificationDto dto);
}
