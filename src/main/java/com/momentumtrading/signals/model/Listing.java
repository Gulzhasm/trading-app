package com.momentumtrading.signals.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.With;

import java.util.ArrayList;

@Getter
@With
@AllArgsConstructor
public class Listing {
        public Status status;
        public ArrayList<Datum> data;
}
