//package com.everis.d4i.tutorial.entities;
//
//import java.io.Serializable;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.Table;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Entity
//@Table(name = "USERS")
//@Data @NoArgsConstructor @AllArgsConstructor
//public class Users implements Serializable{
//	
//	private static final long serialVersionUID = 1L;
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Long id;
//	
//	@Column(name = "NAME")
//	private String name;
//	
//	@Column(name = "SURNAME")
//	private String surname;
//	
//	@Column(name = "EMAIL")
//	private String email;
//	
//	@Column(name = "USERNAME")
//	private String username;
//	
//	@Column(name = "PASSWORD")
//	private String password;
//	
//	@Column(name = "TYPE")
//	private String type;
//	
//}
