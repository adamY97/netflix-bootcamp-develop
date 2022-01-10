package com.everis.d4i.tutorial.services;

import java.util.List;

import javax.validation.Valid;

import com.everis.d4i.tutorial.exceptions.NetflixException;
import com.everis.d4i.tutorial.json.TvShowAwardRest;
import com.everis.d4i.tutorial.json.TvShowRest;

public interface TvShowService {

	List<TvShowRest> getTvShowsByCategory(Long categoryId) throws NetflixException;

	TvShowRest getTvShowById(Long id) throws NetflixException;

	TvShowRest createTvShows(@Valid TvShowRest tvShowRest) throws NetflixException;

	TvShowRest updateTvShow(Long id, @Valid TvShowRest tvShowRest) throws NetflixException;

	TvShowRest deleteTvShow(Long id) throws NetflixException;

	TvShowAwardRest getTvShowAwards(Long id) throws NetflixException;


}
