package com.everis.d4i.tutorial.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
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

import com.everis.d4i.tutorial.entities.Award;
import com.everis.d4i.tutorial.entities.Category;
import com.everis.d4i.tutorial.entities.TvShow;
import com.everis.d4i.tutorial.exceptions.InternalServerErrorException;
import com.everis.d4i.tutorial.exceptions.NetflixException;
import com.everis.d4i.tutorial.json.TvShowAwardRest;
import com.everis.d4i.tutorial.json.TvShowRest;
import com.everis.d4i.tutorial.repositories.TvShowRepository;
import com.everis.d4i.tutorial.utils.constants.ExceptionConstants;

class TvShowServiceImplTest {
	
	@Mock
	private TvShowRepository tvShowRepository;
	
	@InjectMocks
	private TvShowServiceImpl tvShowServiceImpl;
	
	private ModelMapper modelMapper = new ModelMapper();
	
	private TvShow tvShow;
	private TvShowRest tvShowRest;
	private TvShowAwardRest tvShowAwardRest;
	private Category category;
	private Award award;

	@BeforeEach
	void setUp() throws Exception {
		
		MockitoAnnotations.initMocks(this);
		
		category = new Category();
		category.setId((long) 2);
		category.setName("categoria 1");
		
		tvShow = new TvShow();
		tvShow.setId((long) 1);
		tvShow.setName("Friends");
		tvShow.setLongDescription("Descripcion larga");
		tvShow.setShortDescription("Descripcion corta");
		tvShow.setYear(Year.of(2001));
		tvShow.setRecommendedAge((byte) 12);
		tvShow.setCategories(Arrays.asList(category));
		
		tvShowRest = modelMapper.map(tvShow, TvShowRest.class);
		
		award = new Award();
		award.setId((long) 1);
		award.setName("premio 1");
		award.setDescription("Descripcion");
		award.setDate(new Date());
		award.setTvShow(tvShow);
		
		
		tvShowAwardRest = modelMapper.map(tvShow, TvShowAwardRest.class);
	}

	@Test
	void getTvShowsByCategory() throws NetflixException {
		
		when(tvShowRepository.findByCategoriesId(category.getId())).thenReturn(Arrays.asList(tvShow));
		
		List<TvShowRest> tvShowRest2 = tvShowServiceImpl.getTvShowsByCategory(category.getId());
		
		assertNotNull(tvShowRest2);
		assertFalse(tvShowRest2.isEmpty());
	}
	
	@Test
	void getTvShowById() throws NetflixException {
		
		when(tvShowRepository.getOne(tvShow.getId())).thenReturn(tvShow);
		
		TvShowRest tvShowRest2 = tvShowServiceImpl.getTvShowById(tvShow.getId());
		
		assertNotNull(tvShowRest2);
		assertThat(tvShowRest.getId()).isEqualTo(tvShowRest2.getId());
		assertThat(tvShowRest.getName()).isEqualTo(tvShowRest2.getName());
		assertThat(tvShowRest.getShortDescription()).isEqualTo(tvShowRest2.getShortDescription());
		assertThat(tvShowRest.getLongDescription()).isEqualTo(tvShowRest2.getLongDescription());
		assertThat(tvShowRest.getYear()).isEqualTo(tvShowRest2.getYear());
	}
	
	@Test
	void getTvShowAwards() throws NetflixException {
		
		when(tvShowRepository.getOne(tvShow.getId())).thenReturn(tvShow);
		
		TvShowAwardRest tvShowAwardRest2 = tvShowServiceImpl.getTvShowAwards(tvShow.getId());
		
		assertNotNull(tvShowAwardRest2);
		assertThat(tvShowAwardRest.getId()).isEqualTo(tvShowAwardRest2.getId());
		assertThat(tvShowAwardRest.getName()).isEqualTo(tvShowAwardRest2.getName());
		assertThat(tvShowAwardRest.getShortDescription()).isEqualTo(tvShowAwardRest2.getShortDescription());
		assertThat(tvShowAwardRest.getYear()).isEqualTo(tvShowAwardRest2.getYear());
		assertThat(tvShowAwardRest.getAwards()).isEqualTo(tvShowAwardRest2.getAwards());
	}
	
	@Test
	void createTvShows() throws NetflixException {
		
		when(tvShowRepository.save(Mockito.any(TvShow.class))).thenReturn(tvShow);
		
		TvShowRest  tvShowRest2 = tvShowServiceImpl.createTvShows(tvShowRest);
		
		assertNotNull(tvShowRest2);
		assertThat(tvShowRest.getId()).isEqualTo(tvShowRest2.getId());
		assertThat(tvShowRest.getName()).isEqualTo(tvShowRest2.getName());
		assertThat(tvShowRest.getShortDescription()).isEqualTo(tvShowRest2.getShortDescription());
		assertThat(tvShowRest.getLongDescription()).isEqualTo(tvShowRest2.getLongDescription());
		assertThat(tvShowRest.getYear()).isEqualTo(tvShowRest2.getYear());
	}
	
	@Test
	void updateTvShow() throws NetflixException {
		
		when(tvShowRepository.getOne(tvShow.getId())).thenReturn(tvShow);
		when(tvShowRepository.save(tvShow)).thenReturn(tvShow);
		
		TvShowRest  tvShowRest2 = tvShowServiceImpl.updateTvShow(tvShow.getId() ,tvShowRest);
		
		assertNotNull(tvShowRest2);
		assertThat(tvShowRest.getId()).isEqualTo(tvShowRest2.getId());
		assertThat(tvShowRest.getName()).isEqualTo(tvShowRest2.getName());
		assertThat(tvShowRest.getShortDescription()).isEqualTo(tvShowRest2.getShortDescription());
		assertThat(tvShowRest.getLongDescription()).isEqualTo(tvShowRest2.getLongDescription());
		assertThat(tvShowRest.getYear()).isEqualTo(tvShowRest2.getYear());
	}
	
	@Test
	void deleteTvShow() throws NetflixException {
		
		String msg = "";
		
		this.getTvShowById();
		
		doThrow(new IllegalArgumentException()).when(tvShowRepository).deleteById(tvShow.getId());

		try {
			
			tvShowServiceImpl.deleteTvShow(tvShow.getId());
			
		} catch (InternalServerErrorException internalServerErrorException) {
			
			 msg = internalServerErrorException.getMessage();
		}
		
		String expectedMessage = ExceptionConstants.INTERNAL_SERVER_ERROR;

		assertEquals(expectedMessage, msg);
	}

}
