package com.harshini.bookreview.controller;

import com.harshini.bookreview.model.BookReview;
import com.harshini.bookreview.repository.BookReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin(origins = "http://localhost:3000") // apply once at class level
public class BookReviewController {

    @Autowired
    private BookReviewRepository bookReviewRepository;

    // Create a new review
    @PostMapping
    public BookReview createReview(@RequestBody BookReview review) {
        return bookReviewRepository.save(review);
    }

    // Get all reviews
    @GetMapping
    public List<BookReview> getAllReviews() {
        return bookReviewRepository.findAll();
    }

    // Get a review by ID
    @GetMapping("/{id}")
    public Optional<BookReview> getReviewById(@PathVariable Long id) {
        return bookReviewRepository.findById(id);
    }

    // Search reviews by book title
    @GetMapping("/search")
    public List<BookReview> searchByBookTitle(@RequestParam String title) {
        return bookReviewRepository.findByBookTitleContainingIgnoreCase(title);
    }

    // Update a review
    @PutMapping("/{id}")
    public BookReview updateReview(@PathVariable Long id, @RequestBody BookReview reviewDetails) {
        BookReview review = bookReviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        review.setBookTitle(reviewDetails.getBookTitle());
        review.setAuthor(reviewDetails.getAuthor());
        review.setReviewer(reviewDetails.getReviewer());
        review.setRating(reviewDetails.getRating());
        review.setReview(reviewDetails.getReview());

        return bookReviewRepository.save(review);
    }

    // Delete a review
    @DeleteMapping("/{id}")
    public String deleteReview(@PathVariable Long id) {
        bookReviewRepository.deleteById(id);
        return "Review deleted successfully!";
    }
}
