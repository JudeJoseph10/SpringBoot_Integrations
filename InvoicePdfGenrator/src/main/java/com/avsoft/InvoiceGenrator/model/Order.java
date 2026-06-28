package com.avsoft.InvoiceGenrator.model;

import java.util.List;

public class Order {
    private int id;
    private String customerName;
    private List<Item> items;
    
	public Order(int id, String customerName, List<Item> items) {
		super();
		this.id = id;
		this.customerName = customerName;
		this.items = items;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public List<Item> getItems() {
		return items;
	}
	public void setItems(List<Item> items) {
		this.items = items;
	}

    // Getters and Setters
}