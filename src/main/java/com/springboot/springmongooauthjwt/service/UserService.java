package com.springboot.springmongooauthjwt.service;

import com.springboot.springmongooauthjwt.model.User;

import java.util.List;

public interface UserService {


    public User findByUsername(String username);
    public List<User> findAll();
    public User createUser(User user);
    public void deleteUser(User user);


}
