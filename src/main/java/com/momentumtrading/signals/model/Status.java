package com.momentumtrading.signals.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.With;

@Getter
@With
@AllArgsConstructor
public class Status{
    public Date timestamp;
    public int error_code;
    public Object error_message;
    public int elapsed;
    public int credit_count;
    public Object notice;
    public int total_count;
}
