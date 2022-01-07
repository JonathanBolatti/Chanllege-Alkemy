package com.challenge.disney.services;

import com.challenge.disney.entities.User;
import com.challenge.disney.exception.ErrorService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.challenge.disney.enums.Role;
import com.challenge.disney.repositories.UserRepository;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


/**
 *
 * @author Jonathan
 */
@Service
public class UserService implements UserDetailsService{

	@Autowired
	private UserRepository userRepo; 
	
	@Autowired
	private EmailService mailService; 
	
	@Transactional
	public User save(User user) throws ErrorService, IOException {

		String[] symbols = {"+", "=", "-", "*", "'"};

		if (user.getFullName().isEmpty() || user.getFullName()== null) {
			throw new ErrorService("Debe ingresar un Nombre de Usuario");
		}
		
		if (user.getEmail().isEmpty() || user.getEmail() == null) {
			throw new ErrorService("Debe ingresar un email para completar registro");
		}

		User userMail = findByEmail(user.getEmail());
		if (userMail != null) {
			throw new ErrorService("El email ya está registrado");
		}

		if (user.getPassword().isEmpty() || user.getPassword() == null) {
			throw new ErrorService("El password no puede estar vacio");
		}
		if (user.getPassword().length() < 6) {
			throw new ErrorService("La contraseña debe contener al menos 6 digitos");
		}

		for (int i = 0; i < symbols.length; i++) {
			if (user.getPassword().contains(symbols[i])) {
				throw new ErrorService("La contraseña no debe contener los símbolos: \"+\", \"=\", \"-\", \"*\", \"'\"");
			}
		}

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		user.setPassword(encoder.encode(user.getPassword()));

		user.setRole(Role.USER);
		
		//mailService.enviarMail("Bienvenidos al Mundo de Disney!", "La Administracion", user.getEmail());
		return userRepo.save(user);
	}
	
	public List<User> listAll() {
		List<User> lista = userRepo.findAll();
		return lista;
	}

	//cree este servicio (jonathan)
	public List<User> listAllByQ(String query) {
		List<User> lista = userRepo.findAllByQ("%" + query + "%");
		return lista;
	}

	//Este Metodo se utiliza para validar el Id en el registro
	public Optional<User> findById(String id) {
		return userRepo.findById(id);
	}

	public User searchForID(String id) {
		return userRepo.searchForID(id);
	}

	public User findByEmail(String email) {
		return userRepo.findByEmail(email);
	}

	
	
	@Transactional
	public void deleteById(String id) throws ErrorService {
		Optional<User> optional = userRepo.findById(id);
		if (optional.isPresent()) {
			userRepo.delete(optional.get());

		} else {
			throw new ErrorService("No se pudo procesar la solicitud o no se encuentra el usuario que desea Eliminar");
		}

	}

	@Transactional
	public void deleteByObject(User user) {
		userRepo.delete(user);
	}


	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		try {
			User user = userRepo.findByEmail(email);
			List<GrantedAuthority> authorities = new ArrayList<>();
			authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
			if (user.getRole().equals(Role.ADMIN)) {
				authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
			}
			ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
			HttpSession session = attr.getRequest().getSession(true);
			session.setAttribute("usuariosession", user);
			return new org.springframework.security.core.userdetails.User(email, user.getPassword(), authorities);
		} catch (Exception e) {
			throw new UsernameNotFoundException("El usuario no existe");
		}
	}
	
}
