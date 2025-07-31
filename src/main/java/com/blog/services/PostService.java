package com.blog.services;

import java.util.List;

import com.blog.payloads.PostDto;
import com.blog.payloads.PostResponse;

public interface PostService {
	
	// create post
	PostDto createPost(PostDto postDto,long categoryId, long userId );
	
	//update post
	
	PostDto updatePost(PostDto postDto,long postId);
	
	// get post
	PostDto getPost(long id);
	
	//get all posts
	PostResponse getAllPost(int pageNumber,int pageSize,String sortBy,String sortDir);
	
	//delete post
	void deletePost(long id);
	
	//get all posts by category
	List<PostDto> getPostsByCategory(long categoryId);
	
	
	//get all posts by user
	List<PostDto> getPostsByUsers(long userId);
	
	//get post by search
	List<PostDto> getPostBySearch(String keyword);
	


}