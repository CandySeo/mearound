package com.candyseo.mearound.controller;

import java.util.UUID;

import com.candyseo.mearound.model.dto.user.User;
import com.candyseo.mearound.service.user.UserService;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/mearound/users")
public class UserController {
    
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value="/", 
                consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String registUser(@RequestBody User user) {

        log.info("Request to regist: {}", user);

        return userService.regist(user);
    }

    @GetMapping(value="/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUser(@PathVariable("userId") String id) {

        log.info("Request to get: id[{}]", id);
        
        return userService.getByUserId(id);
    }
}
