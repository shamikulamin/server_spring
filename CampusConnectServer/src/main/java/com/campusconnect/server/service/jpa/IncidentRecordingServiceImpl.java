package com.campusconnect.server.service.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.campusconnect.server.domain.IncidentRecording;
import com.campusconnect.server.service.IncidentRecordingService;

@Service("jpaIncidentRecordingService")
@Repository
@Transactional
public class IncidentRecordingServiceImpl implements IncidentRecordingService {

	@PersistenceContext
	private EntityManager em;

	@Override
	public List<IncidentRecording> getAll() {
		return em.createQuery("FROM IncidentRecording ir").getResultList();
	}

	@Override
	public IncidentRecording save(IncidentRecording rec) {
		if (rec.getIncidentMsg() == null) { // Insert IncidentRec
			em.persist(rec);
		} else {                       // Update IncidentRec
			em.merge(rec);
		}
		return rec;
	}

	@Override
	public void delete(IncidentRecording rec) {
		IncidentRecording mergedPic = em.merge(rec);
		em.remove(rec);
	}
}
