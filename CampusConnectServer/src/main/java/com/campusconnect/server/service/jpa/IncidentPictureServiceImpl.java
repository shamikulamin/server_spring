package com.campusconnect.server.service.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.campusconnect.server.domain.IncidentMsg;
import com.campusconnect.server.domain.IncidentPicture;
import com.campusconnect.server.service.IncidentPictureService;

@Service("jpaIncidentPictureService")
@Repository
@Transactional
public class IncidentPictureServiceImpl implements IncidentPictureService {

	@PersistenceContext
	private EntityManager em;

	@Override
	public List<IncidentPicture> getAll() {
		return em.createQuery("FROM IncidentPicture ip").getResultList();
	}

	@Override
	public IncidentPicture save(IncidentPicture pic) {
		if (pic.getIncidentMsg() == null) { // Insert IncidentPic
			em.persist(pic);
		} else {                       // Update IncidentPic
			em.merge(pic);
		}
		return pic;
	}

	@Override
	public void delete(IncidentPicture pic) {
		IncidentPicture mergedPic = em.merge(pic);
		em.remove(pic);
	}
}
