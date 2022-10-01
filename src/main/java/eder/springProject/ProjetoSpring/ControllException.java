package eder.springProject.ProjetoSpring;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.mysql.cj.jdbc.exceptions.MySQLTimeoutException;

@RestControllerAdvice
@ControllerAdvice
public class ControllException extends ResponseEntityExceptionHandler {

	/* qualquer erro que gerar execeção Exception, RuntimeException ou Throwable */
	/* sera interceptada e criar uma lista de msg de errors */
	@ExceptionHandler({ Exception.class, RuntimeException.class, Throwable.class })
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		String msg = "";

		if (ex instanceof MethodArgumentNotValidException) {
			List<ObjectError> list = ((MethodArgumentNotValidException) ex).getBindingResult().getAllErrors();
			for (ObjectError objectError : list) {
				msg += objectError.getDefaultMessage() + "\n";
			}
		} else {
			/* msg padrão */
			msg = ex.getMessage();
		}

		/* construção do Objeto Error */
		ObjectErrors objetoErro = new ObjectErrors();
		objetoErro.setError(msg);
		objetoErro.setCode(status.value() + " ==> " + status.getReasonPhrase());

		return new ResponseEntity<>(objetoErro, headers, status);
	}

	/*METODO QUE INTECEPTA OS PRICIPAIUS ERROS QUE POSSAM ACONTECE ENTRE API E BANCO DE DADOS*/
	@ExceptionHandler({ DataIntegrityViolationException.class, ConstraintViolationException.class,
			MySQLTimeoutException.class, SQLException.class })
	protected ResponseEntity<Object> handleExceptionDataIntegry(Exception ex) {

		String msg = "";

		if (ex instanceof DataIntegrityViolationException) {
			msg = ((DataIntegrityViolationException) ex).getCause().getCause().getMessage();
		} else if (ex instanceof ConstraintViolationException) {
			msg = ((ConstraintViolationException) ex).getCause().getCause().getMessage();
		} else if (ex instanceof MySQLTimeoutException) {
			msg = ((MySQLTimeoutException) ex).getCause().getCause().getMessage();
		} else if (ex instanceof SQLException) {
			msg = ((SQLException) ex).getCause().getCause().getMessage();
		} else {
			/* msg padrão */
			msg = ex.getMessage();
		}

		/* construção do Objeto Error */
		ObjectErrors objetoErro = new ObjectErrors();
		objetoErro.setError(msg);
		objetoErro.setCode(HttpStatus.INTERNAL_SERVER_ERROR + " ==> " + HttpStatus.INTERNAL_SERVER_ERROR);

		return new ResponseEntity<>(objetoErro, HttpStatus.INTERNAL_SERVER_ERROR);

	}

}
