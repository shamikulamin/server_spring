package com.campusconnect.server.service.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.campusconnect.server.domain.CommunityMsg;
import com.campusconnect.server.service.CommunityMsgService;

@Service("jpaCommunityMsgService")
@Repository
@Transactional
public class CommunityMsgServiceImpl implements CommunityMsgService {

	@PersistenceContext
	private EntityManager em;
	
	@Transactional(readOnly=true)
	public List<CommunityMsg> getAll() {
		return em.createQuery("FROM CommunityMsg c").getResultList();
	}

	@Transactional(readOnly=true)
	public List<CommunityMsg> getAllValid() {
		return em.createQuery("FROM CommunityMsg c where CURRENT_TIMESTAMP() < c.expiryTime order by c.reportingTime DESC").getResultList();
	}

	@Transactional(readOnly=true)
	public List<CommunityMsg> getAllWithLocation() {
		return em.createQuery("FROM CommunityMsg c where CURRENT_TIMESTAMP() < c.expiryTime AND c.latlong <> 'none' order by c.reportingTime DESC").getResultList();
	}

	@Transactional(readOnly=true)
	public List<CommunityMsg> getAllValidWithLocation() {
		return em.createQuery("From CommunityMsg c where c.latlong <> 'none' and CURRENT_TIMESTAMP() < c.expiryTime order by c.reportingTime DESC").getResultList();
	}

	@Transactional(readOnly=true)
	public CommunityMsg getById(Integer id) {
		TypedQuery<CommunityMsg> query = em.createNamedQuery("CommunityMsg.getById", CommunityMsg.class);
		query.setParameter("id", id);
		return query.getSingleResult();
	}

	@Override
	public CommunityMsg save(CommunityMsg commMsg) {
		if (commMsg.getCommMsgId() == null) { // Insert Contact
			em.persist(commMsg);
		} else {                       // Update Contact
			em.merge(commMsg);
		}
		return commMsg;
	}

	@Override
	public void delete(CommunityMsg commMsg) {
		CommunityMsg mergedComm = em.merge(commMsg);
		em.remove(mergedComm);
	}

}
