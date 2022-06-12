package com.gmail.evanloafakahaitao.service.impl;

import com.gmail.evanloafakahaitao.dao.RoleDao;
import com.gmail.evanloafakahaitao.dao.UserDao;
import com.gmail.evanloafakahaitao.dao.model.Role;
import com.gmail.evanloafakahaitao.dao.model.User;
import com.gmail.evanloafakahaitao.service.UserService;
import com.gmail.evanloafakahaitao.service.exceptions.RoleNotFoundException;
import com.gmail.evanloafakahaitao.service.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
public class UserServiceImpl implements UserService {

    @Value("${role.default}")
    private String ROLE_DEFAULT;
    private UserDao userDao;
    private PasswordEncoder passwordEncoder;
    private RoleDao roleDao;

    public UserServiceImpl(UserDao userDao, PasswordEncoder passwordEncoder, RoleDao roleDao) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.roleDao = roleDao;
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public User findById(Long id) {
        return userDao.findById(id).orElseThrow(() -> new UserNotFoundException("No user with id " + id));
    }

    @Override
    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public User findByEmailAndPassword(String email, String password) {
        User user = findByEmail(email);
        if (user != null) {
            if (passwordEncoder.matches(password, user.getPassword())) {
                return user;
            }
        } else {
            throw new UserNotFoundException("No user with email " + email);
        }
        return null;
    }

    @Override
    public User save(User user) {
        Role role = roleDao.findByName(ROLE_DEFAULT);
        if (role == null) {
            throw new RoleNotFoundException("Cant find Role " + ROLE_DEFAULT);
        }
        user.setRole(role);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userDao.save(user);
    }

    @Override
    public void deleteById(Long id) {
        userDao.deleteById(id);
    }
}
