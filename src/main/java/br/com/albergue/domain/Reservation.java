package br.com.albergue.domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Reservation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String reservationCode;
	private int numberOfGuests;
	private LocalDate reservationDate;
	private LocalDate checkinDate;
	private LocalDate checkoutDate;
	@OneToMany
	private Set<Room> rooms = new HashSet<>();
//	private Payment payment;
	
	public Reservation(LocalDate reservationDate, LocalDate checkinDate, LocalDate checkoutDate) {
		this.reservationDate = reservationDate;
		this.checkinDate = checkinDate;
		this.checkoutDate = checkoutDate;
	}

	public Reservation() {
		this.reservationDate = LocalDate.now();
	}

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

	public void addRoom(Room room) {
		rooms.add(room);
	}

	public Set<Room> getRooms() {
		return rooms;
	}

//	public Payment createPayment(String type) {
//		if (payment == null) {
//			payment = Payment.createPayment(type);
//		}
//		return payment;
//	}

//	// TODO Implement the business logic
//	public double calculateTotalAmount() {
//		double amount = 0.0;
//		int numberOfDays = 0; //
//		for (Room room : rooms) {
//
//		}
//		return 0.0;
//	}

	public String getReservationCode() {
		return reservationCode;
	}

	public void setReservationCode(String reservationCode) {
		this.reservationCode = reservationCode;
	}

	public int getNumberOfGuests() {
		return numberOfGuests;
	}

	public void setNumberOfGuests(int numberOfGuests) {
		this.numberOfGuests = numberOfGuests;
	}

//	public Payment getPayment() {
//		return payment;
//	}
//
//	public void setPayment(Payment payment) {
//		this.payment = payment;
//	}

	public void setRooms(Set<Room> rooms) {
		this.rooms = rooms;
	}
}
