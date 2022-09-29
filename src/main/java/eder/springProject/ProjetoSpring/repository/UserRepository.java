package eder.springProject.ProjetoSpring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import eder.springProject.ProjetoSpring.model.user;

@Repository
public interface UserRepository extends JpaRepository<user, Long>{
	
	/*query construida para que em determinado ponto do sistema só temos o login para ser validade, no caso o login ao sistema */	
	@Query("select u from user u where u.login = ?1")
	user findUserByLogin(String login);

}
