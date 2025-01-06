package com.vinhdd.sbom.api.dto.websocket;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificationDto {
    private String type;
    private String message;
    private String description;
    private Long timestamp;
}
