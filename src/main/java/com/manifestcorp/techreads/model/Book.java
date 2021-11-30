package com.manifestcorp.techreads.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    String title;
    String author;
    String coverURL;
    double rating;
    public Book() {}

    public Book(String title, String author, String coverURL, double rating) {
        this.title = title;
        this.author = author;
        this.coverURL = coverURL;
        this.rating = rating;
    }

    public Book(Book original) {
        this.copy(original);
    }

    public void copy(Book original) {
        this.title = original.getTitle();
        this.author = original.getAuthor();
        this.coverURL = original.getCoverURL();
        this.rating = original.getRating();
    }

    public String getCoverURL() {
        return coverURL;
    }

    public void setCoverURL(String coverURL) {
        this.coverURL = coverURL;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Double.compare(book.rating, rating) == 0 && id.equals(book.id) && Objects.equals(title, book.title) && Objects.equals(author, book.author) && Objects.equals(coverURL, book.coverURL);
    }

    @Override
    public String toString() {
        return "" + this.id + ", " + this.title + ", " + this.author + ", " + this.rating + ", " + this.coverURL;
    }
}
