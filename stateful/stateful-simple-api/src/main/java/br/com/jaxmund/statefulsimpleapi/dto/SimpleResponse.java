package br.com.jaxmund.statefulsimpleapi.dto;

public record SimpleResponse(String status, Integer code, AuthUserResponse authUserResponse){
}
