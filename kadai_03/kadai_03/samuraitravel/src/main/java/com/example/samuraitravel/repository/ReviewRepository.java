package com.example.samuraitravel.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.samuraitravel.entity.House;
import com.example.samuraitravel.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Integer>{

	public Page<Review> findByHouseOrderByCreatedAtDesc(House house, Pageable pageable);
//	public List<Review> findTop6ByOrderByCreatedAtDesc();
}
