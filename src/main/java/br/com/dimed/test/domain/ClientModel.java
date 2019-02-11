package br.com.dimed.test.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "client")
@ApiModel(description = "Cliente que possui um grupo de linhas de unidade de transporte")
public class ClientModel implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6550202620039740396L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@ApiModelProperty(notes = "Identificador do cliente", required = true)
	private Long id;
	
	@NotNull
	@NotBlank
	@ApiModelProperty(notes = "Nome do cliente", required = true)
	private String name;
	
	@ManyToMany
	@JoinTable(name="client_lines")
	@ApiModelProperty(notes = "Conjunto de unidades de transporte associadas a este cliente", required = true)
	private Set<BusLineModel> busLines;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the busLines
	 */
	public Set<BusLineModel> getBusLines() {
		return busLines;
	}

	/**
	 * @param busLines the busLines to set
	 */
	public void setBusLines(Set<BusLineModel> busLines) {
		this.busLines = busLines;
	}
	
	
	
}
