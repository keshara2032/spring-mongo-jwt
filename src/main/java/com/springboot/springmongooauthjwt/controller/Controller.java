package com.springboot.springmongooauthjwt.controller;

import com.springboot.springmongooauthjwt.exception.BadRequestException;
import com.springboot.springmongooauthjwt.exception.UnauthorizedException;
import com.springboot.springmongooauthjwt.middleware.JWTMiddleware;
import com.springboot.springmongooauthjwt.model.JWTResponse;
import com.springboot.springmongooauthjwt.model.News;
import com.springboot.springmongooauthjwt.model.User;
import com.springboot.springmongooauthjwt.response.Response;
import com.springboot.springmongooauthjwt.service.NewsServiceImplementation;
import com.springboot.springmongooauthjwt.service.S3StorageService;
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
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class Controller {

    @Autowired
    private StorageServiceImplementation storageServiceImplementation;

    @Autowired
    private S3StorageService s3StorageService;

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

        System.out.println(news);

        try {
            String url = s3StorageService.uploadFile(file);
            news.setImg_uri(url);

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
    @GetMapping("/authenticate")
    public  ResponseEntity<?> accessRestricted( @RequestHeader Map<String, String> headers){

        headers.forEach((key, value) -> {
            System.out.println( key +" : "+  value);
        });

        Response response = new Response();
        response.setMessage("Authenticated");
        response.setStatus(200);
        response.setTimestamp(new Date());
        return ResponseEntity.ok().body(response);
    }


    // API for getting authorization token
    @CrossOrigin
    @PostMapping(value = "/login")
    public ResponseEntity<?> authenticateUser(@RequestBody User user, @RequestHeader Map<String, String> headers  ){

   

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
