package com.everis.d4i.tutorial.services.impl;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.everis.d4i.tutorial.exceptions.NetflixException;
import com.everis.d4i.tutorial.json.ActorRest;
import com.everis.d4i.tutorial.json.ActorRestS;
import com.everis.d4i.tutorial.services.ActorService;

@Service
@Qualifier("France")
public class ActorServiceImpl2 implements ActorService{

	@Override
	public List<ActorRestS> getActors() throws NetflixException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActorRest getActorById(Long id) throws NetflixException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActorRest createActor(@Valid ActorRest actorRest) throws NetflixException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActorRest updateActor(Long id, @Valid ActorRest actorRest) throws NetflixException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActorRest deleteActor(Long id) throws NetflixException {
		// TODO Auto-generated method stub
		return null;
	}

}
