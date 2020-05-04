package br.com.albergue.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import br.com.albergue.domain.Customer;
import br.com.albergue.repository.CustomerRepository;

@Component
public class CustomUserDetailService implements UserDetailsService {

	private final CustomerRepository customerRepository;
	
	@Autowired
	public CustomUserDetailService(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Customer customer = Optional.ofNullable(customerRepository.findByUsername(username))
			.orElseThrow(() -> new UsernameNotFoundException("User not found"));
		
		List<GrantedAuthority> authorityListAdmin = AuthorityUtils.createAuthorityList("USER","ADMIN");
		List<GrantedAuthority> authorityListUser = AuthorityUtils.createAuthorityList("USER");
		return new org.springframework.security.core.userdetails
					.User(customer.getUsername(), customer.getPassword(), customer.isAdmin() ? authorityListAdmin : authorityListUser);
	}
	
}
