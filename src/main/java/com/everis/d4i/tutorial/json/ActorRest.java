package com.everis.d4i.tutorial.json;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ActorRest implements Serializable{

	private static final long serialVersionUID = 2562292635410148858L;

	@ApiModelProperty(position = 0)
	private Long id;
	@ApiModelProperty(position = 1)
	private String name;
	@ApiModelProperty(position = 2)
	private String surname;
	@ApiModelProperty(position = 3)
	private Date date_birth;
	@ApiModelProperty(position = 4)
	private List<TvShowRest> tvShows;
	@ApiModelProperty(position = 5)
	private List<ChapterRest> chapters;
	
}
