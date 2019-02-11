package br.com.dimed.test.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "spot")
@ApiModel(description = "Localização geográfica")
public class SpotModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7493989846948037700L;

	@JsonIgnore
	@EmbeddedId
	SpotCompositeId id;

	@NotNull
	@Column(nullable = false, precision = 17)
	@ApiModelProperty(notes = "Latitide do ponto", required = true)
	private Double lat;

	@NotNull
	@Column(nullable = false, precision = 17)
	@ApiModelProperty(notes = "Longitude do ponto", required = true)
	private Double lng;

	public SpotModel() {
		super();
	}

	public SpotModel(Long id, BusLineModel line, @NotNull Double lat, @NotNull Double lng) {
		super();
		this.id = new SpotCompositeId(id, line);
		this.lat = lat;
		this.lng = lng;
	}

	@ApiModelProperty(notes = "Id utilizado pela empresa de transportes", required = true)
	public Long getApiId() {
		return id.getId();
	}
	
	/**
	 * @return the id
	 */
	public SpotCompositeId getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(SpotCompositeId id) {
		this.id = id;
	}

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
		SpotModel other = (SpotModel) obj;
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
		SpotModel other = (SpotModel) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lat == null) {
			if (other.lat != null)
				return false;
		} else if (!lat.equals(other.lat))
			return false;
		if (lng == null) {
			if (other.lng != null)
				return false;
		} else if (!lng.equals(other.lng))
			return false;
		return true;
	}

	/**
	 * Composite PK Id for SpotModel
	 * 
	 * @author Victor
	 *
	 */
	@Embeddable
	public static class SpotCompositeId implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = -2255581896448531243L;

		public Long id;

		@ManyToOne
		public BusLineModel line;

		public SpotCompositeId() {
			super();
		}

		public SpotCompositeId(Long id, BusLineModel line) {
			super();
			this.id = id;
			this.line = line;
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
		 * @return the line
		 */
		public BusLineModel getLine() {
			return line;
		}

		/**
		 * @param line the line to set
		 */
		public void setLine(BusLineModel line) {
			this.line = line;
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
			result = prime * result + ((line == null) ? 0 : line.hashCode());
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
			SpotCompositeId other = (SpotCompositeId) obj;
			if (id == null) {
				if (other.id != null)
					return false;
			} else if (!id.equals(other.id))
				return false;
			if (line == null) {
				if (other.line != null)
					return false;
			} else if (!line.equals(other.line))
				return false;
			return true;
		}

	}

}
