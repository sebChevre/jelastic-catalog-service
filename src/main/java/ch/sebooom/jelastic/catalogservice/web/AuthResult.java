package ch.sebooom.jelastic.catalogservice.web;

import lombok.Getter;

@Getter
public class AuthResult {

    private Boolean login;

    public AuthResult(Boolean login){
        this.login = login;
    }

    public AuthResult() {}
}
