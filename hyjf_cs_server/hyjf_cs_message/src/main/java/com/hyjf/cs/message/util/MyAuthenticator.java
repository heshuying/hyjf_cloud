package com.hyjf.cs.message.util;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * <p>
 * MyAuthenticator
 * </p>
 *
 * @author gogtz
 * @version 1.0.0
 */
public class MyAuthenticator extends Authenticator {
	private String username;
	private String password;

	public MyAuthenticator(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(username, password);
	}

}
