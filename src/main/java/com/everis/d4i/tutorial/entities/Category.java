package com.everis.d4i.tutorial.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CATEGORIES")
@Data @NoArgsConstructor @AllArgsConstructor
public class Category implements Serializable {

	private static final long serialVersionUID = 180802329613616000L;
	
	
	public Category(Long id, String name) {
		
		this.id = id;
		this.name = name;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "NAME", unique = true)
	private String name;

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "categories")
	private List<TvShow> tvShows;

}
