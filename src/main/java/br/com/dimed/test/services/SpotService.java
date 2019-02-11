package br.com.dimed.test.services;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import br.com.dimed.test.domain.BusLineModel;
import br.com.dimed.test.domain.SpotModel;
import br.com.dimed.test.domain.SpotModel.SpotCompositeId;
import br.com.dimed.test.dto.BusLineSearchDto;
import br.com.dimed.test.dto.SpotDto;
import br.com.dimed.test.repositories.BusLineRepository;
import br.com.dimed.test.repositories.SpotRepository;
import javassist.NotFoundException;

@Service
public class SpotService {

	@Autowired
	SpotRepository spotRepository;

	@Autowired
	BusLineRepository busLineRepository;

	public boolean runImport() {
		try {
			for (BusLineModel busLine : busLineRepository.findAll()) {
				List<SpotModel> importedSpots = importPoaSpotsByLine(busLine);
				if (importedSpots == null || importedSpots.isEmpty())
					continue;

				for (SpotModel item : importedSpots)
					updateSpotCreateIfNotExists(item, busLine);
			}

			return true;

		} catch (Exception e) {
			return false;
		}
	}

	private void updateSpotCreateIfNotExists(SpotModel item, BusLineModel busLine) throws Exception {
		try {
			SpotModel dbModel = spotRepository.findById(item.getId()).get();

			updateSpotIfNeeded(item, dbModel);
			updataRelationship(busLine, dbModel);

		} catch (NoSuchElementException e) {
			SpotModel dbModel = spotRepository.save(item);
			updataRelationship(busLine, dbModel);
		}
	}

	private void updataRelationship(BusLineModel busLine, SpotModel spotDbModel) {// todo
		if (busLine.getSpots() == null)
			busLine.setSpots(new HashSet<>());
		if (!busLine.getSpots().contains(spotDbModel))
			busLine.getSpots().add(spotDbModel);
		busLineRepository.save(busLine);
	}

	private void updateSpotIfNeeded(SpotModel dtoItem, SpotModel dbModel) {// todo

		if (!dbModel.fullEquals(dtoItem)) {
			dbModel.setLat(dtoItem.getLat());
			dbModel.setLng(dtoItem.getLng());
			spotRepository.save(dbModel);
		}

	}

	private List<SpotModel> importPoaSpotsByLine(BusLineModel busLine) throws Exception {
		String theUrl = "http://www.poatransporte.com.br/php/facades/process.php?a=il&p=";
		URL url = new URL(theUrl + busLine.getId());
		URLConnection uc = url.openConnection();
		uc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		InputStream in = uc.getInputStream();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String result = IOUtils.toString(in, StandardCharsets.UTF_8);

		List<SpotModel> incomeSpotsList = spotResultToList(result, busLine);
		return incomeSpotsList;
	}

	private List<SpotModel> spotResultToList(String result, BusLineModel busLine)
			throws IOException, JsonParseException, JsonMappingException {
		ObjectMapper mapper = new ObjectMapper();
		SpotDto map = mapper.readValue(result, SpotDto.class);

		List<SpotModel> incomeSpotsList = new ArrayList<>();
		int i = 0;
		while (map.getDetails().containsKey("" + i)) {
			incomeSpotsList.add(convertSpotMapToModel(busLine, map, i++));

		}
		return incomeSpotsList;
	}

	private SpotModel convertSpotMapToModel(BusLineModel busLine, SpotDto map, int i) {
		Map innerMap = (Map) (map.getDetails().get("" + i));
		Double lat = Double.valueOf((String) innerMap.get("lat"));
		Double lng = Double.valueOf((String) innerMap.get("lng"));
		return new SpotModel(Long.valueOf(i), busLine, lat, lng);
	}

	public List<SpotModel> findSpotsByBusline(BusLineSearchDto lineDto) throws Exception {
		if(lineDto.getLineId() == null)
			throw new NotFoundException("Linha não definida");
		return spotRepository.findByLineAndSpotInRadius(lineDto.getLat(), lineDto.getLng(), lineDto.getLineId(), lineDto.getRadiusInMeters());
	}

}
