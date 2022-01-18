package com.everis.d4i.tutorial.json;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AwardRest implements Serializable{

	private static final long serialVersionUID = 7154977426146380961L;

	@ApiModelProperty(position = 0)
	private Long id;
	@ApiModelProperty(position = 1)
	private String name;
	@ApiModelProperty(position = 2)
	private String description;
	@ApiModelProperty(position = 3)
	private Date date;
	
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	
}
