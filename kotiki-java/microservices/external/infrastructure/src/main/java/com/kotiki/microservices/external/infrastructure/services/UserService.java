package com.kotiki.microservices.external.infrastructure.services;
import com.kotiki.core.exceptions.KotikiExeption;
import com.kotiki.microservices.external.infrastructure.daos.UserDao;
import com.kotiki.microservices.external.infrastructure.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws KotikiExeption {
        User user = userDao.findByUsername(username);

        if (user == null) { throw new KotikiExeption("user not found"); }

        return user;
    }

    public List<User> allUsers() { return userDao.findAll(); }

    public User saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userDao.save(user);
    }

    public void deleteUser(Long userId) {
        if (userDao.findById(userId).isPresent())
            userDao.deleteById(userId);
    }

    public User getUser(Long id) { return userDao.findById(id).orElse(null); }
}
