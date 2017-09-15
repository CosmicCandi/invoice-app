package com.theironyard.invoicify.models;

import java.sql.Date;
import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public abstract class BillingRecord {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@ManyToOne
	private User createdBy;
	
	private Date createdOn;
	
	private String description;
	
	@OneToOne(mappedBy="billingRecord")
	private InvoiceLineItem lineItem;
	
	@ManyToOne
	private Company client;
	
	public BillingRecord() {
		long now = Calendar.getInstance().getTimeInMillis();
		createdOn = new Date(now);
	}
	
	public BillingRecord(String description, User createdBy, Company client) {
		this();
		this.createdBy = createdBy;
		this.description = description;
		this.client = client;
	}
	
	public abstract double getTotal();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public InvoiceLineItem getLineItem() {
		return lineItem;
	}

	public void setLineItem(InvoiceLineItem lineItem) {
		this.lineItem = lineItem;
	}

	public Company getClient() {
		return client;
	}

	public void setClient(Company client) {
		this.client = client;
	}
	
}
