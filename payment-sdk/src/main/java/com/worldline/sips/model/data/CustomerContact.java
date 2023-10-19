package com.worldline.sips.model.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder(alphabetic = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerContact extends Contact {
    private String initials;
    private String legalId;
    private String positionOccupied;

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public String getLegalId() {
        return legalId;
    }

    public void setLegalId(String legalId) {
        this.legalId = legalId;
    }

    public String getPositionOccupied() {
        return positionOccupied;
    }

    public void setPositionOccupied(String positionOccupied) {
        this.positionOccupied = positionOccupied;
    }
}
