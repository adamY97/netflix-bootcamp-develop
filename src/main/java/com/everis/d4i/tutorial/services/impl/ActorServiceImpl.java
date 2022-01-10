package com.everis.d4i.tutorial.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.everis.d4i.tutorial.entities.Actor;
import com.everis.d4i.tutorial.entities.Chapter;
import com.everis.d4i.tutorial.exceptions.InternalServerErrorException;
import com.everis.d4i.tutorial.exceptions.NetflixException;
import com.everis.d4i.tutorial.exceptions.NotFoundException;
import com.everis.d4i.tutorial.json.ActorRest;
import com.everis.d4i.tutorial.json.ActorRestS;
import com.everis.d4i.tutorial.json.TvShowRest;
import com.everis.d4i.tutorial.repositories.ActorRepository;
import com.everis.d4i.tutorial.repositories.SeasonRepository;
import com.everis.d4i.tutorial.repositories.TvShowRepository;
import com.everis.d4i.tutorial.services.ActorService;
import com.everis.d4i.tutorial.utils.constants.ExceptionConstants;

@Service
public class ActorServiceImpl implements ActorService{

	private static final Logger LOGGER = LoggerFactory.getLogger(CategoryServiceImpl.class);

	@Autowired
	private ActorRepository actorRepository;
	@Autowired
	private SeasonRepository seasonRepository;
	@Autowired
	private TvShowRepository tvShowRepository;

	private ModelMapper modelMapper = new ModelMapper();
	
	@Override
	public List<ActorRestS> getActors() throws NetflixException {
	
		return actorRepository.findAll().stream().map(actor -> modelMapper.map(actor, ActorRestS.class))
				.collect(Collectors.toList());
	}

	@Override
	public ActorRest getActorById(Long id) throws NetflixException {
		
		try {
			
			Actor actor = actorRepository.getOne(id);
			
			List<Chapter> chapters = actor.getChapters();
			
			List<Long> seasons = new ArrayList<>();
			
			List<Long> seasonsOrd = new ArrayList<>();
			
			List<Long> tvShows = new ArrayList<>();
			
			List<Long> tvShowsOrd = new ArrayList<>();
			
			List<TvShowRest> tvShowList = new ArrayList<>();
			
			chapters.stream().forEach(c -> seasons.add(c.getSeason().getId()));
			
			seasonsOrd = seasons.stream().distinct().collect(Collectors.toList());
		
			seasonsOrd.stream().forEach(c -> tvShows.add(seasonRepository.getOne(c).getTvShow().getId()));
			
			tvShowsOrd = tvShows.stream().distinct().collect(Collectors.toList());
			
			tvShowsOrd.stream().forEach(c -> tvShowList.add(modelMapper.map(tvShowRepository.getOne(c), TvShowRest.class)));
			
			ActorRest actorRest = modelMapper.map(actor, ActorRest.class);
			
			actorRest.setTvShows(tvShowList);
			
			return actorRest;
			
		} catch (final Exception e) {
			LOGGER.error(ExceptionConstants.ID_ERROR, e);
			throw new NotFoundException(ExceptionConstants.ID_ERROR);
		}
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
			LOGGER.error(ExceptionConstants.INTERNAL_SERVER_ERROR, e);
			throw new InternalServerErrorException(ExceptionConstants.INTERNAL_SERVER_ERROR);
		}
		
		return modelMapper.map(actorRepository.findAll(), ActorRest.class);
	}

}
