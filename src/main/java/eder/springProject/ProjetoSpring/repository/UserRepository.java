package eder.springProject.ProjetoSpring.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import eder.springProject.ProjetoSpring.model.user;

@Repository
public interface UserRepository extends JpaRepository<user, Long>{
	
	/*query construida para que em determinado ponto do sistema só temos o login para ser validade, no caso o login ao sistema */	
	@Query("select u from user u where u.login = ?1")
	user findUserByLogin(String login);
	
	/*faz update do token na tebela user*/
	@Modifying
	@Transactional
	@Query(nativeQuery = true, value = "update user set token_user = ?1 where login = ?2")	
	void updateTokenUser(String token, String login);

}
