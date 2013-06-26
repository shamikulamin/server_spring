package com.campusconnect.server.service.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.campusconnect.server.domain.Device;
import com.campusconnect.server.service.DeviceService;

@Service("jpaDeviceService")
@Repository
@Transactional
public class DeviceServiceImpl implements DeviceService {

	@PersistenceContext
	private EntityManager em;
	
	@Transactional(readOnly=true)
	public List<Device> getAll() {
		return em.createQuery("from Device d").getResultList();
	}
	
	@Transactional(readOnly=true)
	public List<String> getAllIds() {
		return em.createQuery("select d.regId from Device d").getResultList();
	}

	@Transactional(readOnly=true)
	public Device getById(String id) {
		TypedQuery<Device> query = em.createNamedQuery("Device.getById", Device.class);
		query.setParameter("id", id);
		return query.getSingleResult();
	}

	public Device save(Device device) {
		if (device.getRegId() == null) { // Insert Contact
			em.persist(device);
		} else {                       // Update Contact
			em.merge(device);
		}
		return device;
	}

	public void delete(Device device) {
		Device mergedDevice = em.merge(device);
		em.remove(mergedDevice);
	}

	@Override
	public void deleteById(String id) {
		Device toDel = this.getById(id);
		Device mergedDevice = em.merge(toDel);
		em.remove(mergedDevice);
	}
}