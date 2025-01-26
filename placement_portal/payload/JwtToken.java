package com.placement_portal.payload;

import lombok.Getter;
import lombok.Setter;


public class JwtToken {
    private String token;
    private String type;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
