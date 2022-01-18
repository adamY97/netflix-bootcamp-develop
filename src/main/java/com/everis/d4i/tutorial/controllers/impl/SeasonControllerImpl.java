package com.everis.d4i.tutorial.controllers.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.everis.d4i.tutorial.controllers.SeasonController;
import com.everis.d4i.tutorial.exceptions.NetflixException;
import com.everis.d4i.tutorial.json.SeasonRest;
import com.everis.d4i.tutorial.responses.NetflixResponse;
import com.everis.d4i.tutorial.services.SeasonService;
import com.everis.d4i.tutorial.utils.constants.CommonConstants;
import com.everis.d4i.tutorial.utils.constants.ExceptionConstants;
import com.everis.d4i.tutorial.utils.constants.RestConstants;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(RestConstants.APPLICATION_NAME + RestConstants.API_VERSION_1 + RestConstants.RESOURCE_SEASON)
public class SeasonControllerImpl implements SeasonController {

	@Autowired
	private SeasonService seasonService;
	
	@ApiOperation(value = "Mostrar temporada/s por ID serie")
	@ApiResponses(value = {
	@ApiResponse(code = 200, message = CommonConstants.OK, response = SeasonRest.class),
	@ApiResponse(code = 404, message = ExceptionConstants.MESSAGE_INEXISTENT_SEASON),
	@ApiResponse(code = 500, message = ExceptionConstants.INTERNAL_SERVER_ERROR)
	})

	@Override
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public NetflixResponse<List<SeasonRest>> getSeasonsByTvShow(Long tvShowId) throws NetflixException {
		return new NetflixResponse<>(CommonConstants.SUCCESS, String.valueOf(HttpStatus.OK), CommonConstants.OK,
				seasonService.getSeasonsByTvShow(tvShowId));
	}
	
	@ApiOperation(value = "Mostrar temporada/s por ID serie y numero de temporada")
	@ApiResponses(value = {
	@ApiResponse(code = 200, message = CommonConstants.OK, response = SeasonRest.class),
	@ApiResponse(code = 404, message = ExceptionConstants.MESSAGE_INEXISTENT_SEASON),
	@ApiResponse(code = 500, message = ExceptionConstants.INTERNAL_SERVER_ERROR)
	})

	@Override
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = RestConstants.RESOURCE_NUMBER, produces = MediaType.APPLICATION_JSON_VALUE)
	public NetflixResponse<SeasonRest> getSeasonByTvShowIdAndSeasonNumber(@PathVariable Long tvShowId,
			@PathVariable short number) throws NetflixException {
		return new NetflixResponse<>(CommonConstants.SUCCESS, String.valueOf(HttpStatus.OK), CommonConstants.OK,
				seasonService.getSeasonByTvShowIdAndSeasonNumber(tvShowId, number));
	}

}
