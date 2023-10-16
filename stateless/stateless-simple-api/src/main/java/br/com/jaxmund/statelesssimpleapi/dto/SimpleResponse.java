package br.com.jaxmund.statelesssimpleapi.dto;

public record SimpleResponse(String status, Integer code, AuthUserResponse authUser) {
}
