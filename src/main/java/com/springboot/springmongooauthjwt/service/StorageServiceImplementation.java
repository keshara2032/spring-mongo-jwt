package com.springboot.springmongooauthjwt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

@Service
@Transactional
public class StorageServiceImplementation implements StorageService {


    @Value("${resources.path}")
    private String userBucketPath;



    @Override
    public void init() {

        File file = new File(userBucketPath);

        if(file.mkdir())
            System.out.println("Directory successfully created "+userBucketPath);
        else
            System.out.println("Directory exists!");


    }

    @Override
    public void store(MultipartFile file) throws IOException {
        this.init();

        file.transferTo(new File(userBucketPath+file.getOriginalFilename()));

        this.load(file.getOriginalFilename());
    }

    @Override
    public Stream<Path> loadAll() {
        return null;
    }

    @Override
    public Path load(String filename) throws FileNotFoundException {

        Path filePath = ResourceUtils.getFile(userBucketPath+filename).toPath();
//
        return filePath;
    }

    @Override
    public Resource loadAsResource(String filename) throws FileNotFoundException, MalformedURLException {
        Resource resource = new UrlResource(this.load(filename).toUri());

        if(resource.exists()) {
            return resource;
        } else {
            throw new FileNotFoundException("File not found " + filename);
        }
    }

    @Override
    public void deleteAll() {

    }
}
