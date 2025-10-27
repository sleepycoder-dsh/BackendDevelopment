package com.harshini.bookreview.controller;

import com.harshini.bookreview.model.BookReview;
import com.harshini.bookreview.repository.BookReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reviews")
public class BookReviewController {

    @Autowired
    private BookReviewRepository bookReviewRepository;

    // CREATE a new review
    @PostMapping
    public BookReview createReview(@RequestBody BookReview review) {
        return bookReviewRepository.save(review);
    }

    // READ all reviews
    @GetMapping
    public List<BookReview> getAllReviews() {
        return bookReviewRepository.findAll();
    }

    // READ a review by ID
    @GetMapping("/{id}")
    public Optional<BookReview> getReviewById(@PathVariable Long id) {
        return bookReviewRepository.findById(id);
    }

    // UPDATE a review
    @PutMapping("/{id}")
    public BookReview updateReview(@PathVariable Long id, @RequestBody BookReview reviewDetails) {
        BookReview review = bookReviewRepository.findById(id).orElseThrow(() -> new RuntimeException("Review not found"));
        review.setBookTitle(reviewDetails.getBookTitle());
        review.setAuthor(reviewDetails.getAuthor());
        review.setReviewer(reviewDetails.getReviewer());
        review.setRating(reviewDetails.getRating());
        review.setReview(reviewDetails.getReview());
        return bookReviewRepository.save(review);
    }

    // DELETE a review
    @DeleteMapping("/{id}")
    public String deleteReview(@PathVariable Long id) {
        bookReviewRepository.deleteById(id);
        return "Review deleted successfully!";
    }
}

