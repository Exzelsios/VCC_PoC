package de.novatec.vccstatusrepository.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum LoadStatus {
    @JsonProperty("LOADED")
    LOADED,
    @JsonProperty("UNLOADED")
    UNLOADED
}
