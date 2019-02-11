package br.com.dimed.test.services;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.dimed.test.domain.BusLineModel;
import br.com.dimed.test.domain.SpotModel;
import br.com.dimed.test.domain.SpotModel.SpotCompositeId;
import br.com.dimed.test.dto.SpotSearchDto;
import br.com.dimed.test.repositories.BusLineRepository;
import br.com.dimed.test.repositories.SpotRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class BusLineServiceTest {

	BusLineModel bus;
	
	
	@Autowired
	BusLineRepository busLineRepository;
	@Autowired
	SpotRepository spotRepository;
	@Autowired
	BusLineService busLineService;
	
	@Before
	public void beforeTest() {
		bus = new BusLineModel();
		bus.setId(1l);
		bus.setCodigo("0001");
		bus.setNome("Teste JUnit");
		busLineRepository.save(bus);
		
		SpotModel spot = new SpotModel();
		spot.setLat(-30d);
		spot.setLng(30d);
		spot.setId(new SpotCompositeId(1l, bus));
		spotRepository.save(spot);
		
		
	}
	
	@Test
	public void runImport() {
		assertTrue(busLineService.runImport());
	}
	
	@Test
	public void listAll() {
		try {
			assertTrue(busLineService.listAll().contains(bus));
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void listAllNome() {
		try {
			assertTrue(busLineService.listAll("JUnit").contains(bus));
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void findBuslinesBySpot() {
		try {
			SpotSearchDto dto = new SpotSearchDto();
			dto.setLat(-30d);
			dto.setLng(30d);
			dto.setRadiusInMeters(10l);
			assertTrue(busLineService.listAll().contains(bus));
		} catch (Exception e) {
			fail();
		}
	}

}
