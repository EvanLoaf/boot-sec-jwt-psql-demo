package com.gmail.evanloafakahaitao.service;

import com.gmail.evanloafakahaitao.dao.model.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User findById(Long id);

    User findByEmail(String email);

    User findByEmailAndPassword(String email, String password);

    User save(User user);

    void deleteById(Long id);
}
