package com.harshini.bookreview.repository;

import com.harshini.bookreview.model.BookReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// JpaRepository provides all CRUD operations automatically
@Repository
public interface BookReviewRepository extends JpaRepository<BookReview, Long> {

    // Custom finder method: search by book title (case-insensitive, partial match)
    List<BookReview> findByBookTitleContainingIgnoreCase(String bookTitle);

    // (Optional) search by author
    List<BookReview> findByAuthorContainingIgnoreCase(String author);

}
