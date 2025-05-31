package com.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.payloads.ApiResponse;
import com.blog.payloads.CommentDto;
import com.blog.services.CommentService;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	
	
	
	@PostMapping("/save/{postId}")
	public ResponseEntity<CommentDto> createCommentController(@RequestBody CommentDto commentDto,@PathVariable long postId ){
		
		
		return new ResponseEntity<CommentDto>(commentService.createComment(commentDto,postId),HttpStatus.CREATED);
	}
	
	@DeleteMapping("/delete/{commentId}")
	public ResponseEntity<ApiResponse> deleteCommentController(@PathVariable long commentId) {
		
		commentService.deleteComment(commentId);
		
		
		return new ResponseEntity<>(new  ApiResponse("comment delted sucessfully",true),HttpStatus.OK);
		
	}
	

}
