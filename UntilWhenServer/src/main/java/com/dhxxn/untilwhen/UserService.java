package com.dhxxn.untilwhen;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(e -> users.add(e));
        return users;
    }

    public Optional<User> findById(Integer id) {
        Optional<User> users = userRepository.findById(id);
        return users;
    }

    public void delete(Integer id) {
        userRepository.deleteById(id);
    }

    public User signUp(User user) {
        userRepository.save(user);
        return user;
    }
}
