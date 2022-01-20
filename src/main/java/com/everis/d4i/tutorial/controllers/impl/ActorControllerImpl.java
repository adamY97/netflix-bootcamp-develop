package com.everis.d4i.tutorial.controllers.impl;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.everis.d4i.tutorial.controllers.ActorController;
import com.everis.d4i.tutorial.exceptions.NetflixException;
import com.everis.d4i.tutorial.json.ActorRest;
import com.everis.d4i.tutorial.json.ActorRestS;
import com.everis.d4i.tutorial.responses.NetflixResponse;
import com.everis.d4i.tutorial.services.ActorService;
import com.everis.d4i.tutorial.utils.constants.CommonConstants;
import com.everis.d4i.tutorial.utils.constants.ExceptionConstants;
import com.everis.d4i.tutorial.utils.constants.RestConstants;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

@RestController
@RequestMapping(RestConstants.APPLICATION_NAME + RestConstants.API_VERSION_1 + RestConstants.RESOURCE_ACTOR)
public class ActorControllerImpl implements ActorController{
	
	@Autowired @Qualifier("Spain")
	private ActorService actorService;
	
	@ApiOperation(value = "Mostrar todos los actores")
	@ApiResponses(value = {
	@ApiResponse(code = 200, message = CommonConstants.OK, response = ActorRestS.class),
	@ApiResponse(code = 404, message = ExceptionConstants.MESSAGE_INEXISTENT_ACTOR),
	@ApiResponse(code = 500, message = ExceptionConstants.INTERNAL_SERVER_ERROR)
	})

	@Override
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public NetflixResponse<List<ActorRestS>> getActors() throws NetflixException {
		return new NetflixResponse<>(CommonConstants.SUCCESS, String.valueOf(HttpStatus.OK), CommonConstants.OK,
				actorService.getActors());
	}
	
	@ApiOperation(value = "Mostrar actor por ID")
	@ApiResponses(value = {
	@ApiResponse(code = 200, message = CommonConstants.OK, response = ActorRest.class),
	@ApiResponse(code = 404, message = ExceptionConstants.MESSAGE_INEXISTENT_ACTOR),
	@ApiResponse(code = 500, message = ExceptionConstants.INTERNAL_SERVER_ERROR)
	})
	
	@Override
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = RestConstants.RESOURCE_ID, produces = MediaType.APPLICATION_JSON_VALUE)
	public NetflixResponse<ActorRest> getActorById(@PathVariable Long id) throws NetflixException {
		return new NetflixResponse<>(CommonConstants.SUCCESS, String.valueOf(HttpStatus.OK), CommonConstants.OK,
				actorService.getActorById(id));
	}
	
	@ApiOperation(value = "Crear un nuevo actor")
	@ApiResponses(value = {
	@ApiResponse(code = 201, message = CommonConstants.CREATED, response = ActorRest.class),
	@ApiResponse(code = 400, message = ExceptionConstants.BAD_REQUEST),
	@ApiResponse(code = 401, message = ExceptionConstants.UNAUTHORIZED),
	@ApiResponse(code = 500, message = ExceptionConstants.INTERNAL_SERVER_ERROR)
	})
	
	@Override
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = RestConstants.RESOURCE_CREATE, produces = MediaType.APPLICATION_JSON_VALUE)
	public NetflixResponse<ActorRest> createActor(
			@ApiParam(value = RestConstants.PARAMETER_ACTOR, required = true) @RequestBody @Valid final ActorRest actorRest)
			throws NetflixException {
		return new NetflixResponse<>(CommonConstants.SUCCESS, String.valueOf(HttpStatus.CREATED), CommonConstants.OK,
				actorService.createActor(actorRest));
	}
	
	@ApiOperation(value = "Editar un actor")
	@ApiResponses(value = {
	@ApiResponse(code = 200, message = CommonConstants.OK, response = ActorRest.class),
	@ApiResponse(code = 400, message = ExceptionConstants.BAD_REQUEST),
	@ApiResponse(code = 401, message = ExceptionConstants.UNAUTHORIZED),
	@ApiResponse(code = 500, message = ExceptionConstants.INTERNAL_SERVER_ERROR)
	})

	@Override
	@ResponseStatus(HttpStatus.OK)
	@PatchMapping(value = RestConstants.RESOURCE_ID + RestConstants.RESOURCE_UPDATE, produces = MediaType.APPLICATION_JSON_VALUE)
	public NetflixResponse<ActorRest> updateActor(
			@PathVariable Long id, @RequestBody @Valid final ActorRest actorRest)
			throws NetflixException{
		return new NetflixResponse<>(CommonConstants.SUCCESS, String.valueOf(HttpStatus.OK), CommonConstants.OK,
				actorService.updateActor(id , actorRest));
	}
	
	@ApiOperation(value = "Borrar un actor")
	@ApiResponses(value = {
	@ApiResponse(code = 204, message = CommonConstants.NO_CONTENT, response = ActorRest.class),
	@ApiResponse(code = 400, message = ExceptionConstants.BAD_REQUEST),
	@ApiResponse(code = 401, message = ExceptionConstants.UNAUTHORIZED),
	@ApiResponse(code = 500, message = ExceptionConstants.INTERNAL_SERVER_ERROR)
	})
	
	@Override
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping(value = RestConstants.RESOURCE_ID + RestConstants.RESOURCE_DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public NetflixResponse<ActorRest> deleteActor(
			@PathVariable Long id)
			throws NetflixException{
		return new NetflixResponse<>(CommonConstants.SUCCESS, String.valueOf(HttpStatus.NO_CONTENT), CommonConstants.OK,
				actorService.deleteActor(id));
	}

}
