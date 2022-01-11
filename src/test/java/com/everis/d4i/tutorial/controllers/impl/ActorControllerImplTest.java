package com.everis.d4i.tutorial.controllers.impl;

//fail(mockito.perform(get(*RUTA*).contentType(MediaType.APPLICATION_JSON_UTF8)).andReturn().getResponse().getContentAsString());

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Year;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.everis.d4i.tutorial.entities.Actor;
import com.everis.d4i.tutorial.entities.Chapter;
import com.everis.d4i.tutorial.entities.Season;
import com.everis.d4i.tutorial.entities.TvShow;
import com.everis.d4i.tutorial.json.ActorRest;
import com.everis.d4i.tutorial.json.ActorRestS;
import com.everis.d4i.tutorial.services.ActorService;
import com.everis.d4i.tutorial.utils.constants.RestConstants;


class ActorControllerImplTest {
	
	private MockMvc mockito;
	
	private ModelMapper modelMapper = new ModelMapper();
	
	@Mock
	private ActorService actorService;
	
	@InjectMocks
	private ActorControllerImpl actorControllerImpl;
	
	private Actor actor;
	private Chapter chapter;
	private Season season;
	private TvShow tvShow;
	private ActorRest actorRest;
	private ActorRestS actorRestS;

	@BeforeEach
	void setUp() throws Exception {
		
		MockitoAnnotations.initMocks(this);
		
		mockito = MockMvcBuilders.standaloneSetup(actorControllerImpl).build();
		
		tvShow = new TvShow();
		tvShow.setId((long) 1);
		tvShow.setName("Friends");
		tvShow.setLongDescription("Descripcion larga");
		tvShow.setShortDescription("Descripcion corta");
		tvShow.setYear(Year.of(2001));
		tvShow.setRecommendedAge((byte) 12);
		
		season = new Season();
		season.setId((long) 2);
		season.setName("temporada 1");
		season.setNumber((short) 1);
		season.setTvShow(tvShow);

		chapter = new Chapter();
		chapter.setId((long) 3);
		chapter.setNumber((short) 2);
		chapter.setName("capitulo 1");
		chapter.setDuration((short) 45);
		chapter.setSeason(season);
		
		actor = new Actor();
		actor.setId((long) 5);
		actor.setName("Adam");
		actor.setSurname("Yacobi");
		actor.setDate_birth(new Date());
		actor.setChapters(Arrays.asList(chapter));
		
		
		actorRest = modelMapper.map(actor, ActorRest.class);
		
		actorRestS = modelMapper.map(actor, ActorRestS.class);
		
		
	}

	@Test
	void getActors() throws Exception{
		
		List<ActorRestS> actorRest = Arrays.asList(actorRestS);
		
		when(actorService.getActors()).thenReturn(actorRest);
		
		mockito.perform(get(RestConstants.APPLICATION_NAME + RestConstants.API_VERSION_1 + RestConstants.RESOURCE_ACTOR))
		
			   .andExpect(status().isOk())
		       .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
		       .andExpect(jsonPath(("$.data[0].name"), is(actorRestS.getName())))
		       .andExpect(jsonPath(("$.data.length()"), is(actorRest.size())));
		
	}
	
	@Test
	void getActorById() throws Exception{
		
		when(actorService.getActorById(actor.getId())).thenReturn(actorRest);
		
		mockito.perform(get(RestConstants.APPLICATION_NAME + RestConstants.API_VERSION_1 + RestConstants.RESOURCE_ACTOR
				+ RestConstants.RESOURCE_ID , actor.getId()))
		
			   .andExpect(status().isOk())
		       .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
		       .andExpect(jsonPath(("$.data.id"), is(Math.toIntExact(actor.getId()))))
		       .andExpect(jsonPath(("$.data.name"), is(actor.getName())))
		       .andExpect(jsonPath(("$.data.surname"), is(actor.getSurname())))
		       .andExpect(jsonPath(("$.data.date_birth"), is(actor.getDate_birth().getTime())));
		
	}
	
	@Test
	void createActor() throws Exception{
		
		when(actorService.createActor(Mockito.any(ActorRest.class))).thenReturn(actorRest);
		
		mockito.perform(post(RestConstants.APPLICATION_NAME + RestConstants.API_VERSION_1 + RestConstants.RESOURCE_ACTOR
				+ RestConstants.RESOURCE_CREATE));
		
//		   .andExpect(status().isCreated())
//	       .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
//	       .andExpect(jsonPath(("$.data.name"), is(actor.getName())));
		
	}

}
