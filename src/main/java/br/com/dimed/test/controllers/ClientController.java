package br.com.dimed.test.controllers;

import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.Valid;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.dimed.test.domain.BusLineModel;
import br.com.dimed.test.domain.ClientModel;
import br.com.dimed.test.dto.ResponseModel;
import br.com.dimed.test.repositories.ClientRepository;
import br.com.dimed.test.services.ClientService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javassist.NotFoundException;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("client")
public class ClientController {

	@Autowired
	ClientRepository clientRepository;
	@Autowired
	ClientService clientService;

	@ApiOperation(value = "Listar todos os clientes")
	@ApiResponses(value = {
	        @ApiResponse(code = 200, response = ClientModel.class, message = "Array de Clientes"),
	        @ApiResponse(code = 204, response = ResponseModel.class, message = "No Content"),
	        @ApiResponse(code = 500, response = ResponseModel.class, message = "Internal Server Error")
	}
	)
	@GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> getClients() {
		try {
			return new ResponseEntity<List<ClientModel>>(clientService.listAll(), HttpStatus.OK);
		} catch (NotFoundException e) {
			return new ResponseEntity<ResponseModel>(new ResponseModel(e.getMessage()),HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<ResponseModel>(new ResponseModel(e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	@ApiOperation(value = "Atualizar os dados do cliente")
	@ApiResponses(value = {
	        @ApiResponse(code = 202, response = ClientModel.class, message = "Cliente após atualização"),
	        @ApiResponse(code = 204, response = ResponseModel.class, message = "No Content"),
	        @ApiResponse(code = 404, response = ResponseModel.class, message = "Not Found"),
	        @ApiResponse(code = 500, response = ResponseModel.class, message = "Internal Server Error")
	}
	)
	@PutMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> updateClient(@Validated @RequestBody ClientModel client) {
		try {
			return new ResponseEntity<ClientModel>(clientService.updateClient(client), HttpStatus.ACCEPTED);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<ResponseModel>(new ResponseModel(e.getMessage()),HttpStatus.NOT_FOUND);
		} catch (ValidationException e) {
			return new ResponseEntity<ResponseModel>(new ResponseModel(e.getMessage()),HttpStatus.NOT_MODIFIED);
		} catch (Exception e) {
			return new ResponseEntity<ResponseModel>(new ResponseModel(e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ApiOperation(value = "Excluir cliente")
	@ApiResponses(value = {
	        @ApiResponse(code = 200, response = ResponseModel.class, message = "Excluido com sucesso"),
	        @ApiResponse(code = 304, response = ResponseModel.class, message = "Não Alterado"),
	        @ApiResponse(code = 404, response = ResponseModel.class, message = "Not Found"),
	        @ApiResponse(code = 500, response = ResponseModel.class, message = "Internal Server Error")
	}
	)
	
	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> deleteClient( 
			@ApiParam(value = "Id do cliente que será excluído") @PathVariable("id") Long id) {
		try {
			clientService.deleteClient(id);
			return new ResponseEntity<ResponseModel>(new ResponseModel("Cliente " + id + " excluido com sucesso"),HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<ResponseModel>(new ResponseModel(e.getMessage()),HttpStatus.NOT_FOUND);
		}catch (IllegalArgumentException  e) {
			return new ResponseEntity<ResponseModel>(new ResponseModel(e.getMessage()),HttpStatus.NOT_MODIFIED);
		}catch(Exception e) {
			return new ResponseEntity<ResponseModel>(new ResponseModel(e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ApiOperation(value = "Criar novo cliente")
	@ApiResponses(value = {
	        @ApiResponse(code = 201, response = ClientModel.class, message = "Criado com sucesso"),
	        @ApiResponse(code = 422, response = ResponseModel.class, message = "Não foi possivel criar o elemento"),
	        @ApiResponse(code = 500, response = ResponseModel.class, message = "Internal Server Error")
	}
	)
	@PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> createClient(@Validated @RequestBody ClientModel client) {
		try {
			return new ResponseEntity<ClientModel>(clientService.createClient(client), HttpStatus.CREATED);
		} catch (ValidationException e) {
			return new ResponseEntity<ResponseModel>(new ResponseModel(e.getMessage()),HttpStatus.UNPROCESSABLE_ENTITY);
		} catch (Exception e) {
			return new ResponseEntity<ResponseModel>(new ResponseModel(e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ApiOperation(value = "Adicionar linha de unidade de transporte a um determinado cliente")
	@ApiResponses(value = {
	        @ApiResponse(code = 200, response = ClientModel.class, message = "Adicionado com sucesso"),
	        @ApiResponse(code = 304, response = ResponseModel.class, message = "Não Alterado"),
	        @ApiResponse(code = 404, response = ResponseModel.class, message = "Not Found"),
	        @ApiResponse(code = 500, response = ResponseModel.class, message = "Internal Server Error")
	}
	)
	@PutMapping(value = "/{id}/busline/{idBus}", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> addLineToClient(
			@ApiParam(value = "Id do cliente que receberá a nova linha") @PathVariable("id") Long id,
			@ApiParam(value = "Id da linha que será adicionada ao cliente") @PathVariable("idBus") Long idBus) {
		try {
			return new ResponseEntity<ClientModel>(clientService.addLineToClient(id, idBus), HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<ResponseModel>(new ResponseModel(e.getMessage()),HttpStatus.NOT_FOUND);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<ResponseModel>(new ResponseModel(e.getMessage()),HttpStatus.NOT_MODIFIED);
		} catch (Exception e) {
			return new ResponseEntity<ResponseModel>(new ResponseModel(e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ApiOperation(value = "Remover linha de unidade de transporte a um determinado cliente")
	@ApiResponses(value = {
	        @ApiResponse(code = 200, response = ResponseModel.class, message = "Adicionado com sucesso"),
	        @ApiResponse(code = 304, response = ResponseModel.class, message = "Não Alterado"),
	        @ApiResponse(code = 404, response = ResponseModel.class, message = "Not Found"),
	        @ApiResponse(code = 500, response = ResponseModel.class, message = "Internal Server Error")
	}
	)
	@DeleteMapping(value = "/{id}/busline/{idBus}", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> delLineToClient(
			@ApiParam(value = "Id do cliente que que detem a linha") @PathVariable("id") Long id,
			@ApiParam(value = "Id da linha que será removida do cliente") @PathVariable("idBus") Long idBus) {
		try {
			if(clientService.delLineToClient(id, idBus))
				return new ResponseEntity<ResponseModel>(new ResponseModel("Adicionado com sucesso"),HttpStatus.OK);
			else
				return new ResponseEntity<ResponseModel>(new ResponseModel("Não Alterado"),HttpStatus.NOT_MODIFIED);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<ResponseModel>(new ResponseModel(e.getMessage()),HttpStatus.NOT_FOUND);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<ResponseModel>(new ResponseModel(e.getMessage()),HttpStatus.NOT_MODIFIED);
		} catch (Exception e) {
			return new ResponseEntity<ResponseModel>(new ResponseModel(e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
