package br.com.dimed.test.services;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.com.dimed.test.domain.BusLineModel;
import br.com.dimed.test.domain.SpotModel;
import br.com.dimed.test.dto.BusLineSearchDto;
import br.com.dimed.test.dto.SpotSearchDto;
import br.com.dimed.test.repositories.BusLineRepository;
import br.com.dimed.test.repositories.SpotRepository;
import javassist.NotFoundException;

@Service
public class BusLineService {

	@Autowired
	BusLineRepository busLineRepository;
	

	public boolean runImport() {
		try {
			List<BusLineModel> importedLines = importPoaLines();

			if (importedLines == null || importedLines.isEmpty())
				return false;

			for (BusLineModel item : importedLines)
				updateLineCreateIfNotExists(item);
			return true;

		} catch (Exception e) {
			return false;
		}
	}

	private void updateLineCreateIfNotExists(BusLineModel item) throws Exception {
		try {
			BusLineModel dbModel = busLineRepository.findById(item.getId()).get();
			if (!dbModel.fullEquals(item)) {
				dbModel.setCodigo(item.getCodigo());
				dbModel.setNome(item.getNome());
				busLineRepository.save(dbModel);
			}
		} catch (NoSuchElementException e) {
			busLineRepository.save(item);
		}
	}

	private List<BusLineModel> importPoaLines() throws Exception {
		String theUrl = "http://www.poatransporte.com.br/php/facades/process.php?a=nc&p=%25&t=o";
		URL url = new URL(theUrl);
		URLConnection uc = url.openConnection();
		uc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		InputStream in = uc.getInputStream();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String result = IOUtils.toString(in, StandardCharsets.UTF_8);
		BusLineModel[] incomeLines = gson.fromJson(result, BusLineModel[].class);
		List<BusLineModel> incomeLinesList = Arrays.asList(incomeLines);
		return incomeLinesList;
	}

	public List<BusLineModel> listAll() throws Exception {
		List<BusLineModel> lineList = busLineRepository.findAll();
		if(lineList == null || lineList.isEmpty())
			throw new NotFoundException("Lista não encontrada");
		return lineList;
	}

	public List<BusLineModel> listAll(String nome) throws Exception {
		List<BusLineModel> lineList = busLineRepository.findByNomeContainingIgnoreCase(nome);
		if(lineList == null || lineList.isEmpty())
			throw new NotFoundException("Lista não encontrada");
		return lineList;
	}

	
	public List<BusLineModel> findBuslinesBySpot(SpotSearchDto lineDto) {
		return busLineRepository.findBySpotInRadius(lineDto.getLat(), lineDto.getLng(), lineDto.getRadiusInMeters());
	}


}
