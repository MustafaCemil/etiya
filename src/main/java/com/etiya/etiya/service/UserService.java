package com.etiya.etiya.service;


import com.etiya.etiya.entity.User;

import java.util.List;

public interface UserService {

    User kayit(User user);
    List<User> listele();
    User emailBul(String email);
}
