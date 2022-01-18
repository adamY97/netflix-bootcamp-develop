package com.everis.d4i.tutorial.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.everis.d4i.tutorial.entities.Category;
import com.everis.d4i.tutorial.entities.TvShow;
import com.everis.d4i.tutorial.exceptions.InternalServerErrorException;
import com.everis.d4i.tutorial.exceptions.NetflixException;
import com.everis.d4i.tutorial.exceptions.NotFoundException;
import com.everis.d4i.tutorial.json.CategoryRest;
import com.everis.d4i.tutorial.json.TvShowAwardRest;
import com.everis.d4i.tutorial.json.TvShowRest;
import com.everis.d4i.tutorial.repositories.TvShowRepository;
import com.everis.d4i.tutorial.services.TvShowService;
import com.everis.d4i.tutorial.utils.constants.ExceptionConstants;

@Service
public class TvShowServiceImpl implements TvShowService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CategoryServiceImpl.class);
	
	@Autowired
	private TvShowRepository tvShowRepository;

	private ModelMapper modelMapper = new ModelMapper();

	@Override
	public List<TvShowRest> getTvShowsByCategory(Long categoryId) throws NetflixException {

		return tvShowRepository.findByCategoriesId(categoryId).stream()
				.map(tvShow -> modelMapper.map(tvShow, TvShowRest.class)).collect(Collectors.toList());

	}

	@Override
	public TvShowRest getTvShowById(Long id) throws NetflixException {

		try {
			return modelMapper.map(tvShowRepository.getOne(id), TvShowRest.class);
		} catch (final Exception e) {
			LOGGER.error(ExceptionConstants.ID_ERROR, e);
			throw new NotFoundException(ExceptionConstants.ID_ERROR);
		}

	}
	
	@Override
	public TvShowAwardRest getTvShowAwards(Long id) throws NetflixException {

		try {
			return modelMapper.map(tvShowRepository.getOne(id), TvShowAwardRest.class);
		} catch (EntityNotFoundException entityNotFoundException) {
			throw new NotFoundException(entityNotFoundException.getMessage());
		}

	}

	@Override
	public TvShowRest createTvShows(@Valid TvShowRest tvShowRest) throws NetflixException {
		
		TvShow tvShow = new TvShow();
		tvShow.setName(tvShowRest.getName());
		tvShow.setShortDescription(tvShowRest.getShortDescription());
		tvShow.setLongDescription(tvShowRest.getLongDescription());
		tvShow.setYear(tvShowRest.getYear());
		tvShow.setRecommendedAge(tvShowRest.getRecommendedAge());
		tvShow.setAdvertising(tvShowRest.getAdvertising());
		
		List<CategoryRest>categories = tvShowRest.getCategories();
		List<Category>categorias = new ArrayList<>();
		
		categories.stream().forEach(c -> categorias.add(new Category(c.getId(), c.getName())));
		
		tvShow.setCategories(categorias);
		
		try {
			tvShow = tvShowRepository.save(tvShow);
		} catch (final Exception e) {
			LOGGER.error(ExceptionConstants.INTERNAL_SERVER_ERROR, e);
			throw new InternalServerErrorException(ExceptionConstants.INTERNAL_SERVER_ERROR);
		}
		return modelMapper.map(tvShow, TvShowRest.class);
	}

	@Override
	public TvShowRest updateTvShow(Long id, @Valid TvShowRest tvShowRest) throws NetflixException {
		
		this.getTvShowById(id);
		
		TvShow tvShow = this.tvShowRepository.getOne(id);
		
		tvShow.setName(tvShowRest.getName());
		
		try {
			tvShowRepository.save(tvShow);
		} catch (final Exception e) {
			LOGGER.error(ExceptionConstants.INTERNAL_SERVER_ERROR, e);
			throw new InternalServerErrorException(ExceptionConstants.INTERNAL_SERVER_ERROR);
		}
		
		return modelMapper.map(tvShow, TvShowRest.class);
	}

	@Override
	public TvShowRest deleteTvShow(Long id) throws NetflixException {
		
		this.getTvShowById(id);
		
		try {
			tvShowRepository.deleteById(id);
		} catch (final Exception e) {
			LOGGER.error(ExceptionConstants.INTERNAL_SERVER_ERROR, e);
			throw new InternalServerErrorException(ExceptionConstants.INTERNAL_SERVER_ERROR);
		}
		
		return modelMapper.map(tvShowRepository.findAll(), TvShowRest.class);
	}
}
