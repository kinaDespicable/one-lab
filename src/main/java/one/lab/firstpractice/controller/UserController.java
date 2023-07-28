package one.lab.firstpractice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import one.lab.firstpractice.annotation.LoggableRequest;
import one.lab.firstpractice.model.dto.request.CreateUserRequest;
import one.lab.firstpractice.model.dto.response.CreatedResponse;
import one.lab.firstpractice.model.dto.response.user.UserResponse;
import one.lab.firstpractice.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @LoggableRequest
    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CreatedResponse> createUser(@RequestBody @Valid CreateUserRequest createUserRequest){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return new ResponseEntity<>(userService.createUser(authentication, createUserRequest), HttpStatus.CREATED);
    }

    @LoggableRequest
    @GetMapping("/fetch-all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<UserResponse>> getAll(@RequestParam(name = "page", required = false) Optional<Integer> pageOptional,
                                                     @RequestParam(name = "size", required = false) Optional<Integer> sizeOptional,
                                                     @RequestParam(name = "sort", required = false) Optional<String> sortOptional) {
        return new ResponseEntity<>(userService.fetchAll(pageOptional, sizeOptional, sortOptional), HttpStatus.OK);
    }

    @LoggableRequest
    @GetMapping("/fetch-admins")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<UserResponse>> getAdmins(){
        return new ResponseEntity<>(userService.fetchAllAdmins(), HttpStatus.OK);
    }

    @LoggableRequest
    @GetMapping("/fetch-authors")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<UserResponse>> getAuhtors(){
        return new ResponseEntity<>(userService.fetchAllAuthors(), HttpStatus.OK);
    }

    @LoggableRequest
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> getById(@PathVariable Long id){
        return new ResponseEntity<>(userService.fetchById(id), HttpStatus.OK);
    }

    @LoggableRequest
    @GetMapping("/profile")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UserResponse> getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (String) authentication.getPrincipal();
        return new ResponseEntity<>(userService.fetchCurrentUser(username), HttpStatus.OK);
    }

}
