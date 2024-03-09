package com.momentumtrading.signals.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.With;

@Getter
@With
@AllArgsConstructor
public class USD{
    public double price;
    public double volume_24h;
    public double volume_change_24h;
    public double percent_change_1h;
    public double percent_change_24h;
    public double percent_change_7d;
    public double percent_change_30d;
    public double percent_change_60d;
    public double percent_change_90d;
    public double market_cap;
    public double market_cap_dominance;
    public double fully_diluted_market_cap;
    public Object tvl;
    public Date last_updated;
}
