package org.group7.medical.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.group7.medical.domain.Allergy;
import org.group7.medical.domain.Illness;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SymptomService {

	@PersistenceContext
	public EntityManager em;
	
	@Transactional(readOnly = true)
	public List<Illness> findAllIllnesses() {
		return em.createQuery("FROM Illness i", Illness.class)
				.getResultList();
	}

	@Transactional(readOnly = true)
	public List<Allergy> findAllAllergies() {
		return em.createQuery("FROM Allergy a", Allergy.class)
				.getResultList();
	}
}
