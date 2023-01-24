package com.bknote71.ajouportal.config;

import lombok.Getter;

@Getter
public class CurrentSystem {
    private int years;
    private String semester;

    public CurrentSystem(int years, String semester) {
        this.years = years;
        this.semester = semester;
    }
}
