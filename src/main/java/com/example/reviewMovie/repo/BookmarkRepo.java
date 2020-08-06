package com.example.reviewMovie.repo;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.example.reviewMovie.entity.Bookmark;


public interface BookmarkRepo extends PagingAndSortingRepository<Bookmark,Integer> {
	

}
