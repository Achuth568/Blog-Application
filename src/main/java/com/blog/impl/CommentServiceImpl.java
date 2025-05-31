package com.blog.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.entity.Comment;
import com.blog.entity.Post;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.CommentDto;
import com.blog.repositories.CommentRepository;
import com.blog.repositories.PostRepository;
import com.blog.services.CommentService;


@Service
public class CommentServiceImpl implements CommentService{
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CommentDto createComment(CommentDto commentDto, long postId) {
		
	Post post=postRepository.findById(postId)
			.orElseThrow(()-> new ResourceNotFoundException("Post","Id", postId));
		Comment comment=modelMapper.map(commentDto,Comment.class);
		
		comment.setPost(post);
		
		Comment savedComment=commentRepository.save(comment);
		
		return modelMapper.map(savedComment,CommentDto.class);
	}

	@Override
	public void deleteComment(long commentId) {
		
		Comment comment=commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment","Id", commentId));
		
		commentRepository.delete(comment);
	}

}
