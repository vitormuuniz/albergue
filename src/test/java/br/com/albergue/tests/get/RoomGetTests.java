package br.com.albergue.tests.get;

import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.albergue.controller.dto.RoomDto;
import br.com.albergue.domain.Room;
import br.com.albergue.repository.RoomRepository;
import org.json.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RoomGetTests {

	@Autowired
	private TestRestTemplate restTemplate;
	
	@LocalServerPort
	private int port;
	
	@MockBean
	private RoomRepository roomRepository;
	
	@Autowired
	ObjectMapper objectMapper;
	
	private URI uri;
	private Room room;	
	private List<Room> roomList = new ArrayList<>();

	
	@Before
	public void init() throws URISyntaxException {

		uri = new URI("/api/rooms");

		room = new Room(13, 230.0);
		
		roomList.add(room);
	}
	
	@Test
	public void shouldReturnAllRoomsAndStatusOkWithoutParam() throws JSONException, JsonMappingException, JsonProcessingException {
		Room room2 = new Room(14, 250.0);
		roomList.add(room2);
		
		Mockito.when(roomRepository.findAll()).thenReturn(roomList);

		ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
		
		String contentAsString = result.getBody();

		RoomDto[] customerObjResponse = objectMapper.readValue(contentAsString, RoomDto[].class);
		
		/// Verify request succeed
		assertEquals(customerObjResponse.length, 2);
		assertEquals(customerObjResponse[0].getNumber(), 13);
		assertEquals(customerObjResponse[0].getDimension(), 230, 0);
		assertEquals(200, result.getStatusCodeValue());

	}
	
	@Test
	public void shouldReturnOneRoomAndStatusOkByParam() throws URISyntaxException, JsonMappingException, JsonProcessingException {
		
		Mockito.when(roomRepository.findByNumber(13)).thenReturn(roomList);

		ResponseEntity<String> result = restTemplate.getForEntity(uri + "?number=13", String.class);

		String contentAsString = result.getBody();

		RoomDto[] customerObjResponse = objectMapper.readValue(contentAsString, RoomDto[].class);
		
		/// Verify request succeed
		assertEquals(customerObjResponse[0].getNumber(), 13);
		assertEquals(customerObjResponse[0].getDimension(), 230, 0);
		assertEquals(200, result.getStatusCodeValue());
	}
	
	@Test
	public void shouldReturnOneRoomAndStatusOkById() throws URISyntaxException, JsonMappingException, JsonProcessingException {
		
		Mockito.when(roomRepository.findById(1L)).thenReturn(Optional.of(room));

		ResponseEntity<String> result = restTemplate.getForEntity(uri + "/1", String.class);

		String contentAsString = result.getBody();

		RoomDto customerObjResponse = objectMapper.readValue(contentAsString, RoomDto.class);
		
		/// Verify request succeed
		assertEquals(customerObjResponse.getNumber(), 13);
		assertEquals(customerObjResponse.getDimension(), 230, 0);
		assertEquals(200, result.getStatusCodeValue());
	}
	
	@Test
	public void shouldReturnNotFoundStatusAndNullBodyByWrongParam() throws URISyntaxException {
		
		Mockito.when(roomRepository.findByNumber(13)).thenReturn(roomList);

		ResponseEntity<String> result = restTemplate.getForEntity(uri + "?number=333", String.class);

		// Verify request succeed
		Assert.assertEquals(404, result.getStatusCodeValue());
		Assert.assertEquals(result.getBody(), null);
	}
}