package com.springboot.springmongooauthjwt.repository;

import com.springboot.springmongooauthjwt.model.News;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NewsRepository  extends MongoRepository<News, String> {

    public News findByTitle(String title);

}
