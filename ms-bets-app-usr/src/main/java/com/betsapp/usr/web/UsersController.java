package com.betsapp.usr.web;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.betsapp.Configuration;
import com.betsapp.exceptions.NotFoundException;
import com.betsapp.usr.domain.User;
import com.betsapp.usr.repositories.UserRepository;

@RestController
public class UsersController {

	private static final Logger logger = LoggerFactory.getLogger(UsersController.class);
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private Configuration config;
	
	@Autowired
	private Environment env;
	
    @GetMapping("/users/{id}")
    public ResponseEntity<User> findById(@PathVariable("id") Long id) {
    	Optional<User> opUser = repository.findById(id);
    	opUser.orElseThrow(() -> new NotFoundException("User not found"));
    	
    	logger.info("User -> {}", opUser.get());
    	logger.info("Number -> {}", config.getNumber());
    	
    	logger.info("Port {}", env.getProperty("local.server.port"));
    	
        return new ResponseEntity<>(opUser.get(), HttpStatus.OK);
    } 
	
}
