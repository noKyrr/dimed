package br.com.dimed.test.controllers;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.dimed.test.domain.BusLineModel;
import br.com.dimed.test.domain.SpotModel;
import br.com.dimed.test.dto.BusLineSearchDto;
import br.com.dimed.test.dto.ResponseModel;
import br.com.dimed.test.dto.SpotSearchDto;
import br.com.dimed.test.services.BusLineService;
import br.com.dimed.test.services.SpotService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javassist.NotFoundException;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("busline")
public class BusLineController {

	@Autowired
	BusLineService busLineService;
	
	@Autowired
	SpotService spotService;
	
	@ApiOperation(value = "Listar todas as unidades de transporte")
	@ApiResponses(value = {
	        @ApiResponse(code = 200, response = BusLineModel.class, message = "Array de unidades de transporte"),
	        @ApiResponse(code = 204, response = ResponseModel.class, message = "No Content"),
	        @ApiResponse(code = 500, response = ResponseModel.class, message = "Internal Server Error")
	}
	)
	@GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> getBusLines() {
		try {
			return new ResponseEntity<List<BusLineModel>>(busLineService.listAll(), HttpStatus.OK);
		} catch (NotFoundException e) {
			return new ResponseEntity<ResponseModel>(new ResponseModel(e.getMessage()),HttpStatus.NO_CONTENT);
		}catch(Exception e) {
			return new ResponseEntity<ResponseModel>(new ResponseModel(e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ApiOperation(value = "Listar todas as unidades de transporte que o nome contenha o filtro enviado")
	@ApiResponses(value = {
	        @ApiResponse(code = 200, response = BusLineModel.class, message = "Array de unidades de transporte"),
	        @ApiResponse(code = 204, response = ResponseModel.class, message = "No Content"),
	        @ApiResponse(code = 500, response = ResponseModel.class, message = "Internal Server Error")
	}
	)
	@GetMapping(value = "/{filter}", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> getBusLinesWithFilter(@PathVariable("filter") String filter) {
		try {
			return new ResponseEntity<List<BusLineModel>>(busLineService.listAll(filter), HttpStatus.OK);
		} catch (NotFoundException e) {
			return new ResponseEntity<ResponseModel>(new ResponseModel(e.getMessage()),HttpStatus.NO_CONTENT);
		}catch(Exception e) {
			return new ResponseEntity<ResponseModel>(new ResponseModel(e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	

	@ApiOperation(value = "Buscar pontos na tragetória de uma linha especifica, dado um ponto base e o raio em metros")
	@ApiResponses(value = {
	        @ApiResponse(code = 200, response = BusLineModel.class, message = "Array de pontos de localização"),
	        @ApiResponse(code = 204, response = ResponseModel.class, message = "No Content"),
	        @ApiResponse(code = 500, response = ResponseModel.class, message = "Internal Server Error")
	}
	)
	@PostMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> getSpotsOfBusline(@Validated @RequestBody BusLineSearchDto lineDto) {
		try {
			return new ResponseEntity<List<SpotModel>>(spotService.findSpotsByBusline(lineDto), HttpStatus.OK);
		} catch (NotFoundException e) {
			return new ResponseEntity<ResponseModel>(new ResponseModel(e.getMessage()),HttpStatus.NO_CONTENT);
		}catch(Exception e) {
			return new ResponseEntity<ResponseModel>(new ResponseModel(e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ApiOperation(value = "Buscar linhas de unidades de transporte dado um ponto base e o raio em metros")
	@ApiResponses(value = {
	        @ApiResponse(code = 200, response = BusLineModel.class, message = "Array de unidades de transporte"),
	        @ApiResponse(code = 204, response = ResponseModel.class, message = "No Content"),
	        @ApiResponse(code = 500, response = ResponseModel.class, message = "Internal Server Error")
	}
	)
	@PostMapping(value = "/search/spot", produces = MediaType.APPLICATION_JSON_VALUE)
	
	public @ResponseBody ResponseEntity<?> getBusLinesByLocation(@Validated @RequestBody SpotSearchDto lineDto) {
		try {
			return new ResponseEntity<List<BusLineModel>>(busLineService.findBuslinesBySpot(lineDto), HttpStatus.OK);
		} catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	
}
