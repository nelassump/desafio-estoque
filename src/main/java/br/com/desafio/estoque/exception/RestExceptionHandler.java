package br.com.desafio.estoque.exception;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javassist.NotFoundException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(value = { NotFoundException.class })
	public ResponseEntity<?> handleNotFoundException(NotFoundException ex) {
		ErrorMessage errorMessage = new ErrorMessage(HttpStatus.NOT_FOUND.value(),
													 "Produto não encontrado.",
													 ex.getMessage(),
													 new Date(),
													 ex.getClass().getName());
		return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
	}		
	
	@Override
	public ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		List<ErrorObject> errors = getErrors(ex);
		ErrorResponse errorResponse = getErrorResponse(ex, status, errors);
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}
	
	private ErrorResponse getErrorResponse(
			MethodArgumentNotValidException ex, HttpStatus status, List<ErrorObject> errors) {
        return new ErrorResponse(        	    
        		status.value(),
        		status.getReasonPhrase(),
                "A requisição possui campos inválidos",
                errors);
    }
	
	private List<ErrorObject> getErrors(MethodArgumentNotValidException ex) {
	    return ex.getBindingResult().getFieldErrors().stream()
	            .map(error -> new ErrorObject(error.getDefaultMessage(), error.getField(), error.getRejectedValue()))
	            .collect(Collectors.toList());
	}
	
	@Override
	public ResponseEntity<Object> handleMissingServletRequestParameter(
			MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		ErrorMessage errorMessage = new ErrorMessage(HttpStatus.BAD_REQUEST.value(),
													"Parâmetro 'quantity' não foi informado",
													 ex.getMessage(),
													 new Date(),
													 ex.getClass().getName());
		return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(
			Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
		ErrorMessage errorMessage = new ErrorMessage(status.value(),
													 status.getReasonPhrase(),
													 ex.getMessage(),
													 new Date(),
													 ex.getClass().getName());
		return new ResponseEntity<>(errorMessage, headers, status);
	}
	
	@ExceptionHandler(value = { Exception.class })
	public ResponseEntity<?> handleException(Exception ex) {
		ErrorMessage errorMessage = new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
													 "Internal Server Error",
													 ex.getMessage(),
													 new Date(),
													 ex.getClass().getName());
		return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(value = { TransactionSystemException.class })
	public ResponseEntity<?> handleTransactionSystemException(TransactionSystemException ex) {
		ErrorMessage errorMessage = new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
													"Erro ao atualizar produto",
													 ex.getMessage(),
													 new Date(),
													 ex.getClass().getName());
		return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
