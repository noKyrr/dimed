package br.com.dimed.test.services;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dimed.test.domain.BusLineModel;
import br.com.dimed.test.domain.ClientModel;
import br.com.dimed.test.repositories.BusLineRepository;
import br.com.dimed.test.repositories.ClientRepository;
import javassist.NotFoundException;

@Service
public class ClientService {

	@Autowired
	ClientRepository clientRepository;
	@Autowired
	BusLineService busLineService;
	@Autowired
	BusLineRepository busLineRepository;

	public List<ClientModel> listAll() throws Exception {
		List<ClientModel> clientList = clientRepository.findAll();
		if (clientList == null || clientList.isEmpty())
			throw new NotFoundException("Lista não encontrada");
		return clientList;
	}

	public ClientModel updateClient(ClientModel client) throws Exception {
		ClientModel dbCLient = clientRepository.findById(client.getId()).get();
		if (dbCLient.getName() == client.getName())
			throw new ValidationException();
		dbCLient.setName(client.getName());
		return clientRepository.save(dbCLient);
	}

	public ClientModel createClient(ClientModel client) throws Exception {
		if (client.getId() != null) {
			if (clientRepository.findById(client.getId()).isPresent())
				throw new ValidationException();
			client.setId(null);
		}
		return clientRepository.save(client);
	}

	public boolean deleteClient(Long id) throws Exception {
		Optional<ClientModel> dbClient = clientRepository.findById(id);
		clientRepository.delete(dbClient.get());
		return true;
	}

	public ClientModel addLineToClient(Long id, Long idBus) throws Exception {
		Optional<ClientModel> dbClient = clientRepository.findById(id);
		Optional<BusLineModel> dbBusLine = busLineRepository.findById(idBus);
		if(dbClient.get().getBusLines() == null)
			dbClient.get().setBusLines(new HashSet<BusLineModel>());
		if(!dbClient.get().getBusLines().add(dbBusLine.get()))
			throw new ValidationException();
		
		return clientRepository.save(dbClient.get());
	}

	public boolean delLineToClient(Long id, Long idBus) throws Exception {
		Optional<ClientModel> dbClient = clientRepository.findById(id);
		Optional<BusLineModel> dbBusLine = busLineRepository.findById(idBus);
		if(!dbClient.get().getBusLines().contains(dbBusLine.get()))
			return false;
		dbClient.get().getBusLines().remove(dbBusLine.get());
		clientRepository.save(dbClient.get());
		return true;
	}

}
