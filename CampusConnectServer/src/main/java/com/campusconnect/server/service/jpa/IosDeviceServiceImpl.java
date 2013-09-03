package com.campusconnect.server.service.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.campusconnect.server.domain.IosDevice;
import com.campusconnect.server.service.IosDeviceService;

@Service("jpaIosDeviceService")
@Repository
@Transactional
public class IosDeviceServiceImpl implements IosDeviceService {

	@PersistenceContext
	private EntityManager em;
	
	@Transactional(readOnly=true)
	public List<IosDevice> getAll() {
		return em.createQuery("from IosDevice d").getResultList();
	}
	
	@Transactional(readOnly=true)
	public List<String> getAllIds() {
		return em.createQuery("select d.regId from IosDevice d").getResultList();
	}

	@Transactional(readOnly=true)
	public IosDevice getById(String id) {
		TypedQuery<IosDevice> query = em.createNamedQuery("IosDevice.getById", IosDevice.class);
		query.setParameter("id", id);
		return query.getSingleResult();
	}

	public IosDevice save(IosDevice device) {
		if (device.getRegId() == null) { // Insert Contact
			em.persist(device);
		} else {                       // Update Contact
			em.merge(device);
		}
		return device;
	}

	public void delete(IosDevice device) {
		IosDevice mergedDevice = em.merge(device);
		em.remove(mergedDevice);
	}

	@Override
	public void deleteById(String id) {
		IosDevice toDel = this.getById(id);
		IosDevice mergedDevice = em.merge(toDel);
		em.remove(mergedDevice);
	}
}