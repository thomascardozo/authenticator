package br.com.jaxmund.statefulauthapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ok")
public class OkController {

    @GetMapping
    public String getOk(){
        return "OK Idiots!";
    }
}
