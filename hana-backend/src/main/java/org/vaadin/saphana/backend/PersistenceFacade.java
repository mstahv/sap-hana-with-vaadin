package org.vaadin.saphana.backend;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.mysema.query.jpa.impl.JPAQuery;

@Stateless
@LocalBean
public class PersistenceFacade {

	@PersistenceContext
	private EntityManager em;

	/**
	 * Get all persons from the table.
	 */
	public List<Person> getAllPersons() {
		return em.createQuery("select p from Person p", Person.class).getResultList();
	}
	
	/**
	 * Get all persons from the table.
	 */
	public List<Team> getGroups() {
		return new JPAQuery(em).from(QTeam.team)
				.list(QTeam.team);
	}

	
	public void save(Person person) {
		if(person.getCreated() == null) {
			em.persist(person);
			em.flush();
		} else {
			em.merge(person);
		}
	}

	public Team save(Team team) {
		if(team.getCreated() == null) {
			em.persist(team);
			em.flush();
			return team;
		} else {
			return em.merge(team);
		}
	}

	public void delete(Person entity) {
		em.remove(em.find(Person.class, entity.getId()));
	}

	public List<Person> getPersonsMatching(String filter) {
		JPAQuery query = new JPAQuery(em);
		// If next line don't compile, you haven't yet done "priming build"
		// Issue mvn install at command line and refresh the project or 
		// run full maven install from your IDE
		QPerson p = QPerson.person;
		List<Person> list = query.from(p)
				.where(
						p.firstName.containsIgnoreCase(filter)
						.or(p.lastName.containsIgnoreCase(filter)
				)).list(p);
		return list;
	}
}
