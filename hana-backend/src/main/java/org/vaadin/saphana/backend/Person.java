package org.vaadin.saphana.backend;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "T_PERSONS")
public class Person extends AbstractEntity {

	private String firstName;
	private String lastName;

	@Temporal(TemporalType.DATE)
	private Date birthDay;

	@ManyToOne
	private Team team;

	public Person() {
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String newFirstName) {
		this.firstName = newFirstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String newLastName) {
		this.lastName = newLastName;
	}

	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

	public Date getBirthDay() {
		return birthDay;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

}