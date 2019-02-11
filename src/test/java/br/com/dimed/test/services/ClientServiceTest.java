package br.com.dimed.test.services;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.dimed.test.domain.BusLineModel;
import br.com.dimed.test.domain.ClientModel;
import br.com.dimed.test.domain.SpotModel;
import br.com.dimed.test.domain.SpotModel.SpotCompositeId;
import br.com.dimed.test.repositories.BusLineRepository;
import br.com.dimed.test.repositories.ClientRepository;
import br.com.dimed.test.repositories.SpotRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class ClientServiceTest {
	
	@Autowired
	ClientRepository clientRepository;
	@Autowired
	BusLineRepository busLineRepository;
	@Autowired
	SpotRepository spotRepository;

	
	@Autowired
	ClientService clientService;
	
	

	BusLineModel bus;
	ClientModel client;
	ClientModel clientToDel;

	
	@Before
	public void BeforeTest() {
		bus = new BusLineModel();
		bus.setId(1l);
		bus.setCodigo("0001");
		bus.setNome("Bus Teste JUnit");
		busLineRepository.save(bus);
		
		SpotModel spot = new SpotModel();
		spot.setLat(-30d);
		spot.setLng(30d);
		spot.setId(new SpotCompositeId(1l, bus));
		spotRepository.save(spot);
		
		clientToDel = new ClientModel();
		clientToDel.setName("Junit delete client test");
		clientToDel = clientRepository.save(clientToDel);
		
		
		client = new ClientModel();
		client.setName("Junit client test");
		client = clientRepository.save(client);
	}
	

	
	@Test
	public void createClient() {
		try {
			ClientModel client = new ClientModel();
			client.setName("Junit client test");
			client = clientService.createClient(client);
			assertTrue(client.getName().equals("Junit client test"));
		} catch (Exception e) {
			fail();
		}
	}

	
	@Test
	public void updateClient() {
		try {
			ClientModel clientToUpdate = new ClientModel();
			clientToUpdate.setId(client.getId());
			clientToUpdate.setName("Updated");
			assertTrue(clientService.updateClient(clientToUpdate).getName().equals("Updated"));
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void listAll() {
		try {
			assertTrue(clientService.listAll().contains(client));
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void addLineToClient() {
		try {
			assertTrue(clientService.addLineToClient(client.getId(),bus.getId()).getBusLines().contains(bus));
			delLineToClient();
		} catch (Exception e) {
			fail();
		}
	}
	
	public void delLineToClient() {
		try {
			assertTrue(clientService.delLineToClient(client.getId(),bus.getId()));
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void deleteClient() {
		try {
			assertTrue(clientService.deleteClient(clientToDel.getId()));
		} catch (Exception e) {
			fail();
		}
	}
}
