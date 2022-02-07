package com.everis.d4i.tutorial.controllers;

import javax.validation.Valid;

import com.everis.d4i.tutorial.json.UserLoginRest;

public interface JwtController {

	String generaToken(@Valid UserLoginRest userLoginRest);

	
}
