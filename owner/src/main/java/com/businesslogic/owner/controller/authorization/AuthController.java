package com.businesslogic.owner.controller.authorization;


import com.businesslogic.client.security.jwt.JwtProvider;
import com.businesslogic.owner.dto.auth.AuthRequest;
import com.businesslogic.owner.dto.auth.AuthResponse;
import com.businesslogic.owner.dto.auth.RegistrationRequest;
import com.businesslogic.owner.entity.OwnerEntity;
import com.businesslogic.owner.entity.ShopEntity;
import com.businesslogic.owner.repository.ShopRepository;
import com.businesslogic.owner.service.OwnerService;
import com.businesslogic.owner.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {


    private final OwnerService ownerService;

    private final ShopService shopService;

    private final JwtProvider jwtProvider;

    private final ShopRepository shopRepository;


    //Регистрация владельца и создание магазина
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String registerOwner(@RequestBody RegistrationRequest registrationRequest) {
        OwnerEntity u = new OwnerEntity();
        ShopEntity s = new ShopEntity();
        u.setPassword(registrationRequest.getPassword());
        u.setLogin(registrationRequest.getLogin());
        ownerService.saveUser(u);
        s.setName(registrationRequest.getName());
        s.setCashback(registrationRequest.getCashback());
        s.setOwnerId(shopRepository.findOwnerId());
        shopService.saveStore(s);
        return "OK";
    }

    //Авторизация владельца
    @PostMapping(value = "/auth", consumes = MediaType.APPLICATION_JSON_VALUE)
    public AuthResponse auth(@RequestBody AuthRequest request) {
        OwnerEntity ownerEntity = ownerService.findByLoginAndPassword(request.getLogin(), request.getPassword());
        String token = jwtProvider.generateToken(ownerEntity.getLogin());
        return new AuthResponse(token);
    }
}
