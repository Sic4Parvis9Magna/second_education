package com.sic.parvis.magna.second_education.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonView(Views.Id.class)
public class UserSubscriptionId implements Serializable {
    private String channelId;
    private String subscriberId;
}
