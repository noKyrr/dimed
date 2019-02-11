package br.com.dimed.test.dto;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Objeto de resposta padrão")
public class ResponseModel implements Serializable {
   
	/**
	 * 
	 */
	private static final long serialVersionUID = -3728860600850298906L;

	@ApiModelProperty(notes = "Mensagem de erro ou informativa", required = true)
	String message;
	
    @ApiModelProperty(notes = "Data da operação", required = true)
	private Date timestamp;

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the timestamp
	 */
	public Date getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public ResponseModel(String message, Date timestamp) {
		super();
		this.message = message;
		this.timestamp = timestamp;
	}

	public ResponseModel(String message) {
		this(message,new Date());
	}
	

}
