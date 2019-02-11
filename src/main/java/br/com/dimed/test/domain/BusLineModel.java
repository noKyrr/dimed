package br.com.dimed.test.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.dimed.test.domain.SpotModel.SpotCompositeId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "bus_line")
@ApiModel(description = "Linha de ônibus contendo código, nome e trajetória em pontos")
public class BusLineModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7986700093091487475L;

	@Id
    @ApiModelProperty(notes = "Indice da unidade de transporte", required = true)
	private Long id;

	@NotBlank
	@NotNull
	@Column(nullable = false)
    @ApiModelProperty(notes = "Código da unidade de transporte", required = true)
	private String codigo;

	@NotBlank
	@NotNull
	@Column(nullable = false)
    @ApiModelProperty(notes = "Nome da unidade de transporte", required = true)
	private String nome;

    @ApiModelProperty(notes = "Pontos na tragetória da unidade de transporte", required = true)
	@OneToMany(mappedBy = "id.line", fetch = FetchType.EAGER, targetEntity = SpotModel.class)
	private Set<SpotModel> spots;

	/**
	 * @return the spots
	 */
	public Set<SpotModel> getSpots() {
		return spots;
	}

	/**
	 * @param spots the spots to set
	 */
	public void setSpots(Set<SpotModel> spots) {
		this.spots = spots;
	}

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
	 * @return the codigo
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BusLineModel other = (BusLineModel) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public boolean fullEquals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BusLineModel other = (BusLineModel) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

}
