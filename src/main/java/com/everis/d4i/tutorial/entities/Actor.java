package com.everis.d4i.tutorial.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Entity
@Table(name = "ACTORS")
@Data
public class Actor implements Serializable{

	private static final long serialVersionUID = 2455208515714808003L;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "NAME")
	@NotEmpty
	private String name;

	@Column(name = "SURNAME")
	private String surname;
	
	@Column(name = "DATE_BIRTH")
	private Date date_birth;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name = "ACTOR_CHAPTER",
			joinColumns = @JoinColumn(name = "ACTOR_ID"),
			inverseJoinColumns = @JoinColumn(name = "CHAPTER_ID"))
	private List<Chapter> chapters;	
	
}
