package com.avsoft.InvoiceGenrator.service;

import java.io.IOException;

import com.avsoft.InvoiceGenrator.model.Order;

public interface InvoiceGenratorService {

	
	public byte[] genrateInvoice(Order order) throws IOException;
	
	
	
}
