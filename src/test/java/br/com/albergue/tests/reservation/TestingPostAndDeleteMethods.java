package br.com.albergue.tests.reservation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.albergue.controller.dto.ReservationDto;
import br.com.albergue.domain.Customer;
import br.com.albergue.domain.Reservation;
import br.com.albergue.repository.CustomerRepository;
import br.com.albergue.repository.ReservationRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class TestingPostAndDeleteMethods {

	@Autowired
	private TestRestTemplate restTemplate;
	
	@LocalServerPort
	private int port;

	@Autowired
	ReservationRepository reservationRepository;
	
	private URI uri;
	//private ResponseEntity<TokenDto> auth;
	private HttpHeaders headers;
	
	@Before
	public void init() throws URISyntaxException {
		uri = new URI("/api/reservations");
		
		//login
//		LoginForm login = new LoginForm();
//		login.setEmail("aluno@email.com");
//		login.setPassword("123456");
//		auth = restTemplate.postForEntity("/auth", login, TokenDto.class);
		
		//getting authorization
//		headers = new HttpHeaders();
//		headers.setContentType(MediaType.APPLICATION_JSON);
//		headers.set("Authorization", "Bearer "+ auth.getBody().getToken());
		
	}
	
	@Test
	public void testRegisterReservationMethodWithCashPayment() throws JSONException {
		JSONObject personJsonObjectReservation = new JSONObject();
		personJsonObjectReservation.put("customer_ID", 1);	
		personJsonObjectReservation.put("checkinDate", "2019-04-01");
		personJsonObjectReservation.put("checkoutDate", "2019-04-04");
		personJsonObjectReservation.put("reservationDate", "2019-04-01");
		JSONObject personJsonObjectPayments = new JSONObject();
		personJsonObjectPayments.put("type", "cash");
		personJsonObjectPayments.put("amount", 5000.0);
		personJsonObjectPayments.put("date", "2020-01-25T21:34:55");
		personJsonObjectPayments.put("amountTendered", 5000.0);
		personJsonObjectReservation.put("payments", personJsonObjectPayments);
		
		HttpEntity<String> entity = new HttpEntity<String>(personJsonObjectReservation.toString(), headers);
		ResponseEntity<ReservationDto> result = restTemplate.exchange(uri, HttpMethod.POST, entity, ReservationDto.class);

		// Verify request succeed
		assertEquals(201, result.getStatusCodeValue());
	}
	
	@Test
	public void testRegisterReservationMethodWithCheckPayment() throws JSONException {
		JSONObject personJsonObjectReservation = new JSONObject();
		personJsonObjectReservation.put("customer_ID", 1);	
		personJsonObjectReservation.put("checkinDate", "2019-04-01");
		personJsonObjectReservation.put("checkoutDate", "2019-04-04");
		personJsonObjectReservation.put("reservationDate", "2019-04-01");
		
		JSONObject personJsonObjectPayments = new JSONObject();
		personJsonObjectPayments.put("type", "check");
		personJsonObjectPayments.put("amount", 5000.0);
		personJsonObjectPayments.put("date", "2020-01-25T21:34:55");
		personJsonObjectPayments.put("bankId", "01");
		personJsonObjectPayments.put("bankName", "Banco do Brasil");
		personJsonObjectPayments.put("branchNumber", "1234-5");
		personJsonObjectReservation.put("payments", personJsonObjectPayments);
		
		HttpEntity<String> entity = new HttpEntity<String>(personJsonObjectReservation.toString(), headers);
		ResponseEntity<ReservationDto> result = restTemplate.exchange(uri, HttpMethod.POST, entity, ReservationDto.class);

		// Verify request succeed
		assertEquals(201, result.getStatusCodeValue());
	}
	
	@Test
	public void testRegisterReservationMethodWithCreditCardPayment() throws JSONException {
		JSONObject personJsonObjectReservation = new JSONObject();
		personJsonObjectReservation.put("customer_ID", 1);	
		personJsonObjectReservation.put("checkinDate", "2019-04-01");
		personJsonObjectReservation.put("checkoutDate", "2019-04-04");
		personJsonObjectReservation.put("reservationDate", "2019-04-01");
		JSONObject personJsonObjectPayments = new JSONObject();
		personJsonObjectPayments.put("type", "creditCard");
		personJsonObjectPayments.put("amount", 5000.0);
		personJsonObjectPayments.put("date", "2020-01-25T21:34:55");
		personJsonObjectPayments.put("issuer", "VISA");
		personJsonObjectPayments.put("number", "1234");
		personJsonObjectPayments.put("nameOnCard", "1234 5678 9101 1121");
		personJsonObjectPayments.put("expirationDate", "2020-05-01");
		personJsonObjectPayments.put("securityCode", "123");
		personJsonObjectReservation.put("payments", personJsonObjectPayments);
		
		HttpEntity<String> entity = new HttpEntity<String>(personJsonObjectReservation.toString(), headers);
		ResponseEntity<ReservationDto> result = restTemplate.exchange(uri, HttpMethod.POST, entity, ReservationDto.class);

		// Verify request succeed
		assertEquals(201, result.getStatusCodeValue());
	}
	
	@Test
	public void testDeleteReservationMethod() {
		
		HttpEntity<?> entity = new HttpEntity<Object>(null, headers);
		ResponseEntity<String> result = restTemplate.exchange(uri+"/1", HttpMethod.DELETE, entity, String.class);
		
		Optional<Reservation> findById = reservationRepository.findById(1L);
		
		assertEquals(findById.isPresent(), false);
		assertEquals(200, result.getStatusCodeValue());

	}
}