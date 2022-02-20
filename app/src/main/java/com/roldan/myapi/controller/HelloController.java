package com.roldan.myapi.controller;

import lombok.AllArgsConstructor;
import org.json.JSONObject;
import spark.Request;
import spark.Response;

@AllArgsConstructor
public class HelloController {

    public JSONObject greet(Request request, Response response) {
        response.status(201);
        response.header("Location", "/greeting");
        return new JSONObject()
                .put("greeting", "Hola");
    }
}
