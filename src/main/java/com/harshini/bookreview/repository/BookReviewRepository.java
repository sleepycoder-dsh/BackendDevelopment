package com.harshini.bookreview.repository;

// Imports JPA repository features
import com.harshini.bookreview.model.BookReview;
import org.springframework.data.jpa.repository.JpaRepository;

// Marks this interface as a Spring repository
import org.springframework.stereotype.Repository;

// JpaRepository provides all  CRUD operations automatically
@Repository
public interface BookReviewRepository extends JpaRepository<BookReview, Long> {

}
