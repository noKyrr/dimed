package br.com.dimed.test.dto;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnySetter;


public class SpotDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5888311220297695790L;
	/**
	 * @return the id
	 */
    Map<String, Object> details = new LinkedHashMap<>();
    
    @JsonAnySetter
    void setDetail(String key, Object value) {
        details.put(key, value);
    }

	/**
	 * @return the details
	 */
	public Map<String, Object> getDetails() {
		return details;
	}


}
