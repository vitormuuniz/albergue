package br.com.albergue.controller.form;

import java.time.LocalDate;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import br.com.albergue.domain.Customer;
import br.com.albergue.domain.Payments;
import br.com.albergue.domain.Reservation;
import br.com.albergue.repository.CustomerRepository;
import br.com.albergue.repository.PaymentsRepository;

public class ReservationForm {
	
	@NotNull
	LocalDate reservationDate;
	@NotNull
	LocalDate checkinDate;
	@NotNull
	LocalDate checkoutDate;
	@NotNull 
	private Payments payments;
	@NotNull
	private
	Long customer_ID;

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
	
	public Payments getPayments() {
		return payments;
	}

	public void setPayments(Payments payments) {
		this.payments = payments;
	}

	public Optional<Customer> returnCustomer(CustomerRepository customerRepository) {
		return customerRepository.findById(getCustomer_ID());
	}
	
	public Reservation returnReservation(PaymentsRepository paymentsRepository) {
		paymentsRepository.save(payments);
		return new Reservation(reservationDate, checkinDate, checkoutDate, payments);
	}
}
