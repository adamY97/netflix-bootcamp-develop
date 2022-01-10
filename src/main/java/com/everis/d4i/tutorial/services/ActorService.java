package com.everis.d4i.tutorial.services;

import java.util.List;

import javax.validation.Valid;

import com.everis.d4i.tutorial.exceptions.NetflixException;
import com.everis.d4i.tutorial.json.ActorRest;
import com.everis.d4i.tutorial.json.ActorRestS;

public interface ActorService {
	
	List<ActorRestS> getActors() throws NetflixException;

	ActorRest getActorById(Long id) throws NetflixException;

	ActorRest createActor(@Valid ActorRest actorRest) throws NetflixException;

	ActorRest updateActor(Long id, @Valid ActorRest actorRest) throws NetflixException;

	ActorRest deleteActor(Long id) throws NetflixException;
	

}
