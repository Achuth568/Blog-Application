package com.blog.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blog.entity.Category;
import com.blog.entity.Post;
import com.blog.entity.Users;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.PostDto;
import com.blog.payloads.PostResponse;
import com.blog.repositories.CategoryRepository;
import com.blog.repositories.PostRepository;
import com.blog.repositories.UserRepository;
import com.blog.services.PostService;

@Service
public class PostServiceImpl implements PostService{
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public PostDto createPost(PostDto postDto,long categoryId,long userId) {
		
	 Users user=userRepository.findById(userId)
			 .orElseThrow(()->new ResourceNotFoundException("user", "id", userId));
	
	 Category category=categoryRepository.findById(categoryId)
			 .orElseThrow(()->new ResourceNotFoundException("Category","Id", categoryId));
	 
	 
		Post post=modelMapper.map(postDto,Post.class);
		post.setImageName("default.png");
		post.setAddedDate(LocalDate.now());
		post.setUsers(user);
		post.setCategory(category);
		
		Post savedPost=postRepository.save(post);
		
		return modelMapper.map(savedPost,PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, long postId) {
		Post post=postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","Id",postId));
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		
		Post updatedPost= postRepository.save(post);
		
		return modelMapper.map(updatedPost,PostDto.class);
	}

	@Override
	public PostDto getPost(long id) {
		
		Post post=postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post","Id", id));
				
		return modelMapper.map(post,PostDto.class); 
	}

	@Override
	public PostResponse getAllPost(int pageNumber,int pageSize,String sortBy,String sortDir) {
		
		Sort sort=(sortDir.equalsIgnoreCase("asc"))? Sort.by(sortBy):Sort.by(sortBy).descending();
		
		Pageable p=PageRequest.of(pageNumber, pageSize,sort);
		
		Page<Post>pagePosts=postRepository.findAll(p);
		
		List<Post> posts=pagePosts.getContent();
		
		List<PostDto> postDto=posts.stream().map(post->modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
		
		PostResponse postResponse= new PostResponse();
		
		postResponse.setContent(postDto);
		postResponse.setPageNumber(pagePosts.getNumber());
		postResponse.setPageSize(pagePosts.getSize());
		postResponse.setTotalElements(pagePosts.getTotalElements());
		postResponse.setTotalPages(pagePosts.getTotalPages());
		postResponse.setLastPage(pagePosts.isLast());
		
		return postResponse;
	}

	@Override
	public void deletePost(long id) {
		
		Post post=postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post","Id", id));
		
		postRepository.delete(post);
		
	}

	@Override
	public List<PostDto> getPostsByCategory(long categordId) {
		
		Category cateory=categoryRepository.findById(categordId).orElseThrow(()->new ResourceNotFoundException("category", "id", categordId));
		List<Post> posts= postRepository.findByCategory(cateory);
		
		return posts.stream().map(post->modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
	
	}

	@Override
	public List<PostDto> getPostsByUsers(long userId) {
		Users user=userRepository.findById(userId)
				 .orElseThrow(()->new ResourceNotFoundException("user","id",userId));
		List<Post> posts=postRepository.findByUsers(user);
		
		
		return posts.stream().map(post->modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
	}

	@Override
	public List<PostDto> getPostBySearch(String keyword) {
		
		List<Post> posts=postRepository.findByTitleContaining(keyword);
		
		List<PostDto>search=posts.stream().map(post->modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
		
		return search;
	}

}
