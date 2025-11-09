package ru.rodionov.lab_5.service;

import ru.rodionov.lab_5.model.User;

import java.util.List;

public interface UserService {

    User registerUser(String name, String email);

    User findUserByEmail(String email);

    List<User> listAllUsers();
}