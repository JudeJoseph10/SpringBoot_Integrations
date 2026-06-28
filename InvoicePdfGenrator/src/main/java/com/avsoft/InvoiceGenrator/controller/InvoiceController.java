package com.avsoft.InvoiceGenrator.controller;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.ServerRequest.Headers;

import com.avsoft.InvoiceGenrator.model.Item;
import com.avsoft.InvoiceGenrator.model.Order;
import com.avsoft.InvoiceGenrator.service.InvoiceGenratorService;

@RestController
public class InvoiceController {

	@Autowired
	InvoiceGenratorService genratorService;
	
	
	@GetMapping("getInvoice/{orderId}")
	public ResponseEntity<byte[]> getInvoice(@PathVariable("orderId") int orderId) throws IOException {

		Item i1 =new Item("Producet 1", 10, 1001);
		Item i2 =new Item("Producet 2", 10, 1020);
		Item i3 =new Item("Producet 3", 10, 1030);
		Item i4 =new Item("Producet 4", 10, 1040);
		Item i5 =new Item("Producet 5", 10, 1050);
	
		
		Order order =new Order(orderId, "john wick", Arrays.asList(i1,i2,i3,i4,i5));
	byte [] bytes= 	genratorService.genrateInvoice(order);
		
	HttpHeaders headers =new HttpHeaders();
	 headers.add(HttpHeaders.CONTENT_TYPE, "application/pdf");
	
	
	return ResponseEntity.ok().headers(headers).body(bytes);
		
	}

}
