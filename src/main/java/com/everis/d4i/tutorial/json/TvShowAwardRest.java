package com.everis.d4i.tutorial.json;

import java.io.Serializable;
import java.time.Year;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class TvShowAwardRest implements Serializable{

	private static final long serialVersionUID = 4860574199418708002L;
	
	@ApiModelProperty(position = 0)
	private Long id;
	@ApiModelProperty(position = 1)
	private String name;
	@ApiModelProperty(position = 2)
	private String shortDescription;
	@ApiModelProperty(position = 3)
	private Year year;
	@ApiModelProperty(position = 4)
	private List<AwardRest> awards;
}
