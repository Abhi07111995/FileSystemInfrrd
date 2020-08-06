package com.example.reviewMovie.entity;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Nullable
@Entity
public class BookmarkedMovie {
	@Id
	@Column(name = "id")
	private String id;
	String movieName;

}
