package com.harshini.bookreview.model;
//package com.harshini.bookreview.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class BookReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bookTitle;
    private String author;
    private String reviewer;
    private String review;
    private int rating;

    // Default constructor (required by JPA)
    public BookReview() {}

    //Parameterized constructor
    public BookReview(Long id, String bookTitle, String author, String review, String reviewer, int rating) {
        this.id = id;
        this.bookTitle = bookTitle;
        this.author = author;
        this.review = review;
        this.reviewer = reviewer;
        this.rating = rating;
    }

    // Constructor with fields
    public BookReview(String bookTitle, String author, String reviewer, String review, int rating) {
        this.bookTitle = bookTitle;
        this.author = author;
        this.reviewer = reviewer;
        this.review = review;
        this.rating = rating;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getBookTitle() { return bookTitle; }
    public void setBookTitle(String bookTitle) { this.bookTitle = bookTitle; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getReviewer() { return reviewer; }
    public void setReviewer(String reviewer) { this.reviewer = reviewer; }

    public String getReview() { return review; }
    public void setReview(String review) { this.review = review; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }
}

