package com.inside.insidetask.security.jwt;

import com.inside.insidetask.user.User;

public final class JwtUserFactory {
    public JwtUserFactory() {
    }

    public static JwtUser create(User user) {
        return new JwtUser(
                user.getName(),
                user.getPassword()
        );
    }

}
