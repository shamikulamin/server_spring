package com.campusconnect.server.service.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.campusconnect.server.domain.IncidentMsg;
import com.campusconnect.server.service.IncidentMsgService;

@Service("jpaIncidentMsgService")
@Repository
@Transactional
public class IncidentMsgServiceImpl implements IncidentMsgService {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public List<IncidentMsg> getAll() {
		return em.createQuery("FROM IncidentMsg im").getResultList();
	}

	@Override
	public IncidentMsg getById(Long id) {
		TypedQuery<IncidentMsg> query = em.createNamedQuery("IncidentMsg.getById", IncidentMsg.class);
		query.setParameter("id", id.intValue());
		return query.getSingleResult();
	}

	@Override
	public IncidentMsg save(IncidentMsg msg) {
		if (msg.getIncidentId() == null) { // Insert Contact
			em.persist(msg);
		} else {                       // Update Contact
			em.merge(msg);
		}
		return msg;
	}
	
	@Override
	public void delete(IncidentMsg msg) {
		IncidentMsg mergedMsg = em.merge(msg);
		em.remove(msg);
	}
}
