package com.campusconnect.server.service;

import java.util.List;

import com.campusconnect.server.domain.IosDevice;

public interface IosDeviceService {
	// Find all Devices
	public List<IosDevice> getAll();
	
	// Find all Device ids
	public List<String> getAllIds();
	
	// Find a device with details by id
	// NOTE: Currently not being used
	public IosDevice getById(String id);
	
	// Insert or update a device
	public IosDevice save(IosDevice device);
	
	// Delete a device
	public void delete(IosDevice device);
	
	// Delete a device by Id
	public void deleteById(String id);
}
