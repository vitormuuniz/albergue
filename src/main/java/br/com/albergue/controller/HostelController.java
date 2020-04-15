package br.com.albergue.controller;

import java.net.URI;
import java.time.LocalDate;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.albergue.controller.dto.CustomerDto;
import br.com.albergue.controller.dto.ReservationDto;
import br.com.albergue.controller.dto.RoomDto;
import br.com.albergue.controller.form.CustomerForm;
import br.com.albergue.controller.form.ReservationForm;
import br.com.albergue.controller.form.RoomForm;
import br.com.albergue.domain.Customer;
import br.com.albergue.domain.Reservation;
import br.com.albergue.domain.Room;
import br.com.albergue.repository.AddressRepository;
import br.com.albergue.repository.CustomerRepository;
import br.com.albergue.repository.PaymentsRepository;
import br.com.albergue.repository.ReservationRepository;
import br.com.albergue.repository.RoomRepository;

@RestController
@RequestMapping("/api")
public class HostelController {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private ReservationRepository reservationRepository;

	@Autowired
	private RoomRepository roomRepository;
	
	@Autowired
	private PaymentsRepository paymentsRepository;
	
	
	@PostMapping("/customers") // chegam do cliente para a api
	public ResponseEntity<CustomerDto> registerCustomer(@RequestBody @Valid CustomerForm form, UriComponentsBuilder uriBuilder) {
		Customer customer = form.returnCustomer(addressRepository);
		customerRepository.save(customer);

		// path indica o caminho do recurso sendo chamado (pra nao passar o caminho
		// completo)
		// buildAndExpend serve para pegar e substituir o id em {id} dinamicamente
		// toUri para transformar na uri completa
		URI uri = uriBuilder.path("/customers/{id}").buildAndExpand(customer.getId()).toUri();
		return ResponseEntity.created(uri).body(new CustomerDto(customer));
	}
	
	// @RequestParam indica que os parametros irão vir pela url e que são
	// obrigatórios
	// @PageableDefault serve para dizer qual ordenação deverá ser feita caso não
	// sejam passados parametros

	@GetMapping("/customers") // dto = saem da api e é retornado para o cliente
	public Page<CustomerDto> listAllCustomers(@RequestParam(required = false) String name,
			@PageableDefault(sort = "id", direction = Direction.DESC, page = 0, size = 10) Pageable pagination) {

		if (name == null)
			return CustomerDto.converter(customerRepository.findAll(pagination));
		else
			return CustomerDto.converter(customerRepository.findByName(name, pagination));
	}

	// @PathVariable indica que esse 'id' virá através da url com /topicos/id
	// inves de ser passado com '?id='
	@GetMapping("/customers/{id}")
	public ResponseEntity<CustomerDto> listOneCustomer(@PathVariable Long id) {
		Optional<Customer> customer = customerRepository.findById(id);
		if (customer.isPresent())
			return ResponseEntity.ok(new CustomerDto(customer.get()));
		else
			return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/customers/{id}")
	@Transactional
	public ResponseEntity<?> deleteCustomer(@PathVariable Long id) {
		Optional<Customer> customer = customerRepository.findById(id);
		if (customer.isPresent()) {
			customerRepository.deleteById(id);
			return ResponseEntity.ok().build();
		} else
			return ResponseEntity.notFound().build();
	}
	
	@PostMapping("/reservations") // chegam do cliente para a api
	public ResponseEntity<ReservationDto> registerReservation(@RequestBody @Valid ReservationForm form, UriComponentsBuilder uriBuilder) {
		
		Optional<Customer> customerOp = form.returnCustomer(customerRepository);
		
		if(customerOp.isPresent()) {
			Reservation reservation = form.returnReservation(paymentsRepository);
			reservationRepository.save(reservation);
			
			Customer customer = customerOp.get();
			customer.addReservation(reservation);
			customerRepository.save(customer);
			
			// path indica o caminho do recurso sendo chamado (pra nao passar o caminho
			// completo)
			// buildAndExpend serve para pegar e substituir o id em {id} dinamicamente
			// toUri para transformar na uri completa
			URI uri = uriBuilder.path("/reservations/{id}").buildAndExpand(customer.getId()).toUri();
			return ResponseEntity.created(uri).body(new ReservationDto(reservation));
		} else 
			return (ResponseEntity<ReservationDto>) ResponseEntity.badRequest();
	}
	
	@GetMapping("/reservations") // dto = saem da api e é retornado para o cliente
	public Page<ReservationDto> listAllReservations(@RequestParam(required = false) LocalDate reservationDate,
			@PageableDefault(sort = "id", direction = Direction.DESC, page = 0, size = 10) Pageable pagination) {

		if (reservationDate == null)
			return ReservationDto.converter(reservationRepository.findAll(pagination));
		else
			return ReservationDto.converter(reservationRepository.findByReservationDate(reservationDate, pagination));
	}
	
	// @PathVariable indica que esse 'id' virá através da url com /topicos/id
	// inves de ser passado com '?id='
	@GetMapping("/reservations/{id}")
	public ResponseEntity<ReservationDto> listOneReservation(@PathVariable Long id) {
		Optional<Reservation> reservation = reservationRepository.findById(id);
		if (reservation.isPresent())
			return ResponseEntity.ok(new ReservationDto(reservation.get()));
		else
			return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/reservations/{id}")
	@Transactional
	public ResponseEntity<?> deleteReservation(@PathVariable Long id) {
		Optional<Reservation> reservation = reservationRepository.findById(id);
		if (reservation.isPresent()) {
			reservationRepository.deleteById(id);
			return ResponseEntity.ok().build();
		} else
			return ResponseEntity.notFound().build();
	}
	
	
	@PostMapping("/rooms") // chegam do cliente para a api
	public ResponseEntity<RoomDto> registerRoom(@RequestBody @Valid RoomForm form, UriComponentsBuilder uriBuilder) {
		Room room = form.returnRoom();
		roomRepository.save(room);

		// path indica o caminho do recurso sendo chamado (pra nao passar o caminho
		// completo)
		// buildAndExpend serve para pegar e substituir o id em {id} dinamicamente
		// toUri para transformar na uri completa
		URI uri = uriBuilder.path("/rooms/{id}").buildAndExpand(room.getId()).toUri();
		return ResponseEntity.created(uri).body(new RoomDto(room));
	}
	
	@GetMapping("/rooms") // dto = saem da api e é retornado para o cliente
	public Page<RoomDto> listAllRooms(@RequestParam(required = false) Integer number,
			@PageableDefault(sort = "id", direction = Direction.DESC, page = 0, size = 10) Pageable pagination) {

		if (!(number instanceof Integer))
			return RoomDto.converter(roomRepository.findAll(pagination));
		else
			return RoomDto.converter(roomRepository.findByNumber(number, pagination));
	}
	
	// @PathVariable indica que esse 'id' virá através da url com /topicos/id
	// inves de ser passado com '?id='
	@GetMapping("/rooms/{id}")
	public ResponseEntity<RoomDto> listOneRoom(@PathVariable Long id) {
		Optional<Room> room = roomRepository.findById(id);
		if (room.isPresent())
			return ResponseEntity.ok(new RoomDto(room.get()));
		else
			return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/rooms/{id}")
	@Transactional
	public ResponseEntity<?> deleteRoom(@PathVariable Long id) {
		Optional<Room> room = roomRepository.findById(id);
		if (room.isPresent()) {
			roomRepository.deleteById(id);
			return ResponseEntity.ok().build();
		} else
			return ResponseEntity.notFound().build();
	}
}