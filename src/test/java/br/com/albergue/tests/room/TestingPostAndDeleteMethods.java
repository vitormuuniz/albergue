package br.com.albergue.tests.room;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.albergue.controller.dto.RoomDto;
import br.com.albergue.domain.Room;
import br.com.albergue.repository.RoomRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.JVM)
public class TestingPostAndDeleteMethods {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private RoomRepository roomRepository;

	@LocalServerPort
	private int port;

	private URI uri;
	//private ResponseEntity<TokenDto> auth;
	private HttpHeaders headers;

	@Before
	public void init() throws URISyntaxException {
		uri = new URI("/api/rooms");

		// login
//		LoginForm login = new LoginForm();
//		login.setEmail("aluno@email.com");
//		login.setPassword("123456");
//		auth = restTemplate.postForEntity("/auth", login, TokenDto.class);

		// getting authorization
//		headers = new HttpHeaders();
//		headers.setContentType(MediaType.APPLICATION_JSON);
//		headers.set("Authorization", "Bearer " + auth.getBody().getToken());
	}

	@Test
	public void testRegisterRoomMethod() {

		Room room = new Room();
		room.setNumber(13);
		room.setDimension(230);

		HttpEntity<?> entity = new HttpEntity<Object>(room, headers);

		ResponseEntity<RoomDto> result = restTemplate.exchange(uri, HttpMethod.POST, entity, RoomDto.class);

		// Verify request succeed
		assertEquals(201, result.getStatusCodeValue());
		assertEquals(13, result.getBody().getNumber());

	}

	@Test
	public void testDeleteCustomerMethod() {

		HttpEntity<?> entity = new HttpEntity<Object>(null, headers);
		ResponseEntity<String> result = restTemplate.exchange(uri + "/1", HttpMethod.DELETE, entity, String.class);

		Optional<Room> findById = roomRepository.findById(1L);

		assertEquals(findById.isPresent(), false);
		assertEquals(200, result.getStatusCodeValue());

	}
}