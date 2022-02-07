package com.everis.d4i.tutorial.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

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
import com.everis.d4i.tutorial.repositories.ActorRepository;
import com.everis.d4i.tutorial.services.ActorService;
import com.everis.d4i.tutorial.utils.constants.ExceptionConstants;

@Service
@Qualifier("Spain")
public class ActorServiceImpl implements ActorService{

	private static final Logger LOGGER = LoggerFactory.getLogger(CategoryServiceImpl.class);

	@Autowired
	private ActorRepository actorRepository;

	private ModelMapper modelMapper = new ModelMapper();
	
	@Override
	public List<ActorRestS> getActors() throws NetflixException {
	
		return actorRepository.findAll().stream().map(actor -> modelMapper.map(actor, ActorRestS.class))
				.collect(Collectors.toList());
	}

	@Override
	public ActorFilmRest getActorById(Long id) throws NetflixException {
		
			
			Actor actor = actorRepository.findActorById(id)
					.orElseThrow(() -> new NotFoundException(ExceptionConstants.MESSAGE_INEXISTENT_ACTOR));
			
			List<Chapter> chapters = actor.getChapters();
			
			List<Season> seasons = chapters.stream().map(chapter ->  chapter.getSeason()).distinct().collect(Collectors.toList());
			
			List<TvShow> tvShows = seasons.stream().map(season ->  season.getTvShow()).distinct().collect(Collectors.toList());
			
			ActorFilmRest actorRest = modelMapper.map(actor, ActorFilmRest.class);
			List<TvShowFilmRest> tvShows2 = tvShows.stream().map(tvShow -> modelMapper.map(tvShow, TvShowFilmRest.class)).collect(Collectors.toList());
			
			actorRest.setTvShows(tvShows2);
			
			return actorRest;
			
	}

	@Override
	public ActorRest createActor(@Valid ActorRest actorRest) throws NetflixException {
		
		Actor actor = new Actor();
		actor.setName(actorRest.getName());
		actor.setSurname(actorRest.getSurname());
		actor.setDate_birth(actorRest.getDate_birth());
		
		try {
			actor = actorRepository.save(actor);
		} catch (final Exception e) {
			LOGGER.error(ExceptionConstants.INTERNAL_SERVER_ERROR, e);
			throw new InternalServerErrorException(ExceptionConstants.INTERNAL_SERVER_ERROR);
		}
		return modelMapper.map(actor, ActorRest.class);
	}

	@Override
	public ActorRest updateActor(Long id, @Valid ActorRest actorRest) throws NetflixException {
		
		this.getActorById(id);
		
		Actor actor = this.actorRepository.getOne(id);
		
		if(actorRest.getName() != null) {
			actor.setName(actorRest.getName());
		}
		if(actorRest.getSurname() != null) {
			actor.setSurname(actorRest.getSurname());
		}
		if(actorRest.getDate_birth() != null) {
			actor.setDate_birth(actorRest.getDate_birth());
		}
		
		try {
			actorRepository.save(actor);
		} catch (final Exception e) {
			LOGGER.error(ExceptionConstants.INTERNAL_SERVER_ERROR, e);
			throw new InternalServerErrorException(ExceptionConstants.INTERNAL_SERVER_ERROR);
		}
		
		return modelMapper.map(actor, ActorRest.class);
	}

	@Override
	public ActorRest deleteActor(Long id) throws NetflixException {
		
		this.getActorById(id);
		
		try {
			actorRepository.deleteById(id);
		} catch (final Exception e) {
			LOGGER.error(ExceptionConstants.MESSAGE_INEXISTENT_ACTOR, e);
			throw new NotFoundException(ExceptionConstants.MESSAGE_INEXISTENT_ACTOR);
		}
		
		return modelMapper.map(actorRepository.findAll(), ActorRest.class);
	}

}
