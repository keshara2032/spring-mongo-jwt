package com.springboot.springmongooauthjwt.controller;

import com.springboot.springmongooauthjwt.exception.BadRequestException;
import com.springboot.springmongooauthjwt.exception.UnauthorizedException;
import com.springboot.springmongooauthjwt.middleware.JWTMiddleware;
import com.springboot.springmongooauthjwt.model.JWTResponse;
import com.springboot.springmongooauthjwt.model.News;
import com.springboot.springmongooauthjwt.model.User;
import com.springboot.springmongooauthjwt.service.NewsServiceImplementation;
import com.springboot.springmongooauthjwt.service.StorageServiceImplementation;
import com.springboot.springmongooauthjwt.service.UserServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserController {

    @Autowired
    private StorageServiceImplementation storageServiceImplementation;

    @Autowired
    private UserServiceImplementation userService;

    @Autowired
    private NewsServiceImplementation newsService;

    @Autowired
    private  AuthenticationManager authenticationManager;

    @Autowired
    private JWTMiddleware jwtMiddleware;



    // API for creating a news post
    @CrossOrigin
    @PostMapping(value = "/news/create", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<News> createNews(@RequestPart("news") News news, @RequestPart("file") MultipartFile file){

        System.out.println(news.getTitle());

        try {
            storageServiceImplementation.store(file);
            news.setImg_uri(file.getOriginalFilename());

        }catch (Exception e){
            throw new BadRequestException(e.getLocalizedMessage());
        }



        return ResponseEntity.ok().body(newsService.createNews(news));
    }


    // API for creating a user
    @CrossOrigin
    @PostMapping("/register")
    public ResponseEntity<User> createUser(@RequestBody User user){
        return ResponseEntity.ok().body(userService.createUser(user));
    }

    // API to get latest news
    @CrossOrigin
    @GetMapping("/news")
    public  ResponseEntity<List<News>> getAll(){

        List<News> list = newsService.findAll();

        return ResponseEntity.ok().body(list);

    }


    // Test API for restricted access
    @CrossOrigin
    @GetMapping("/restricted")
    public  ResponseEntity<?> accessRestricted(){
        return ResponseEntity.ok().body("welcome to secured level");
    }


    // API for getting authorization token
    @CrossOrigin
    @PostMapping(value = "/auth")
    public ResponseEntity<?> authenticateUser(@RequestBody User user){

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getusername(),user.getPassword()));
        }catch (Exception e){
            throw new UnauthorizedException(e.getLocalizedMessage());
        }

        final UserDetails userDetails
                = userService.loadUserByUsername(user.getusername());

        final String token =
                jwtMiddleware.generateToken(userDetails);

        return  ResponseEntity.ok().body(new JWTResponse(token));
    }

}