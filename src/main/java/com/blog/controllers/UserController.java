package com.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.blog.payloads.ApiResponse;
import com.blog.payloads.UserDto;
import com.blog.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
@Tag(name="User")
public class UserController {

    @Autowired
    private UserService userService;

    // POST - CREATE THE USER
    @PostMapping("/save")
    public ResponseEntity<UserDto> saveController(@Valid @RequestBody UserDto userDto) {
        UserDto createdUser = userService.createUser(userDto);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    // UPDATE THE USER
    @PutMapping("/update/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable long userId) {
        UserDto updatedUser = userService.updateUser(userDto, userId);
        return ResponseEntity.ok(updatedUser);
    }

    // GET THE INDIVIDUAL USER
    @GetMapping("/singleUser/{userId}")
    public ResponseEntity<UserDto> getIndividualUser(@PathVariable long userId) {
        UserDto user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    // GET THE LIST OF USERS
    @GetMapping("/multipleUser")
    public ResponseEntity<List<UserDto> > getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    
    
    // DELETE THE USER
    @DeleteMapping("/delete/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable long userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>(new ApiResponse("User deleted successfully", true), HttpStatus.OK);
    }
    
    //Register new user api
    @PostMapping("/registerUser")
    @Operation(description = "POST endpoint from USER",
               summary ="Using this endpoint user can register their details",
               responses = { @io.swagger.v3.oas.annotations.responses.ApiResponse(
            		   description="success",
            		   responseCode ="200" ),
            		   @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    		   description="UnAuthorized",
                    		   responseCode ="403")
               })
    public ResponseEntity<UserDto> registerNewUser(@RequestBody UserDto userDto){
    	
    	UserDto register=userService.registerNewUser(userDto);
    	
    	return new ResponseEntity<UserDto>(register,HttpStatus.CREATED);
    	
    }
}

