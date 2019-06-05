package de.novatec.vccadmin.datapoolstatus.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum LoadStatus {
    @JsonProperty("LOADED")
    LOADED,
    @JsonProperty("UNLOADED")
    UNLOADED,
    @JsonProperty("UNDEFINED")
    UNDEFINED
}
