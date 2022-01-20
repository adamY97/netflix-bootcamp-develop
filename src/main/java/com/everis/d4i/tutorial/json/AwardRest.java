package com.everis.d4i.tutorial.json;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
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
	
	
}
