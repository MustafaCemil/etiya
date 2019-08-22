package com.etiya.etiya.service.impl;

import com.etiya.etiya.entity.User;
import com.etiya.etiya.repository.UserRepository;
import com.etiya.etiya.service.UserService;
import com.etiya.etiya.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User kayit(User user) {
        String password = PasswordUtil.getPasswordHash(user.getPassword());
        user.setPassword(password);
        user.setCreatedAt(new Date());
        return userRepository.save(user);
    }

    @Override
    public List<User> listele() {
        return userRepository.findAll();
    }

    @Override
    public User emailBul(String email) {
        return userRepository.findByEmailIgnoreCase(email);
    }
}
