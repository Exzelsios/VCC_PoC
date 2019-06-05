package de.novatec.vccservice.datapoolstatus.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum TargetStatus {
    @JsonProperty("LOADED")
    LOADED,
    @JsonProperty("UNLOADED")
    UNLOADED
}
