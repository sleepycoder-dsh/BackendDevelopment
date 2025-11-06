package com.harshini.bookreview.controller;

import com.harshini.bookreview.model.BookReview;
import com.harshini.bookreview.repository.BookReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin(origins = "http://localhost:3000")
public class BookReviewController {

    @Autowired
    private BookReviewRepository bookReviewRepository;

    @PostMapping
    public ResponseEntity<?> createReview(@RequestBody BookReview review) {
        try {
            String sentiment = analyzeSentiment(review.getReview());
            review.setSentiment(sentiment);

            BookReview saved = bookReviewRepository.save(review);

            Map<String, Object> response = new LinkedHashMap<>();
            response.put("message", "Review saved successfully");
            response.put("data", saved);

            System.out.println("Saved review: " + saved.getBookTitle() + " | Sentiment: " + saved.getSentiment());
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to save review: " + e.getMessage()));
        }
    }

    private String analyzeSentiment(String reviewText) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8081/analyze";

        try {
            Map<String, String> requestBody = Map.of("review", reviewText);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, entity, Map.class);

            if (response.getStatusCode().is2xxSuccessful()
                    && response.getBody() != null
                    && response.getBody().get("sentiment") != null) {
                return response.getBody().get("sentiment").toString();
            } else {
                return "Unknown";
            }
        } catch (RestClientException e) {
            System.err.println("Error calling AI service: " + e.getMessage());
            return "Unknown";
        }
    }

    @GetMapping
    public ResponseEntity<List<BookReview>> getAllReviews() {
        return ResponseEntity.ok(bookReviewRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getReviewById(@PathVariable Long id) {
        Optional<BookReview> reviewOpt = bookReviewRepository.findById(id);
        if (reviewOpt.isPresent()) {
            return ResponseEntity.ok(reviewOpt.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Review not found"));
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<BookReview>> searchByBookTitle(@RequestParam String title) {
        return ResponseEntity.ok(bookReviewRepository.findByBookTitleContainingIgnoreCase(title));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateReview(@PathVariable Long id, @RequestBody BookReview details) {
        Optional<BookReview> optionalReview = bookReviewRepository.findById(id);
        if (optionalReview.isPresent()) {
            BookReview review = optionalReview.get();
            review.setBookTitle(details.getBookTitle());
            review.setAuthor(details.getAuthor());
            review.setReviewer(details.getReviewer());
            review.setRating(details.getRating());
            review.setReview(details.getReview());
            BookReview updated = bookReviewRepository.save(review);
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Review not found"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteReview(@PathVariable Long id) {
        bookReviewRepository.deleteById(id);
        return ResponseEntity.ok(Map.of("message", "Review deleted successfully"));
    }
}
