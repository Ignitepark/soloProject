package com.jin.post;

import java.sql.Date;

import lombok.Data;

@Data
public class Post {

	private int postNo;
	private String postTitle;
	private String postContents;
	private Date postDate;
	private String postWriter;
}
