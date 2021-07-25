package com.community.communityback.config.filter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


public enum FilterConstants {
    DOMAIN("http://localhost:3000/");

    private final String value;

    public String getValue() {return value;}

    FilterConstants(String value){
        this.value = value;
    }

    public String toString(){
        return getValue();
    }
}
