package com.springboot.springmongooauthjwt.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.text.SimpleDateFormat;
import java.util.Date;


@Document(collection = "news")
public class News {


    private String title;

    private String description;

    private String img_uri;

    @CreatedDate
    public String created;

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public News(String title, String description, String img_uri) {

        Date date = new Date(); // This object contains the current date value
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        this.title = title;
        this.description = description;
        this.img_uri = img_uri;
        this.created = formatter.format(date);
    }

    public News() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImg_uri() {
        return img_uri;
    }

    public void setImg_uri(String img_uri) {
        this.img_uri = img_uri;
    }
}
