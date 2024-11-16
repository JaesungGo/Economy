package org.hackathon.economy.member.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginDTO {
    private String email;
    private String password;
}
