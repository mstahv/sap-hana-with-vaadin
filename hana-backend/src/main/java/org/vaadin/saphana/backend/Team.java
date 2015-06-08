package org.vaadin.saphana.backend;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "T_TEAMS")
public class Team extends AbstractEntity {
	
	private String teamName;
	
	public Team() {
	}

	public String getTeamName() {
		return teamName;
	}
	
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	
	@Override
	public String toString() {
		return teamName;
	}

}
