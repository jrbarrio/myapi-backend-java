package com.roldan.myapi;

import com.roldan.myapi.controller.HelloController;
import org.json.JSONObject;

import static spark.Spark.*;

public class App {
    public static void main(String[] args) {

        var helloController = new HelloController();

        before(((request, response) -> {
            var method = request.requestMethod();
            if ((method.equals("PUT") || method.equals("POST")) &&
                    !"application/json".equals(request.contentType())) {
                halt(415, new JSONObject().put(
                        "error", "Only application/json supported"
                ).toString());
            }
        }));

        get("/greeting", helloController::greet);
        put("/greeting", helloController::greet);
        post("/greeting", helloController::greet);

        afterAfter((request, response) -> {
            // Preventing XSS
            response.type("application/json;charset=utf-8");
            response.header("X-Content-Type-Options", "nosniff");
            response.header("X-Frame-Options", "DENY");
            response.header("X-XSS-Protection", "0");
            response.header("Cache-Control", "no-store");
            response.header("Content-Security-Policy",
                    "default-src 'none'; frame-ancestors 'none'; sandbox");
            // Remove information leak
            response.header("Server", "");
        });
    }
}
