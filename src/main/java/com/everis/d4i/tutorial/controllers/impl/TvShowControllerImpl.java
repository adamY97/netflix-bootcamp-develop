package com.everis.d4i.tutorial.controllers.impl;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.everis.d4i.tutorial.controllers.TvShowController;
import com.everis.d4i.tutorial.exceptions.NetflixException;
import com.everis.d4i.tutorial.json.ActorRestS;
import com.everis.d4i.tutorial.json.TvShowAwardRest;
import com.everis.d4i.tutorial.json.TvShowRest;
import com.everis.d4i.tutorial.responses.NetflixResponse;
import com.everis.d4i.tutorial.services.TvShowService;
import com.everis.d4i.tutorial.utils.constants.CommonConstants;
import com.everis.d4i.tutorial.utils.constants.ExceptionConstants;
import com.everis.d4i.tutorial.utils.constants.RestConstants;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(RestConstants.APPLICATION_NAME + RestConstants.API_VERSION_1 + RestConstants.RESOURCE_TV_SHOW)
public class TvShowControllerImpl implements TvShowController {

	@Autowired
	private TvShowService tvShowService;
	
	@ApiOperation(value = "Mostrar serie/s por ID categoria")
	@ApiResponses(value = {
	@ApiResponse(code = 200, message = CommonConstants.OK, response = ActorRestS.class),
	@ApiResponse(code = 404, message = ExceptionConstants.MESSAGE_INEXISTENT_ACTOR),
	@ApiResponse(code = 500, message = ExceptionConstants.INTERNAL_SERVER_ERROR)
	})

	@Override
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public NetflixResponse<List<TvShowRest>> getTvShowsByCategory(@RequestParam Long categoryId)
			throws NetflixException {
		return new NetflixResponse<>(CommonConstants.SUCCESS, String.valueOf(HttpStatus.OK), CommonConstants.OK,
				tvShowService.getTvShowsByCategory(categoryId));
	}
	
	@ApiOperation(value = "Mostrar serie/s por ID")
	@ApiResponses(value = {
	@ApiResponse(code = 200, message = CommonConstants.OK, response = ActorRestS.class),
	@ApiResponse(code = 404, message = ExceptionConstants.MESSAGE_INEXISTENT_ACTOR),
	@ApiResponse(code = 500, message = ExceptionConstants.INTERNAL_SERVER_ERROR)
	})

	@Override
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = RestConstants.RESOURCE_ID, produces = MediaType.APPLICATION_JSON_VALUE)
	public NetflixResponse<TvShowRest> getTvShowById(@PathVariable Long id) throws NetflixException {
		return new NetflixResponse<>(CommonConstants.SUCCESS, String.valueOf(HttpStatus.OK), CommonConstants.OK,
				tvShowService.getTvShowById(id));
	}
	
	@ApiOperation(value = "Mostrar premios por ID serie")
	@ApiResponses(value = {
	@ApiResponse(code = 200, message = CommonConstants.OK, response = ActorRestS.class),
	@ApiResponse(code = 404, message = ExceptionConstants.MESSAGE_INEXISTENT_ACTOR),
	@ApiResponse(code = 500, message = ExceptionConstants.INTERNAL_SERVER_ERROR)
	})
	
	@Override
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = RestConstants.RESOURCE_AWARD, produces = MediaType.APPLICATION_JSON_VALUE)
	public NetflixResponse<TvShowAwardRest> getTvShowAwards(@PathVariable Long id) throws NetflixException {
		return new NetflixResponse<>(CommonConstants.SUCCESS, String.valueOf(HttpStatus.OK), CommonConstants.OK,
				tvShowService.getTvShowAwards(id));
	}
	
	//METODO POST
	
	@ApiOperation(value = "Crear una nueva serie")
	@ApiResponses(value = {
	@ApiResponse(code = 201, message = CommonConstants.CREATED, response = TvShowRest.class),
	@ApiResponse(code = 400, message = ExceptionConstants.BAD_REQUEST),
	@ApiResponse(code = 401, message = ExceptionConstants.UNAUTHORIZED),
	@ApiResponse(code = 500, message = ExceptionConstants.INTERNAL_SERVER_ERROR)
	})
	
	
	@Override
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = RestConstants.RESOURCE_CREATE, produces = MediaType.APPLICATION_JSON_VALUE)
	public NetflixResponse<TvShowRest> createTvShow(
			@ApiParam(value = RestConstants.PARAMETER_TV_SHOW, required = true) @RequestBody @Valid final TvShowRest tvShowRest)
			throws NetflixException {
		return new NetflixResponse<>(CommonConstants.SUCCESS, String.valueOf(HttpStatus.CREATED), CommonConstants.OK,
				tvShowService.createTvShows(tvShowRest));
	}

	//METODO PATCH
	
	@ApiOperation(value = "Editar una serie")
	@ApiResponses(value = {
	@ApiResponse(code = 200, message = CommonConstants.OK, response = TvShowRest.class),
	@ApiResponse(code = 400, message = ExceptionConstants.BAD_REQUEST),
	@ApiResponse(code = 401, message = ExceptionConstants.UNAUTHORIZED),
	@ApiResponse(code = 500, message = ExceptionConstants.INTERNAL_SERVER_ERROR)
	})
	
	@Override
	@ResponseStatus(HttpStatus.OK)
	@PatchMapping(value = RestConstants.RESOURCE_ID + RestConstants.RESOURCE_UPDATE, produces = MediaType.APPLICATION_JSON_VALUE)
	public NetflixResponse<TvShowRest> updateTvShow(
			@PathVariable Long id, @RequestBody @Valid final TvShowRest tvShowRest)
			throws NetflixException{
		return new NetflixResponse<>(CommonConstants.SUCCESS, String.valueOf(HttpStatus.NO_CONTENT), CommonConstants.OK,
				tvShowService.updateTvShow(id , tvShowRest));
	}
	
	//METODO DELETE
	
	@ApiOperation(value = "Borrar una serie")
	@ApiResponses(value = {
	@ApiResponse(code = 204, message = CommonConstants.NO_CONTENT, response = TvShowRest.class),
	@ApiResponse(code = 400, message = ExceptionConstants.BAD_REQUEST),
	@ApiResponse(code = 401, message = ExceptionConstants.UNAUTHORIZED),
	@ApiResponse(code = 500, message = ExceptionConstants.INTERNAL_SERVER_ERROR)
	})
	
	@Override
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping(value = RestConstants.RESOURCE_ID + RestConstants.RESOURCE_DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public NetflixResponse<TvShowRest> deleteTvShow(
			@PathVariable Long id)
			throws NetflixException{
		return new NetflixResponse<>(CommonConstants.SUCCESS, String.valueOf(HttpStatus.NO_CONTENT), CommonConstants.OK,
				tvShowService.deleteTvShow(id));
	}
	
}
