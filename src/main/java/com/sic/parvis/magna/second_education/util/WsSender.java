package com.sic.parvis.magna.second_education.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sic.parvis.magna.second_education.dto.EventType;
import com.sic.parvis.magna.second_education.dto.ObjectType;
import com.sic.parvis.magna.second_education.dto.WsEventDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.function.BiConsumer;

@Component
@RequiredArgsConstructor
public class WsSender {
    private static final String TOPIC_ACTIVITY = "/topic/activity";

    private final SimpMessagingTemplate template;
    private final ObjectMapper mapper;

    public <T> BiConsumer<EventType, T> getSender(ObjectType objectType, Class view) {
//        mapper.setConfig(mapper.getSerializationConfig().withView(view));

        return (EventType eventType, T payload) -> {
            String value = null;
            try {
                value = mapper.writeValueAsString(payload);
            } catch (JsonProcessingException ex) {
                throw  new RuntimeException(ex);
            }
            template.convertAndSend(TOPIC_ACTIVITY, new WsEventDto(objectType, eventType, value));
        };
    }
}
