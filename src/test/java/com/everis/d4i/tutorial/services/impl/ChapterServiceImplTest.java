package com.everis.d4i.tutorial.services.impl;


import static org.assertj.core.api.Assertions.assertThat;
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
import org.modelmapper.ModelMapper;

import com.everis.d4i.tutorial.entities.Chapter;
import com.everis.d4i.tutorial.entities.Season;
import com.everis.d4i.tutorial.entities.TvShow;
import com.everis.d4i.tutorial.exceptions.NetflixException;
import com.everis.d4i.tutorial.json.ChapterRest;
import com.everis.d4i.tutorial.repositories.ChapterRepository;

class ChapterServiceImplTest {
	
	@Mock
	private ChapterRepository chapterRepository;
	
	@InjectMocks
	private ChapterServiceImpl chapterServiceImpl;

	private ModelMapper modelMapper = new ModelMapper();
	
	private Season season;
	private Chapter chapter;
	private ChapterRest chapterRest;
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
		
		chapter = new Chapter();
		chapter.setId((long) 3);
		chapter.setNumber((short) 2);
		chapter.setName("capitulo 1");
		chapter.setDuration((short) 45);
		chapter.setSeason(season);
		
		chapterRest = modelMapper.map(chapter, ChapterRest.class);
	}

	@Test
	void getChaptersByTvShowIdAndSeasonNumber() throws NetflixException {
		
		when(chapterRepository.findBySeasonTvShowIdAndSeasonNumber(tvShow.getId(), season.getNumber())).thenReturn(Arrays.asList(chapter));
		
		List<ChapterRest> chapters = chapterServiceImpl.getChaptersByTvShowIdAndSeasonNumber(chapter.getId(), season.getNumber());
		
		assertNotNull(chapters);
	}
	
	@Test
	void getChapterByTvShowIdAndSeasonNumberAndChapterNumbe() throws NetflixException {
		
		when(chapterRepository.findBySeasonTvShowIdAndSeasonNumberAndNumber(tvShow.getId(), season.getNumber(), chapter.getNumber())).thenReturn(Optional.of(chapter));
		
		ChapterRest chapterRest2 = chapterServiceImpl.getChapterByTvShowIdAndSeasonNumberAndChapterNumber(tvShow.getId(), season.getNumber(), chapter.getNumber());
		
		assertNotNull(chapterRest2);
	}
	
	@Test
	void updateChapter() throws NetflixException {
		
		when(chapterRepository.findBySeasonTvShowIdAndSeasonNumberAndNumber(tvShow.getId(), season.getNumber(), chapter.getNumber())).thenReturn(Optional.of(chapter));
		when(chapterRepository.save(chapter)).thenReturn(chapter);
		
		ChapterRest chapterRest2 = chapterServiceImpl.updateChapter(tvShow.getId(), season.getNumber(), chapter.getNumber(), chapter);
		
		assertNotNull(chapterRest2);
		assertThat(chapterRest2.getId()).isEqualTo(chapterRest.getId());
		assertThat(chapterRest2.getName()).isEqualTo(chapterRest.getName());
		assertThat(chapterRest2.getNumber()).isEqualTo(chapterRest.getNumber());
		assertThat(chapterRest2.getDuration()).isEqualTo(chapterRest.getDuration());
		
	}
	
	

}
