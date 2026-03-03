package com.nti.paymentservice.controller;

import com.nti.paymentservice.dto.ClientAuthDto;
import com.nti.paymentservice.dto.ClientDto;
import com.nti.paymentservice.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final ClientService  clientService;

    @PostMapping("/register")
    public ClientAuthDto  register(@Valid @RequestBody ClientDto clientDto) throws Exception {
        return clientService.register(clientDto.getUsername(),clientDto.getPassword());
    }

    @PostMapping("/login")
    public ClientAuthDto login(@Valid @RequestBody ClientDto clientDto) throws Exception {
        return clientService.login(clientDto.getUsername(),clientDto.getPassword());
    }

}
