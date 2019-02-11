package br.com.dimed.test.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Objeto contendo dados para pesquisa de um conjunto de linhas de unidades de transporte, proximos a um ponto específico")
public class SpotSearchDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8903508018512975372L;

	
	@ApiModelProperty(notes = "Latitide do ponto de busca", required = true)
	@NotNull
	private Double lat;
	
	@ApiModelProperty(notes = "Longitude do ponto de busca", required = true)
	@NotNull
	private Double lng;
	
	@ApiModelProperty(notes = "Raio de busca em metros", required = true)
	@NotNull
	private Long radiusInMeters;
	
	/**
	 * @return the lat
	 */
	public Double getLat() {
		return lat;
	}
	/**
	 * @param lat the lat to set
	 */
	public void setLat(Double lat) {
		this.lat = lat;
	}
	/**
	 * @return the lng
	 */
	public Double getLng() {
		return lng;
	}
	/**
	 * @param lng the lng to set
	 */
	public void setLng(Double lng) {
		this.lng = lng;
	}
	/**
	 * @return the radiusInMeters
	 */
	public Long getRadiusInMeters() {
		return radiusInMeters;
	}
	/**
	 * @param radiusInMeters the radiusInMeters to set
	 */
	public void setRadiusInMeters(Long radiusInMeters) {
		this.radiusInMeters = radiusInMeters;
	}
	
	
}
