package com.everis.d4i.tutorial.json;

import java.io.Serializable;
import java.time.Year;
import java.util.List;

public class TvShowAwardRest implements Serializable{

	private static final long serialVersionUID = 4860574199418708002L;
	
	private Long id;
	private String name;
	private String shortDescription;
	private Year year;
	private List<AwardRest> awards;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getShortDescription() {
		return shortDescription;
	}
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public Year getYear() {
		return year;
	}
	public void setYear(Year year) {
		this.year = year;
	}

	public List<AwardRest> getAwards() {
		return awards;
	}
	public void setAwards(List<AwardRest> awards) {
		this.awards = awards;
	}

	
}
