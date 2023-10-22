package br.com.jaxmund.statefulsimpleapi.controller;

import br.com.jaxmund.statefulsimpleapi.dto.SimpleResponse;
import br.com.jaxmund.statefulsimpleapi.service.SimpleService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("api/resource")
public class SimpleController {

    private final SimpleService simpleService;

    @GetMapping
    public SimpleResponse getResource(@RequestHeader String accessToken){
        return simpleService.getData(accessToken);
    }
}
