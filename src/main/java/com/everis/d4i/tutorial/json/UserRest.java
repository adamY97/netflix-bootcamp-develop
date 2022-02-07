package com.everis.d4i.tutorial.json;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data 
public class UserRest  implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(position = 0)
	private Long id;
	@ApiModelProperty(position = 1)
	private String name;
	@ApiModelProperty(position = 2)
	private String surname;
	@ApiModelProperty(position = 3)
	private String username;
	@ApiModelProperty(position = 4)
	private String password;
}
