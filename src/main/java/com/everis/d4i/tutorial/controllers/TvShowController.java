package com.everis.d4i.tutorial.controllers;

import java.util.List;

import javax.validation.Valid;

import com.everis.d4i.tutorial.exceptions.NetflixException;
import com.everis.d4i.tutorial.json.TvShowAwardRest;
import com.everis.d4i.tutorial.json.TvShowRest;
import com.everis.d4i.tutorial.responses.NetflixResponse;

public interface TvShowController {

	NetflixResponse<List<TvShowRest>> getTvShowsByCategory(Long categoryId) throws NetflixException;

	NetflixResponse<TvShowRest> getTvShowById(Long id) throws NetflixException;

	NetflixResponse<TvShowRest> createTvShow(@Valid TvShowRest tvShowRest) throws NetflixException;

	NetflixResponse<TvShowRest> updateTvShow(Long id, @Valid TvShowRest tvShowRest) throws NetflixException;

	NetflixResponse<TvShowRest> deleteTvShow(Long id) throws NetflixException;

	NetflixResponse<TvShowAwardRest> getTvShowAwards(Long id) throws NetflixException;

}
