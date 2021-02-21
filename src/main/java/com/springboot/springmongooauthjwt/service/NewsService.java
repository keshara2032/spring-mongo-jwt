package com.springboot.springmongooauthjwt.service;


import com.springboot.springmongooauthjwt.model.News;

import java.util.List;

public interface NewsService {

    public News findByTitle(String username);
    public List<News> findAll();
    public News createNews(News news);
    public void deleteNews(News news);


}
