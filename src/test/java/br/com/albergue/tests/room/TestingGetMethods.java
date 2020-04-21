package br.com.albergue.tests.room;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.albergue.controller.dto.RoomDto;
import br.com.albergue.domain.Room;
import br.com.albergue.repository.RoomRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class TestingGetMethods {

	@Autowired
	private TestRestTemplate restTemplate;
	
	@LocalServerPort
	int port = 8080;
	
	@Autowired
	private RoomRepository roomRepository;
	
	private final String baseUrl = "http://localhost:" + port;

	@Test
	public void testListAllRoomsMethodWithoutParam() throws URISyntaxException {

		URI uri = new URI(baseUrl + "/api/rooms");

		Room room = new Room(13, 230.0);
		roomRepository.save(room);
		ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);

		System.out.println(result.getBody());
		
		// Verify request succeed
		Assert.assertEquals(200, result.getStatusCodeValue());
		Assert.assertTrue(result.getBody().contains("\"number\":13"));

	}
	
	@Test
	public void testListAllRoomsMethodWithParam() throws URISyntaxException {

		URI uri = new URI(baseUrl + "/api/rooms?number=13");

		Room room = new Room(13, 230.0);
		roomRepository.save(room);
		
		ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);

		// Verify request succeed
		Assert.assertEquals(200, result.getStatusCodeValue());
		Assert.assertTrue(result.getBody().contains("\"number\":13"));
	}
}