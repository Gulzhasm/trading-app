package com.momentumtrading.signals.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.With;

@Getter
@With
@AllArgsConstructor
public class Quote{
    @JsonProperty("USD")
    public USD uSD;
}