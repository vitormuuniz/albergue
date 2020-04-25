package br.com.albergue.tests.room;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.albergue.domain.Room;
import br.com.albergue.repository.RoomRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestingGetMethods {

	@Autowired
	private TestRestTemplate restTemplate;
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private RoomRepository roomRepository;
	
	private URI uri;
	private Room room;
	
	@Before
	public void init() throws URISyntaxException {

		uri = new URI("/api/rooms");

		room = new Room(13, 230.0);
		roomRepository.save(room);
	}
	
	@Test
	public void testListAllRoomsMethodWithoutParam() throws URISyntaxException {

		ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);

		// Verify request succeed
		Assert.assertEquals(200, result.getStatusCodeValue());
		Assert.assertTrue(result.getBody().contains("\"number\":13"));

	}
	
	@Test
	public void testListAllRoomsMethodByParam() throws URISyntaxException {
		
		ResponseEntity<String> result = restTemplate.getForEntity(uri + "?dimension=230", String.class);

		// Verify request succeed
		Assert.assertEquals(200, result.getStatusCodeValue());
		Assert.assertTrue(result.getBody().contains("\"number\":13"));
	}
	
	@Test
	public void testListAllRoomsMethodById() throws URISyntaxException {
		
		ResponseEntity<String> result = restTemplate.getForEntity(uri + "/1", String.class);

		// Verify request succeed
		Assert.assertEquals(200, result.getStatusCodeValue());
		Assert.assertTrue(result.getBody().contains("\"number\":13"));
	}
	
	@Test
	public void testListAllCustomersMethodByWrongParam() throws URISyntaxException {

		ResponseEntity<String> result = restTemplate.getForEntity(uri + "?number=1333", String.class);

		// Verify request succeed
		Assert.assertEquals(200, result.getStatusCodeValue());
		Assert.assertFalse(result.getBody().contains("\"number\":1333"));
	}
}