package com.springboot.springmongooauthjwt.service;

import com.springboot.springmongooauthjwt.exception.BadRequestException;
import com.springboot.springmongooauthjwt.exception.ResourceNotFoundException;
import com.springboot.springmongooauthjwt.model.User;
import com.springboot.springmongooauthjwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImplementation implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User findByUsername(String username) {

        Optional<User> user_db = Optional.ofNullable(this.userRepository.findByUsername(username));

        if(user_db.isPresent()){
            return user_db.get();
        }else {
            throw new ResourceNotFoundException("User not found with email : " + username);
        }

    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public User createUser(User user) {
        if(user.getPassword() == null || user.getusername() == null || user.getusername().isEmpty() || user.getPassword().isEmpty())
            throw new BadRequestException("Username and Password can not be empty");

        Optional<User> user_db = Optional.ofNullable(this.userRepository.findByUsername(user.getusername()));
        if(!user_db.isPresent()) {
            try {
                String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
                user.setPassword(encodedPassword);

                this.userRepository.insert(user);

            } catch (Exception e) {
                throw new BadRequestException(e.getLocalizedMessage());

            }

            return this.userRepository.findByUsername(user.getusername());
        }else {
            throw new BadRequestException("User exists");
        }
    }

    @Override
    public void deleteUser(User user) {

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user_db = this.findByUsername(username);

        if(user_db == null)
            return null;

        return new org.springframework.security.core.userdetails.User(user_db.getusername(),user_db.getPassword(),new ArrayList<>());
    }


}
