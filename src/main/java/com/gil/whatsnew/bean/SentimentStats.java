package com.gil.whatsnew.bean;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public class SentimentStats {

    private int positive;
    private int neutral;
    private int negative;
}
