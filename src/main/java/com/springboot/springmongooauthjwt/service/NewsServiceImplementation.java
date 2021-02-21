package com.springboot.springmongooauthjwt.service;

import com.springboot.springmongooauthjwt.exception.BadRequestException;
import com.springboot.springmongooauthjwt.exception.ResourceNotFoundException;
import com.springboot.springmongooauthjwt.model.News;
import com.springboot.springmongooauthjwt.model.User;
import com.springboot.springmongooauthjwt.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class NewsServiceImplementation implements NewsService {

    @Autowired
    private NewsRepository newsRepository;

    @Override
    public News findByTitle(String username) {
        return null;
    }

    @Override
    public List<News> findAll() {

        List<News> list = null;
        list  = this.newsRepository.findAll();

        if(list.isEmpty()){
            throw new ResourceNotFoundException("No news");
        }
        return list;
    }

    @Override
    public News createNews(News news) {

        if(news.getTitle() == null || news.getDescription() == null || news.getTitle().isEmpty() || news.getDescription().isEmpty() )
            throw new BadRequestException("Fields can not be empty");


        Optional<News> news_db = Optional.ofNullable(this.newsRepository.findByTitle(news.getTitle()));

        if(!news_db.isPresent()) {
            try {

                this.newsRepository.insert(news);

            } catch (Exception e) {
                throw new BadRequestException(e.getLocalizedMessage());

            }

            return this.newsRepository.findByTitle(news.getTitle());
        }else {
            throw new BadRequestException("News exists");
        }

    }

    @Override
    public void deleteNews(News news) {

    }
}
