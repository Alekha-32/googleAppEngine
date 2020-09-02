package org.alekha.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.client.auth.openidconnect.IdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

@RestController
public class LoginController {
	private static final String CLIENT_ID = "48162481753-b6noo6mps928frga21dfd841kvo1dhgg.apps.googleusercontent.com";

	private static final HttpTransport TRANSPORT = new NetHttpTransport();
	private static final JacksonFactory JSON_FACTORY = new JacksonFactory();

	@PostMapping(value = "/googleSignIn", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<String, Object> googleSignIn(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		HttpSession session = request.getSession(false);

		HashMap<String, Object> map = new HashMap<String, Object>();
		GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(TRANSPORT, JSON_FACTORY)
				.setAudience(Collections.singletonList(CLIENT_ID))

				.build();
		System.out.println("test1");

		String idTokenString = request.getParameter("googleToken");

		GoogleIdToken idToken = null;
		try {
			idToken = verifier.verify(idTokenString);
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}
		if (idToken != null) {
			Payload payload = idToken.getPayload();
			String userId = payload.getSubject();
			System.out.println("User ID: " + userId);
			String email = (String) payload.get("email");
			boolean emailVerified = Boolean.valueOf(
					((com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload) payload).getEmailVerified());
			String name = (String) payload.get("name");
			if (name == null) {
				map.put("value", "false");
			} else {
				session = request.getSession(true);

				session.setAttribute("email", email);
				session.setAttribute("name", name);
				map.put("value", "true");
			}

		} else {

			map.put("value", "false");
			System.out.println("Invalid ID token.");
		}

		return map;

	}


	public int getLucky() {
		return 7;
	}

}
