package br.com.albergue.tests;

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

import br.com.albergue.domain.Room;
import br.com.albergue.repository.RoomRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class TestingGetMethods {

	@Autowired
	private TestRestTemplate restTemplate;
	
	@LocalServerPort
	int port;
	
	@Autowired
	private RoomRepository roomRepository;
	
//	@Autowired
//	RestTemplate restTemplate;


//	@TestConfiguration
//	static class Config {
//		@Bean
//		public RestTemplateBuilder restTemplateBuilder() {
//			return new RestTemplateBuilder().basicAuthentication("root", "root");
//		}
//	}

//	@Test
//	public void testAccessDenied() {
//		System.out.println(port);
//		restTemplate = restTemplate.withBasicAuth("teste", "teste");
//		ResponseEntity<String> response = restTemplate.getForEntity("/auth", String.class);
//		Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(403);
//	}

	@Test
	public void testListAllRoomsMethodWithoutParam() throws URISyntaxException {

		final String baseUrl = "http://localhost:" + port + "/api/rooms";
		URI uri = new URI(baseUrl);

		Room room = new Room(1, 230.0);
		roomRepository.save(room);
//		ResponseEntity<String> result = restTemplate.postForEntity(uri, room, String.class);
		ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);

		System.out.println(result.getBody());
		// Verify request succeed
		Assert.assertEquals(200, result.getStatusCodeValue());
	}
	
	@Test
	public void testListAllCustomersMethodWithoutParam() throws URISyntaxException {

		final String baseUrl = "http://localhost:" + port + "/api/customers";
		URI uri = new URI(baseUrl);
		ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);

		System.out.println(result.getBody());
		// Verify request succeed
		Assert.assertEquals(200, result.getStatusCodeValue());
		Assert.assertTrue(result.getBody().contains("name"));

	}
	
	@Test
	public void testListAllCustomersMethodWithParam() throws URISyntaxException {
		
		final String baseUrl = "http://localhost:" + port + "/api/customers?name=Aluno";
		URI uri = new URI(baseUrl);
		ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
		
		System.out.println(result.getBody());
		// Verify request succeed
		Assert.assertEquals(200, result.getStatusCodeValue());
		Assert.assertTrue(result.getBody().contains("name"));
	}
	
	@Test
	public void testListAllCustomersMethodWithWrongParam() throws URISyntaxException {

		final String baseUrl = "http://localhost:" + port + "/api/customers?name=Aluno222";
		URI uri = new URI(baseUrl);
		ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);

		System.out.println(result.getBody());
		// Verify request succeed
		Assert.assertEquals(200, result.getStatusCodeValue());
		Assert.assertFalse(result.getBody().contains("name"));
	}

}