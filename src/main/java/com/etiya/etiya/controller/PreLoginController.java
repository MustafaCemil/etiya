package com.etiya.etiya.controller;

import com.etiya.etiya.dto.Response;
import com.etiya.etiya.entity.User;
import com.etiya.etiya.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PreLoginController {

    @Autowired
    private UserService userService;

    public PreLoginController(UserService userService){
        this.userService = userService;
    }

    @PostMapping(value = "/registration")
    public ResponseEntity<Response> registration(@RequestBody User user) {
        User dbUser = userService.kayit(user);
        if (dbUser != null) {
            return new ResponseEntity<Response>(new Response("User is saved successfully"), HttpStatus.OK);
        }
        return null;
    }

}