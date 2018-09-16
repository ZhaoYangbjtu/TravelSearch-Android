package com.yangzhao.travelsearch.Bean;

/**
 * Created by YangZhao on 2018/4/13.
 */

public class Review {
    private String name;
    private String rating;
    private String time;
    private String text;
    private String personImg;
    private String author_url;

    public String getAuthor_url() {
        return author_url;
    }
    public void setAuthor_url(String author_url) {
        this.author_url = author_url;
    }
    public String getPersonImg() {
        return personImg;
    }

    public void setPersonImg(String personImg) {
        this.personImg = personImg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
