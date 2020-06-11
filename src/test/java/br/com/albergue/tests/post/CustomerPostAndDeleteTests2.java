package br.com.albergue.tests.post;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
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
import br.com.albergue.domain.Address;
import br.com.albergue.domain.Customer;
import br.com.albergue.domain.Room;
import br.com.albergue.repository.AddressRepository;
import br.com.albergue.repository.CustomerRepository;
import br.com.albergue.repository.RoomRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class CustomerPostAndDeleteTests2 {

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
	private Address address = new Address();
	private Customer customer2 = new Customer();

	@Before
	public void init() throws URISyntaxException {
		uri = new URI("/api/customers");
		
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
	public void test() throws Exception {
		// setting address to put into the customer paramseters
		address.setAddressName("rua tal");
		address.setCity("alguma");
		address.setCountry("algum");
		address.setState("XXX");
		address.setZipCode("xxxx-xxx");
		
		// setting customer
		customer2.setAddress(address);
		customer2.setBirthday(LocalDate.of(1900, 12, 12));
		customer2.setEmail("washington@orkut.com");
		customer2.setName("Washington");
		customer2.setLastName("Ferrolho");
		customer2.setTitle("MRS.");
		customer2.setPassword("1234567");
		
		MvcResult result = mockMvc.perform(post(uri)
						.headers(headers)
						.content(objectMapper.writeValueAsString(customer2)))
						.andDo(print())
						.andExpect(status().isCreated())
						.andReturn();
		
		assertTrue(result.getResponse().getContentAsString().contains("\"name\":\"Washington\""));
	}

//	@Test
//	public void testDeleteCustomerMethod() throws Exception {
//			mockMvc.perform(delete("/api/rooms/1")
//				.headers(headers))
//	            .andExpect(status().isOk())
//	            .andReturn();
//	}
}