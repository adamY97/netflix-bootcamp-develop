package com.everis.d4i.tutorial.controllers;

import java.util.List;

import javax.validation.Valid;

import com.everis.d4i.tutorial.exceptions.NetflixException;
import com.everis.d4i.tutorial.json.ActorFilmRest;
import com.everis.d4i.tutorial.json.ActorRest;
import com.everis.d4i.tutorial.json.ActorRestS;
import com.everis.d4i.tutorial.responses.NetflixResponse;

public interface ActorController {

	NetflixResponse<List<ActorRestS>> getActors() throws NetflixException;

	NetflixResponse<ActorFilmRest> getActorById(Long id) throws NetflixException;

	NetflixResponse<ActorRest> createActor(@Valid ActorRest actorRest) throws NetflixException;

	NetflixResponse<ActorRest> updateActor(Long id, @Valid ActorRest actorRest) throws NetflixException;

	NetflixResponse<ActorRest> deleteActor(Long id) throws NetflixException;
}
