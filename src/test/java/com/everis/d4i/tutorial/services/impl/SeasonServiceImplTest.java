package com.everis.d4i.tutorial.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.time.Year;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
//import org.modelmapper.ModelMapper;

import com.everis.d4i.tutorial.entities.Season;
import com.everis.d4i.tutorial.entities.TvShow;
import com.everis.d4i.tutorial.exceptions.NetflixException;
import com.everis.d4i.tutorial.json.SeasonRest;
import com.everis.d4i.tutorial.repositories.SeasonRepository;

class SeasonServiceImplTest {

	@Mock
	private SeasonRepository seasonRepository;

	@InjectMocks
	private SeasonServiceImpl seasonServiceImpl;

//	private ModelMapper modelMapper = new ModelMapper();
	
	private Season season;
	private TvShow tvShow;
	
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

		
	}

	@Test
	void getSeasonsByTvShow() throws NetflixException {
		
		when(seasonRepository.findByTvShowId(tvShow.getId())).thenReturn(Arrays.asList(season));
		
		List<SeasonRest> seasonRest2 = seasonServiceImpl.getSeasonsByTvShow(tvShow.getId());
		
		assertNotNull(seasonRest2);
		assertFalse(seasonRest2.isEmpty());
	}
	
	@Test
	void getSeasonByTvShowIdAndSeasonNumber() throws NetflixException {
		
		when(seasonRepository.findByTvShowIdAndNumber(tvShow.getId(), season.getNumber())).thenReturn(Optional.of(season));
		
		SeasonRest seasonRest2 = seasonServiceImpl.getSeasonByTvShowIdAndSeasonNumber(tvShow.getId(), season.getNumber());
		
		assertNotNull(seasonRest2);
		assertThat(season.getId()).isEqualTo(seasonRest2.getId());
		assertThat(season.getName()).isEqualTo(seasonRest2.getName());
	}

}
