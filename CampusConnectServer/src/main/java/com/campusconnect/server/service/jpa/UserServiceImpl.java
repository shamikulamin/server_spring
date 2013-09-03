package com.campusconnect.server.service.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.campusconnect.server.domain.User;
import com.campusconnect.server.service.UserService;

@Service("jpaUserService")
@Repository
@Transactional
public class UserServiceImpl implements UserService {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	@Transactional(readOnly=true)
	public User getById(String id) {
		TypedQuery<User> query = em.createNamedQuery("User.getById", User.class);
		query.setParameter("id", id);
		return query.getSingleResult();
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<User> getAllExcept(String except) {
		TypedQuery<User> query = em.createNamedQuery("User.getByIdExcept", User.class);
		query.setParameter("id", except);
		return query.getResultList();
	}

	@Override
	public User save(User u) {
		if (u.getUsername() == null) { // Insert User
			em.persist(u);
		} else {                       // Update User
			em.merge(u);
		}
		return u;
	}

	public void delete(User u) {
		User mergedUser = em.merge(u);
		em.remove(mergedUser);
	}

	@Override
	@Transactional(readOnly=true)
	public List<User> getAll() {
		return em.createQuery("from User u").getResultList();
	}
}
