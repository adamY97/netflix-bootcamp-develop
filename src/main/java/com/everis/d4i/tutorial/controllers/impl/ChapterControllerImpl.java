package com.everis.d4i.tutorial.controllers.impl;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.everis.d4i.tutorial.controllers.ChapterController;
import com.everis.d4i.tutorial.entities.Chapter;
import com.everis.d4i.tutorial.exceptions.NetflixException;
import com.everis.d4i.tutorial.json.ChapterRest;
import com.everis.d4i.tutorial.responses.NetflixResponse;
import com.everis.d4i.tutorial.services.ChapterService;
import com.everis.d4i.tutorial.utils.constants.CommonConstants;
import com.everis.d4i.tutorial.utils.constants.ExceptionConstants;
import com.everis.d4i.tutorial.utils.constants.RestConstants;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(RestConstants.APPLICATION_NAME + RestConstants.API_VERSION_1 + RestConstants.RESOURCE_CHAPTER)
public class ChapterControllerImpl implements ChapterController {

	@Autowired
	private ChapterService chapterService;
	
	@ApiOperation(value = "Mostrar capitulo/s por numero de serie y numero de temporada")
	@ApiResponses(value = {
	@ApiResponse(code = 200, message = CommonConstants.OK, response = ChapterRest.class),
	@ApiResponse(code = 404, message = ExceptionConstants.MESSAGE_INEXISTENT_CHAPTER),
	@ApiResponse(code = 500, message = ExceptionConstants.INTERNAL_SERVER_ERROR)
	})

	@Override
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public NetflixResponse<List<ChapterRest>> getChaptersByTvShowIdAndSeasonNumber(@PathVariable Long tvShowId,
			@PathVariable short seasonNumber) throws NetflixException {
		return new NetflixResponse<>(CommonConstants.SUCCESS, String.valueOf(HttpStatus.OK), CommonConstants.OK,
				chapterService.getChaptersByTvShowIdAndSeasonNumber(tvShowId, seasonNumber));
	}
	
	@ApiOperation(value = "Mostrar capitulo numero de serie, numero de temporada e ID")
	@ApiResponses(value = {
	@ApiResponse(code = 200, message = CommonConstants.OK, response = ChapterRest.class),
	@ApiResponse(code = 404, message = ExceptionConstants.MESSAGE_INEXISTENT_CHAPTER),
	@ApiResponse(code = 500, message = ExceptionConstants.INTERNAL_SERVER_ERROR)
	})

	@Override
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = RestConstants.RESOURCE_NUMBER, produces = MediaType.APPLICATION_JSON_VALUE)
	public NetflixResponse<ChapterRest> getChapterByTvShowIdAndSeasonNumberAndChapterNumber(@PathVariable Long tvShowId,
			@PathVariable short seasonNumber, @PathVariable short number) throws NetflixException {
		return new NetflixResponse<>(CommonConstants.SUCCESS, String.valueOf(HttpStatus.OK), CommonConstants.OK,
				chapterService.getChapterByTvShowIdAndSeasonNumberAndChapterNumber(tvShowId, seasonNumber, number));
	}
	
	//METODO PATCH
	
	@ApiOperation(value = "Editar un capitulo")
	@ApiResponses(value = {
	@ApiResponse(code = 200, message = CommonConstants.OK, response = ChapterRest.class),
	@ApiResponse(code = 400, message = ExceptionConstants.BAD_REQUEST),
	@ApiResponse(code = 401, message = ExceptionConstants.UNAUTHORIZED),
	@ApiResponse(code = 500, message = ExceptionConstants.INTERNAL_SERVER_ERROR)
	})
	
	@Override
	@ResponseStatus(HttpStatus.OK)
	@PatchMapping(value = RestConstants.RESOURCE_NUMBER + RestConstants.RESOURCE_UPDATE, produces = MediaType.APPLICATION_JSON_VALUE)
	public NetflixResponse<ChapterRest> updateChapter(
			@PathVariable Long tvShowId, @PathVariable short seasonNumber, 
			@PathVariable short number, @RequestBody @Valid final Chapter chapterRest)
			throws NetflixException{
		return new NetflixResponse<>(CommonConstants.SUCCESS, String.valueOf(HttpStatus.OK), CommonConstants.OK,
				chapterService.updateChapter(tvShowId ,seasonNumber, number, chapterRest));
	}

}
