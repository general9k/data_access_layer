package ru.rodionov.lab_5.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
public class UserDTO {

    private Long id;

    private String name;

    private String email;

    private OffsetDateTime createdAt;
}