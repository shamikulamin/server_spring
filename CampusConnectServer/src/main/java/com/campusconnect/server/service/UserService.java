package com.campusconnect.server.service;

import java.util.List;

import com.campusconnect.server.domain.User;

public interface UserService {
	// Find all users
	public List<User> getAll();
	
	// Get all users except this user
	public List<User> getAllExcept(String except);
	
	// Find a user by username
	public User getById(String id);
	
	// Insert or update a user
	public User save(User u);
	
	// Delete a user
	public void delete(User u);
}
