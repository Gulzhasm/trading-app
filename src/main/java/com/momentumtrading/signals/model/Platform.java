package com.momentumtrading.signals.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.With;

@Getter
@With
@AllArgsConstructor
public class Platform{
    public int id;
    public String name;
    public String symbol;
    public String slug;
    public String token_address;
}
