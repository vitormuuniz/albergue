package br.com.albergue.tests.post;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.albergue.controller.dto.RoomDto;
import br.com.albergue.domain.Room;
import br.com.albergue.repository.RoomRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestPropertySource(locations="classpath:test.properties")
public class RoomPostAndDeleteTests {
	
	@Autowired
	private RoomRepository roomRepository;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper;

	@Value("${forum.jwt.expiration}") // @value serve para pegar a propriedade do application.properties
	private String expiration;

	@Value("${forum.jwt.secret}")
	private String secret;

	private URI uri;
	private HttpHeaders headers = new HttpHeaders();
	private String token;
	private Room room = new Room(5, 230.0);

	@Before
	public void init() throws URISyntaxException {
		uri = new URI("/api/rooms");

		//generating token to autentication
		Date hoje = new Date();
		Date dataExpiracao = new Date(hoje.getTime() + Long.parseLong(expiration));
		token = Jwts.builder()
				.setIssuer("API do Albergue") // quem fez a geração do token
				.setSubject(Long.toString(1L)) // usuario a quem esse token pertence
				.setIssuedAt(hoje) // data de geração
				.setExpiration(dataExpiracao) // data de expiração
				.signWith(SignatureAlgorithm.HS256, secret) // usar a senha do application.properties / algoritmo de
															// criptografia
				.compact();
		
		//seting header to put on post and delete request parameters
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer " + token);
	}


	@Test
	public void shouldAutenticateAndDeleteOneRoomWithId2() throws Exception {
		roomRepository.save(room);
		
		mockMvc.perform(delete("/api/rooms/1")
			.headers(headers))
            .andExpect(status().isOk())
            .andReturn();
	}
	
	@Test
	public void shouldAutenticateAndCreateOneRoomAndReturnStatusCreated() throws Exception {

		MvcResult result = mockMvc.perform(post(uri)
						.headers(headers)
						.content(objectMapper.writeValueAsString(room)))
						.andDo(print())
						.andExpect(status().isCreated())
						.andReturn();
		
		String contentAsString = result.getResponse().getContentAsString();

		RoomDto customerObjResponse = objectMapper.readValue(contentAsString, RoomDto.class);

		assertEquals(customerObjResponse.getNumber(), 5);
		assertEquals(customerObjResponse.getDimension(), 230, 0);
	}
}