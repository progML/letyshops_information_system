package com.businesslogic.client.controller.authorization;


import com.businesslogic.client.dto.auth.AuthRequest;
import com.businesslogic.client.dto.auth.AuthResponse;
import com.businesslogic.client.dto.auth.RegistrationRequest;
import com.businesslogic.client.entity.ClientEntity;
import com.businesslogic.client.security.jwt.JwtProvider;
import com.businesslogic.client.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor

@RestController
public class AuthController {


    private final ClientService clientService;

    private final JwtProvider jwtProvider;

    //Регистрация покупателя
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String registrationUser(@RequestBody RegistrationRequest registrationRequest) {
        ClientEntity u = new ClientEntity();
        u.setPassword(registrationRequest.getPassword());
        u.setLogin(registrationRequest.getLogin());
        clientService.saveUser(u);
        return "OK";
    }

    //Авторизация покупателя
    @PostMapping(value = "/auth", consumes = MediaType.APPLICATION_JSON_VALUE)
    public AuthResponse authorizationUser(@RequestBody AuthRequest request) {
        ClientEntity clientEntity = clientService.findByLoginAndPassword(request.getLogin(), request.getPassword());
        String token = jwtProvider.generateToken(clientEntity.getLogin());
        return new AuthResponse(token);
    }
}
