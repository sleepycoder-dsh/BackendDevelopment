package com.harshini.bookreview.repository;

import com.harshini.bookreview.model.BookReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookReviewRepository extends JpaRepository<BookReview, Long> {
    // JpaRepository provides all basic CRUD operations automatically
}

