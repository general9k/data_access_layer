package ru.rodionov.lab_5.dto;

import lombok.Data;

@Data
public class UserRequest {
    private String name;
    private String email;
}