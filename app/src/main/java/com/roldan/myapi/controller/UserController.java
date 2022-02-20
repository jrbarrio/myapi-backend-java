package com.roldan.myapi.controller;

import spark.Request;
import spark.Response;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static spark.Spark.halt;

public class UserController {

    private static final String USERNAME_PATTERN = "[a-zA-Z][a-zA-Z0-9]{1,29}";

    public void authenticate(Request request, Response response) {
        var authHeader = request.headers("Authorization");
        if (authHeader == null || !authHeader.startsWith("Basic ")) {
            return;
        }

        var offset = "Basic ".length();
        var credentials = new String(Base64.getDecoder().decode(
                authHeader.substring(offset)), StandardCharsets.UTF_8);

        var components = credentials.split(":", 2);
        if (components.length != 2) {
            throw new IllegalArgumentException("invalid auth header");
        }

        var username = components[0];
        var password = components[1];

        if (!username.matches(USERNAME_PATTERN)) {
            throw new IllegalArgumentException("invalid username");
        }

        request.attribute("subject", username);
    }

    public void requireAuthentication(Request request, Response response) {
        if (request.attribute("subject") == null) {
            response.header("WWW-Authenticate",
                    "Basic realm=\"/\", charset=\"UTF-8\"");
            halt(401);
        }
    }
}
