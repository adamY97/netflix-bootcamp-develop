package com.everis.d4i.tutorial.controllers.impl;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.everis.d4i.tutorial.controllers.JwtController;
import com.everis.d4i.tutorial.json.UserLoginRest;
import com.everis.d4i.tutorial.services.JwtService;
import com.everis.d4i.tutorial.utils.constants.RestConstants;

import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(RestConstants.APPLICATION_NAME + RestConstants.API_VERSION_1)
public class JwtControllerImpl implements JwtController{
	
	@Autowired
	private JwtService jwtService;

	@Override
	@PostMapping("/token")
	public String generaToken(@ApiParam(value = RestConstants.PARAMETER_USER, required = true) @RequestBody @Valid final UserLoginRest userLoginRest) {
		
		return jwtService.createToken(userLoginRest);
	}
}
