package br.com.dimed.test.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Objeto contendo dados para pesquisa de um conjunto de pontos proximos a um ponto específico, de uma linha específica")
public class BusLineSearchDto implements Serializable {

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
	
	@ApiModelProperty(notes = "Número ID da unidade de transporte", required = true)
	@NotNull
	private Long lineId;
	
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
	 * @return the lineId
	 */
	public Long getLineId() {
		return lineId;
	}
	/**
	 * @param lineId the lineId to set
	 */
	public void setLineId(Long lineId) {
		this.lineId = lineId;
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
