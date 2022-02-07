package com.everis.d4i.tutorial.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;


import java.time.Year;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
import com.everis.d4i.tutorial.exceptions.NotFoundException;
import com.everis.d4i.tutorial.json.ActorFilmRest;
import com.everis.d4i.tutorial.json.ActorRest;
import com.everis.d4i.tutorial.json.ActorRestS;
import com.everis.d4i.tutorial.json.TvShowFilmRest;
import com.everis.d4i.tutorial.json.TvShowRest;
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
	private Optional<Actor> actorOptional;
	private ActorFilmRest actorFilmRest;
	private ActorRest actorRest;
	private Chapter chapter;
	private Season season;
	private TvShow tvShow;
	private TvShowRest tvShowRest;
	private TvShowFilmRest tvShowFilmRest;

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
		
		actorOptional = Optional.of(actor);
		
		tvShowFilmRest = new TvShowFilmRest();
		tvShowFilmRest.setId((long) 2);
		tvShowFilmRest.setName("Friends");
		tvShowFilmRest.setLongDescription("Descripcion larga");
		tvShowFilmRest.setShortDescription("Descripcion corta");
	
		actorFilmRest = new ActorFilmRest();
		actorFilmRest.setId((long) 5);
		actorFilmRest.setName("Adam");
		actorFilmRest.setSurname("Yacobi");
		actorFilmRest.setDate_birth(new Date());
		actorFilmRest.setTvShows(Arrays.asList(tvShowFilmRest));
		
		tvShowRest = modelMapper.map(tvShow, TvShowRest.class);
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
		
		when(actorRepository.findActorById(actor.getId())).thenReturn(actorOptional);
		
		ActorFilmRest actorFilmRest2 = actorServiceImpl.getActorById(actor.getId());
		
		assertNotNull(actorFilmRest2);
		assertThat(actorFilmRest2.getId()).isEqualTo(actorFilmRest.getId());
		assertThat(actorFilmRest2.getName()).isEqualTo(actorFilmRest.getName());
		assertThat(actorFilmRest2.getSurname()).isEqualTo(actorFilmRest.getSurname());
		assertThat(actorFilmRest2.getDate_birth()).isEqualTo(actorFilmRest.getDate_birth());
		assertThat(actorFilmRest2.getSurname()).isEqualTo(actorFilmRest.getSurname());
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
		
		this.getActorById();
		
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
		
		this.getActorById();
		
		doThrow(new IllegalArgumentException()).when(actorRepository).deleteById(actor.getId());

		Exception exception = assertThrows(NotFoundException.class, () -> {
			actorServiceImpl.deleteActor(actor.getId());
		});
		
		String expectedMessage = ExceptionConstants.MESSAGE_INEXISTENT_ACTOR;
		String actualMessage = exception.getMessage();
	
	
	
		assertTrue(actualMessage.contains(expectedMessage));
	}
	
//	@Test
//	void deleteActorById() throws NetflixException {
//	doThrow(new IllegalArgumentException()).when(actorRepository).deleteById();
//
//
//
//	Exception exception = assertThrows(NotFoundException.class, () -> {
//	actorService.deleteActorById();
//	});
//
//
//
//	String expectedMessage = ExceptionConstants.MESSAGE_INEXISTENT_ACTOR;
//	String actualMessage = exception.getMessage();
//
//
//
//	assertTrue(actualMessage.contains(expectedMessage));
//	}

}
