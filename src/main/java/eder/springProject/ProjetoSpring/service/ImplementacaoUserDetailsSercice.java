package eder.springProject.ProjetoSpring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import eder.springProject.ProjetoSpring.model.user;
import eder.springProject.ProjetoSpring.repository.UserRepository;

@Service
public class ImplementacaoUserDetailsSercice implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		
		/*Consulta no banco o usuario*/
		
		user usuario = userRepository.findUserByLogin(username);
		
		if (usuario == null) {
			throw new UsernameNotFoundException("Usuário não foi encontrado");
		}
		
		return new User(usuario.getLogin(),
				usuario.getPassword(),
				usuario.getAuthorities());
	}

}
