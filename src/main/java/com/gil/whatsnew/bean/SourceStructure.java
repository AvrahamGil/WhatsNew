package com.gil.whatsnew.bean;

import lombok.*;

@ToString
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class SourceStructure {

    private String name;
    private String url;

    public SourceStructure() {

    }
}
