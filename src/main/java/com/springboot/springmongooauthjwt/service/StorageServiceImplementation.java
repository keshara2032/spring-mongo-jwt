package com.springboot.springmongooauthjwt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
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


    }

    @Override
    public void store(MultipartFile file) throws IOException {

        final File f = new File(StorageServiceImplementation.class.getProtectionDomain().getCodeSource().getLocation().getPath());


        userBucketPath = Arrays.asList(System.getProperty("java.class.path") .split(";")).get(0) + "/static/";

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
