package com.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blog.config.AppConstants;
import com.blog.impl.PostServiceImpl;
import com.blog.payloads.ApiResponse;
import com.blog.payloads.PostDto;
import com.blog.payloads.PostResponse;
import com.blog.services.PostService;

@RestController
@RequestMapping("/api/post")
public class PostController {
	
	
	@Autowired
	private PostServiceImpl postService;
	
	@PostMapping("/save/user/{userId}/category/{categoryId}")
	public ResponseEntity<PostDto> savePost
	(@RequestBody PostDto postDto,@PathVariable("categoryId") long categoryId,@PathVariable("userId") long userId){
		
		return new ResponseEntity<>
		(postService.createPost(postDto, categoryId, userId),HttpStatus.CREATED);
	}
	
	
	@GetMapping("/postsList/category/{categoryId}")
	public ResponseEntity<List<PostDto>> getPostsByCategoryController(@PathVariable long categoryId){
	
		return new ResponseEntity<List<PostDto>>(postService.getPostsByCategory(categoryId),HttpStatus.OK);
	}
	
	@GetMapping("/postsList/user/{userId}")
	public ResponseEntity<List<PostDto>> getPostsByUserController(@PathVariable long userId){
	
		return new ResponseEntity<List<PostDto>>(postService.getPostsByUsers(userId),HttpStatus.OK);
	}
	
	@GetMapping("/getPost/{postId}")
	public ResponseEntity<PostDto> singlePost(@PathVariable long postId){
		return   ResponseEntity.ok(postService.getPost(postId));
	}
	
	@GetMapping("/postList")
	public ResponseEntity<PostResponse> getPostList(
			@RequestParam(value="pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) int pageNumber,
			@RequestParam(value="pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) int pageSize,
			@RequestParam(value="sortBy",defaultValue = AppConstants.SORT_BY,required = false) String sortBy,
			@RequestParam(value="sortDir",defaultValue = AppConstants.SORT_DIR,required = false) String sortDir){
		
		return  ResponseEntity.ok(postService.getAllPost(pageNumber,pageSize,sortBy,sortDir));
	}
	
	@DeleteMapping("/delete/{postId}")
	public ApiResponse deletePost(@PathVariable long postId) {
		
		postService.deletePost(postId);
		return new ApiResponse("post deleted suceesfully",true);
	}
	
	@PutMapping("/update/{postId}")
	public ResponseEntity<PostDto> updatedPosts(@RequestBody PostDto postDto,@PathVariable long postId){
		
		return new  ResponseEntity<PostDto>(postService.updatePost(postDto, postId),HttpStatus.OK);
	}
	
	@GetMapping("/search/{keyword}")
	public ResponseEntity<List<PostDto>> searchPostController(@PathVariable String keyword){
	
		
		return ResponseEntity.ok(postService.getPostBySearch(keyword));
	}
	

}
