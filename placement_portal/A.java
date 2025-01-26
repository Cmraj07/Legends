package com.placement_portal;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class A {
    public static void main(String[] args) {
        // 1st way
//        PasswordEncoder e = new BCryptPasswordEncoder();
//        System.out.println("testing");

        // 2nd way (strong)
        String encodedPwd = BCrypt.hashpw("testing", BCrypt.gensalt(10));
        System.out.println(encodedPwd);
    }
}
