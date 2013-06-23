package com.campusconnect.server.service;

import java.util.ArrayList;
import java.util.List;
import com.campusconnect.server.domain.Device;

public interface DeviceService {
	// Find all Devices
	public List<Device> getAll();
	
	// Find all Device ids
	public List<String> getAllIds();
	
	// Find a device with details by id
	// NOTE: Currently not being used
	public Device getById(String id);
	
	// Insert or update a device
	public Device save(Device device);
	
	// Delete a device
	public void delete(Device device);
	
	// Delete a device by Id
	public void deleteById(String id);
}
