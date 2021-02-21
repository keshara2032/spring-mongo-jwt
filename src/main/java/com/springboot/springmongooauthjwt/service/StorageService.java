package com.springboot.springmongooauthjwt.service;


import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {

    void init();

    void store(MultipartFile file) throws IOException;

    Stream<Path> loadAll();

    Path load(String filename) throws FileNotFoundException;

    Resource loadAsResource(String filename) throws FileNotFoundException, MalformedURLException;

    void deleteAll();

}
