package com.momentumtrading.signals.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.With;

import java.util.ArrayList;

@Getter
@With
@AllArgsConstructor
public class Datum{
    public int id;
    public String name;
    public String symbol;
    public String slug;
    public int num_market_pairs;
    public Date date_added;
    public ArrayList<String> tags;
    public int max_supply;
    public double circulating_supply;
    public double total_supply;
    public boolean infinite_supply;
    public Platform platform;
    public int cmc_rank;
    public Object self_reported_circulating_supply;
    public Object self_reported_market_cap;
    public Object tvl_ratio;
    public Date last_updated;
    public Quote quote;
}