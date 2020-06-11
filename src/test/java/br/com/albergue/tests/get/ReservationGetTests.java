package br.com.albergue.tests.get;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONException;
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

import br.com.albergue.domain.CashPayment;
import br.com.albergue.domain.Customer;
import br.com.albergue.domain.Reservation;
import br.com.albergue.repository.CustomerRepository;
import br.com.albergue.repository.ReservationRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReservationGetTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;
	
	@MockBean
	private CustomerRepository customerRepository;

	@MockBean
	private ReservationRepository reservationRepository;

	private URI uri;
	private Reservation reservation = new Reservation();
	private CashPayment cash = new CashPayment();
	private List<Reservation> reservationsList = new ArrayList<>();
	
	private Customer customer = Mockito.mock(Customer.class);
	private List<Customer> customersList = new ArrayList<>();
	
	@Before
	public void init() throws URISyntaxException {
		uri = new URI("/api/reservations");

		cash.setAmountTendered(5000.0);
		cash.setAmount(5000.0);
		cash.setDate(LocalDateTime.now());

		reservation.setCheckinDate(LocalDate.of(2012, 12, 12));
		reservation.setCheckoutDate(LocalDate.of(2012, 12, 17));
		reservation.setReservationDate(LocalDate.now());
		reservation.setNumberOfGuests(2);
		reservation.setPayment(cash);

		reservationsList.add(reservation);
		
		customer.addReservation(reservation);
		customersList.add(customer);
	}

	@Test
	public void shouldReturnOneReservationAndStatusOkWithoutParam() throws URISyntaxException, JSONException {
		
		Reservation reservation2 = reservation;
		reservation2.setNumberOfGuests(25);
		reservationsList.add(reservation2);

		Mockito.when(reservationRepository.findAll()).thenReturn(reservationsList);

		ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);

		JSONArray jArray = new JSONArray(result.getBody());

		// Verify request succeed
		Assert.assertEquals(200, result.getStatusCodeValue());
		Assert.assertEquals(jArray.length(), 2);
		

	}

	@Test
	public void shouldReturnOneReservationAndStatusOkByParam() throws URISyntaxException {

		Mockito.when(customerRepository.findByName("Teste")).thenReturn(customersList);
		Mockito.when(customer.getReservations()).thenReturn(reservationsList.stream().collect(Collectors.toSet()));
		
		ResponseEntity<String> result = restTemplate.getForEntity(uri + "?name=Teste", String.class);

		// Verify request succeed
		Assert.assertEquals(200, result.getStatusCodeValue());
		Assert.assertTrue(result.getBody().contains("\"amount\":5000"));
	}

	@Test
	public void shouldReturnOneReservationAndStatusOkById() throws URISyntaxException {

		Mockito.when(reservationRepository.findById(2L)).thenReturn(Optional.of(reservation));

		ResponseEntity<String> result = restTemplate.getForEntity(uri + "/2", String.class);

		/// Verify request succeed
		Assert.assertEquals(200, result.getStatusCodeValue());
		Assert.assertTrue(result.getBody().contains("\"amount\":5000"));
	}

	@Test
	public void shouldReturnNotFoundStatusAndNullBodyByWrongParam() throws URISyntaxException {

		Mockito.when(customerRepository.findByName("Teste")).thenReturn(customersList);
		Mockito.when(customer.getReservations()).thenReturn(reservationsList.stream().collect(Collectors.toSet()));

		ResponseEntity<String> result = restTemplate.getForEntity(uri + "?name=Teste333", String.class);

		// Verify request succeed
		Assert.assertEquals(404, result.getStatusCodeValue());
		Assert.assertEquals(result.getBody(), null);
	}
}