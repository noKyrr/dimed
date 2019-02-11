package br.com.dimed.test.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.dimed.test.services.BusLineService;
import br.com.dimed.test.services.SpotService;

@EnableScheduling
@Component
public class Scheduler {
	
	@Autowired
	SpotService spotService;
	
	@Autowired
	BusLineService busLineService;
	
	
	@Scheduled(fixedRate=3600000l)
	public void updatePoaLines() {
		busLineService.runImport();
		spotService.runImport();
	}

}
