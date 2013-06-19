package com.campusconnect.server.service.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.campusconnect.server.service.UserService;

@Service("jpaUserService")
@Repository
@Transactional
public class UserServiceImpl implements UserService {

	@PersistenceContext
	private EntityManager em;
}
