package com.blog.services;

import com.blog.payloads.CommentDto;

public interface CommentService {
	
	CommentDto createComment(CommentDto commentDto,long postId);
	
	void deleteComment(long commentId);

}
