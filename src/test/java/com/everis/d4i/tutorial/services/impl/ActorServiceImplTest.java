package com.everis.d4i.tutorial.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;


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

import com.everis.d4i.tutorial.entities.Actor;
import com.everis.d4i.tutorial.entities.Chapter;
import com.everis.d4i.tutorial.entities.Season;
import com.everis.d4i.tutorial.entities.TvShow;
import com.everis.d4i.tutorial.exceptions.InternalServerErrorException;
import com.everis.d4i.tutorial.exceptions.NetflixException;
import com.everis.d4i.tutorial.json.ActorRest;
import com.everis.d4i.tutorial.json.ActorRestS;
import com.everis.d4i.tutorial.repositories.ActorRepository;
import com.everis.d4i.tutorial.repositories.SeasonRepository;
import com.everis.d4i.tutorial.repositories.TvShowRepository;
import com.everis.d4i.tutorial.utils.constants.ExceptionConstants;

class ActorServiceImplTest {
	
	@Mock
	private ActorRepository actorRepository;
	@Mock
	private SeasonRepository seasonRepository;
	@Mock
	private TvShowRepository tvShowRepository;
	
	@InjectMocks
	private ActorServiceImpl actorServiceImpl;
	
	private ModelMapper modelMapper = new ModelMapper();
	
	private Actor actor;
	private ActorRest actorRest;
	private Chapter chapter;
	private Season season;
	private TvShow tvShow;

	@BeforeEach
	void setUp() throws Exception {
		
		MockitoAnnotations.initMocks(this);

		
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
	}

	@Test
	void getActors() throws NetflixException {
		
		when(actorRepository.findAll()).thenReturn(Arrays.asList(actor));
		
		List<ActorRestS> actors = actorServiceImpl.getActors(); 
		
		assertNotNull(actors);
		assertThat(actors.size()).isEqualTo(1);
	
	}
	
	@Test
	void getActorById() throws NetflixException {
		
		when(actorRepository.getOne(actor.getId())).thenReturn(actor);
		when(seasonRepository.getOne(season.getId())).thenReturn(season);
		when(tvShowRepository.getOne(tvShow.getId())).thenReturn(tvShow);
		
		ActorRest actorRest2 = actorServiceImpl.getActorById(actorRest.getId());
		
		assertNotNull(actorRest2);
		assertThat(actorRest2.getId()).isEqualTo(actorRest.getId());
		assertThat(actorRest2.getName()).isEqualTo(actorRest.getName());
		assertThat(actorRest2.getSurname()).isEqualTo(actorRest.getSurname());
		assertThat(actorRest2.getDate_birth()).isEqualTo(actorRest.getDate_birth());
		assertThat(actorRest2.getSurname()).isEqualTo(actorRest.getSurname());
		assertThat(actorRest2.getChapters().equals(actorRest.getChapters()));
	}
	
	@Test
	void createActor() throws NetflixException {
		
		when(actorRepository.save(Mockito.any(Actor.class))).thenReturn(actor);
		
		ActorRest actorRest2 = actorServiceImpl.createActor(actorRest);
	
		assertNotNull(actorRest2);
		assertThat(actorRest2.getId()).isEqualTo(actorRest.getId());
		assertThat(actorRest2.getName()).isEqualTo(actorRest.getName());
		assertThat(actorRest2.getSurname()).isEqualTo(actorRest.getSurname());
		assertThat(actorRest2.getDate_birth()).isEqualTo(actorRest.getDate_birth());
		assertThat(actorRest2.getSurname()).isEqualTo(actorRest.getSurname());
		assertThat(actorRest2.getChapters().equals(actorRest.getChapters()));
	}
	
	@Test
	void updateActor() throws NetflixException {
		
		when(actorRepository.getOne(actor.getId())).thenReturn(actor);
		when(actorRepository.save(actor)).thenReturn(actor);
		
		ActorRest actorRest2 = actorServiceImpl.updateActor(actor.getId(), actorRest);
		
		assertNotNull(actorRest2);
		assertThat(actorRest2.getId()).isEqualTo(actorRest.getId());
		assertThat(actorRest2.getName()).isEqualTo(actorRest.getName());
		assertThat(actorRest2.getSurname()).isEqualTo(actorRest.getSurname());
		assertThat(actorRest2.getDate_birth()).isEqualTo(actorRest.getDate_birth());
		assertThat(actorRest2.getSurname()).isEqualTo(actorRest.getSurname());
		assertThat(actorRest2.getChapters().equals(actorRest.getChapters()));
	}

	@Test
	void deleteActor() throws NetflixException {
		
		String msg = "";
		
		
		doThrow(new IllegalArgumentException()).when(actorRepository).deleteById(actor.getId());

		try {
			
			actorServiceImpl.deleteActor(actor.getId());
			
		} catch (InternalServerErrorException internalServerErrorException) {
			
			 msg = internalServerErrorException.getMessage();
		}
		
		String expectedMessage = ExceptionConstants.INTERNAL_SERVER_ERROR;

		assertEquals(expectedMessage, msg);
	}

}
