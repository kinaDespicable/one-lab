package one.lab.firstpractice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import one.lab.firstpractice.annotation.LoggableRequest;
import one.lab.firstpractice.model.dto.request.LoginRequest;
import one.lab.firstpractice.model.dto.request.RegistrationRequest;
import one.lab.firstpractice.model.dto.response.CreatedResponse;
import one.lab.firstpractice.model.dto.response.auth.LoginResponse;
import one.lab.firstpractice.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;


    @LoggableRequest
    @PostMapping("/signup")
    public ResponseEntity<CreatedResponse> register(@RequestBody @Valid RegistrationRequest registrationRequest) {
        return new ResponseEntity<>(authService.register(registrationRequest), HttpStatus.CREATED);
    }

    @LoggableRequest
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody @Valid LoginRequest loginRequest) {
        return new ResponseEntity<>(authService.authenticate(loginRequest), HttpStatus.OK);
    }

}
