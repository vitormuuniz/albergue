package br.com.albergue.controller.form;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import javax.validation.constraints.NotNull;

import br.com.albergue.domain.Customer;
import br.com.albergue.domain.Payments;
import br.com.albergue.domain.Reservation;
import br.com.albergue.domain.Room;
import br.com.albergue.repository.CustomerRepository;
import br.com.albergue.repository.DailyRateRepository;
import br.com.albergue.repository.PaymentsRepository;
import br.com.albergue.repository.RoomRepository;

public class ReservationForm {
	
	@NotNull
	LocalDate reservationDate;
	@NotNull
	LocalDate checkinDate;
	@NotNull
	LocalDate checkoutDate;
	@NotNull 
	private Payments payment;
	@NotNull
	private	Long customer_ID;
	@NotNull
	private Set<Room> rooms;

	public LocalDate getReservationDate() {
		return reservationDate;
	}

	public void setReservationDate(LocalDate reservationDate) {
		this.reservationDate = reservationDate;
	}

	public LocalDate getCheckinDate() {
		return checkinDate;
	}

	public void setCheckinDate(LocalDate checkinDate) {
		this.checkinDate = checkinDate;
	}

	public LocalDate getCheckoutDate() {
		return checkoutDate;
	}

	public void setCheckoutDate(LocalDate checkoutDate) {
		this.checkoutDate = checkoutDate;
	}

	public Long getCustomer_ID() {
		return customer_ID;
	}

	public void setCustomer_ID(Long customer_ID) {
		this.customer_ID = customer_ID;
	}
	
	public Payments getPayment() {
		return payment;
	}

	public void setPayment(Payments payment) {
		this.payment = payment;
	}
	
	public Set<Room> getRooms() {
		return rooms;
	}

	public void setRooms(Set<Room> rooms) {
		this.rooms = rooms;
	}

	public Optional<Customer> returnCustomer(CustomerRepository customerRepository) {
		return customerRepository.findById(getCustomer_ID());
	}
	
	public Reservation returnReservation(PaymentsRepository paymentsRepository, RoomRepository roomRepository, DailyRateRepository dailyRateRepository) {
		paymentsRepository.save(getPayment());
		
		for (Room room : rooms) {
			dailyRateRepository.save(room.getDailyRate());
			roomRepository.save(room);
		}
		
		return new Reservation(getReservationDate(), getCheckinDate(), getCheckoutDate(), getRooms(), getPayment());
	}
}
