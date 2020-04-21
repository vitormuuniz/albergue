package br.com.albergue.tests.reservation;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import br.com.albergue.controller.dto.TokenDto;
import br.com.albergue.controller.form.LoginForm;
import br.com.albergue.domain.Room;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class TestingPostMethods {

	@Autowired
	private TestRestTemplate restTemplate;
	
	@LocalServerPort
	int port = 8080;
	
	private String baseUrl = "http://localhost:" + port;
	private URI uri;
	private ResponseEntity<TokenDto> auth;
	private HttpHeaders headers;
	
	@Before
	public void init() throws URISyntaxException {
		uri = new URI(baseUrl + "/api/rooms");

		//login
		LoginForm login = new LoginForm();
		login.setEmail("aluno@email.com");
		login.setPassword("123456");
		auth = restTemplate.postForEntity(baseUrl + "/auth", login, TokenDto.class);
		
		//getting authorization
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer "+ auth.getBody().getToken());
	}
	
	@Test
	public void testRegisterRoomMethod() {

		Room room = new Room();
		room.setNumber(1);
		room.setDimension(230);

		HttpEntity<?> entity = new HttpEntity<Object>(room, headers);
		
		ResponseEntity<String> result = restTemplate.postForEntity(uri, entity, String.class);
//
//		System.out.println(result.getBody());
		// Verify request succeed
		Assert.assertEquals(200, auth.getStatusCodeValue());
		Assert.assertEquals(201, result.getStatusCodeValue());
	}
}