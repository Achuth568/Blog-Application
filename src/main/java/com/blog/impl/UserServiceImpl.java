package com.blog.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.blog.config.AppConstants;
import com.blog.entity.Role;
import com.blog.entity.Users;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.UserDto;
import com.blog.repositories.RoleRepository;
import com.blog.repositories.UserRepository;
import com.blog.services.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDto createUser(UserDto userDto) {
        Users user = modelMapper.map(userDto, Users.class);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        Users savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public UserDto updateUser(UserDto userDto, long id) {
        Users user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "Id", id));
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        // Encode password before setting it
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setAbout(userDto.getAbout());
        Users updatedUser = userRepository.save(user);
        return modelMapper.map(updatedUser, UserDto.class);
    }

    @Override
    public UserDto getUserById(long id) {
        Users user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "Id", id));
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<Users> users = userRepository.findAll();
        return users.stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUser(long id) {
        Users user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "Id", id));
        userRepository.delete(user);
    }

	@Override
	public UserDto registerNewUser(UserDto userDto) {
		
		Users user=modelMapper.map(userDto,Users.class);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		// Safe Optional handling to prevent NoSuchElementException
		Role role=roleRepository.findById(AppConstants.NORMAL_USER)
				.orElseThrow(() -> new ResourceNotFoundException("Role", "Id", AppConstants.NORMAL_USER));
		
		user.getRoles().add(role);
		
		Users newUser= userRepository.save(user);
		
		
		return modelMapper.map(newUser,UserDto.class);
	}
}
