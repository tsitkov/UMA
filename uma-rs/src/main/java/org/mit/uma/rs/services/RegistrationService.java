package org.mit.uma.rs.services;

import org.mit.uma.rs.ASConfiguration;

import org.mit.uma.rs.RegistrationResponse;


public interface RegistrationService {
	
	public RegistrationResponse performClientRegistration(ASConfiguration config);
}
