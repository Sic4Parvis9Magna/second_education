package com.sic.parvis.magna.second_education.dto;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.annotation.JsonView;
import com.sic.parvis.magna.second_education.model.Views;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WsEventDto {
    @JsonView(Views.WithData.class)
    private ObjectType objectType;
    @JsonView(Views.WithData.class)
    private EventType eventType;
    @JsonRawValue
    @JsonView(Views.WithData.class)
    private String body;
}
