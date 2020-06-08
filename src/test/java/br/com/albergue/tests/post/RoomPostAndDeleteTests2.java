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
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.albergue.controller.AutenticationController;
import br.com.albergue.controller.dto.TokenDto;
import br.com.albergue.domain.Customer;
import br.com.albergue.domain.Room;
import br.com.albergue.repository.RoomRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class RoomPostAndDeleteTests2 {

	@MockBean
	RoomRepository roomRepository;
	
	@MockBean
	private AutenticationController autentication;

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
	private Customer customer = Mockito.mock(Customer.class);
	private String token;
	private Room room = new Room(1, 230.0);

	@Before
	public void init() throws URISyntaxException {
		uri = new URI("/api/rooms");

		//mocking getid to generate token
		Mockito.when(customer.getId()).thenReturn(1L);
		
		//generating token to autentication
		Date hoje = new Date();
		Date dataExpiracao = new Date(hoje.getTime() + Long.parseLong(expiration));
		token = Jwts.builder()
				.setIssuer("API do Albergue") // quem fez a geração do token
				.setSubject(customer.getId().toString()) // usuario a quem esse token pertence
				.setIssuedAt(hoje) // data de geração
				.setExpiration(dataExpiracao) // data de expiração
				.signWith(SignatureAlgorithm.HS256, secret) // usar a senha do application.properties / algoritmo de
															// criptografia
				.compact();
		
		//mocking autenticate return
		Mockito.when(autentication.autenticate(Mockito.any()))
				.thenReturn(ResponseEntity.ok(new TokenDto(token, "Bearer")));
		
		//seting header to put on post and delete request parameters
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer " + token);
	}

	@Test
	public void test() throws Exception {
		Mockito.when(roomRepository.save(Mockito.any())).thenReturn(room);

		MvcResult result = mockMvc.perform(post(uri)
						.headers(headers)
						.content(objectMapper.writeValueAsString(room)))
						.andDo(print())
						.andExpect(status().isCreated())
						.andReturn();
		
		assertTrue(result.getResponse().getContentAsString().contains("\"number\":1"));
	}

//	@Test
//	public void testDeleteCustomerMethod() throws Exception {
//			mockMvc.perform(delete("/api/rooms/1")
//				.headers(headers))
//	            .andExpect(status().isOk())
//	            .andReturn();
//	}
}