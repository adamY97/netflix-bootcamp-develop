package com.everis.d4i.tutorial.services.impl;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.time.Year;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;


import com.everis.d4i.tutorial.entities.Category;
import com.everis.d4i.tutorial.entities.TvShow;
import com.everis.d4i.tutorial.exceptions.NetflixException;
import com.everis.d4i.tutorial.json.CategoryRest;
import com.everis.d4i.tutorial.repositories.CategoryRepository;

class CategoryServiceImplTest {
	
	@Mock
	private CategoryRepository categoryRepository;
	
	@InjectMocks
	private CategoryServiceImpl categoryServiceImpl;

	private ModelMapper modelMapper = new ModelMapper();
	
	private Category category;
	private TvShow tvShow;
	private CategoryRest categoryRest;

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
		tvShow.setAdvertising("advertencia");
		
		
		category = new Category();
		category.setId((long) 2);
		category.setName("categoria 1");
		category.setTvShows(Arrays.asList(tvShow));
		
		categoryRest = modelMapper.map(category, CategoryRest.class);
	}

	@Test
	void getCategories() throws NetflixException {
		
		when(categoryRepository.findAll()).thenReturn(Arrays.asList(category));
		
		List<CategoryRest> categories = categoryServiceImpl.getCategories();
		
		assertNotNull(categories);
	}
	
	@Test
	void createCategories() throws NetflixException {
		
		when(categoryRepository.save(Mockito.any(Category.class))).thenReturn(category);
		
		CategoryRest categoryRest2 = categoryServiceImpl.createCategories(categoryRest);
		
		assertNotNull(categoryRest2);
		assertThat(categoryRest2.getId()).isEqualTo(categoryRest.getId());
		assertThat(categoryRest2.getName()).isEqualTo(categoryRest.getName());

	}

}
