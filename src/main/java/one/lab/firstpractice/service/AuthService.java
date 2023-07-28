package one.lab.firstpractice.service;

import one.lab.firstpractice.model.dto.request.LoginRequest;
import one.lab.firstpractice.model.dto.request.RegistrationRequest;
import one.lab.firstpractice.model.dto.response.CreatedResponse;
import one.lab.firstpractice.model.dto.response.LoginResponse;

public interface AuthService {

    CreatedResponse register(RegistrationRequest request);

    LoginResponse authenticate(LoginRequest loginRequest);
}
