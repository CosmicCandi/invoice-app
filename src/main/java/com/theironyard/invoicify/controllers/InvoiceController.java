package com.theironyard.invoicify.controllers;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.theironyard.invoicify.models.BillingRecord;
import com.theironyard.invoicify.models.Invoice;
import com.theironyard.invoicify.models.InvoiceLineItem;
import com.theironyard.invoicify.models.User;
import com.theironyard.invoicify.repositories.BillingRecordRepository;
import com.theironyard.invoicify.repositories.CompanyRepository;
import com.theironyard.invoicify.repositories.InvoiceRepository;

@Controller
@RequestMapping("/invoices")
public class InvoiceController {

	private CompanyRepository companyRepository;
	private BillingRecordRepository billingRecordRepository;
	private InvoiceRepository invoiceRepository;
	
	public InvoiceController(CompanyRepository companyRepository, BillingRecordRepository billingRecordRepository, InvoiceRepository invoiceRepository) {
		this.companyRepository = companyRepository;
		this.billingRecordRepository = billingRecordRepository;
		this.invoiceRepository = invoiceRepository;
	}
	
	@GetMapping("")
	public ModelAndView list(Authentication auth) {
		List<Invoice> invoices = invoiceRepository.findAll();
		User user = (User) auth.getPrincipal();
		ModelAndView mv = new ModelAndView("invoices/list");
		mv.addObject("user", user);
		mv.addObject("invoices", invoices);
		mv.addObject("showtable", invoices.size() > 0);
		return mv;
	}
	
	@GetMapping("/new")
	public ModelAndView step1() {
		ModelAndView mv = new ModelAndView("invoices/step1");
		mv.addObject("companies", companyRepository.findAll());
		
		return mv;
	}
	
	@PostMapping("/new")
	public ModelAndView step2(long clientId) {
		ModelAndView mv = new ModelAndView("invoices/step2");
		mv.addObject("clientId", clientId);
		mv.addObject("records", billingRecordRepository.findByLineItemIsNullAndClientId(clientId));
		return mv;
	}
	
	@PostMapping("create")
	public String createInvoice(Invoice invoice, long clientId, long[] recordIds, Authentication auth) {
		User creator = (User) auth.getPrincipal();
		List<BillingRecord> records = billingRecordRepository.findByIdIn(recordIds);
		long nowish = Calendar.getInstance().getTimeInMillis();
		Date now = new Date(nowish);
		
		List<InvoiceLineItem> items = new ArrayList<InvoiceLineItem>();
		for (BillingRecord record : records) {
			InvoiceLineItem lineItem = new InvoiceLineItem();
			lineItem.setBillingRecord(record);
			lineItem.setCreatedBy(creator);
			lineItem.setCreatedOn(now);
			lineItem.setInvoice(invoice);
			items.add(lineItem);
		}
		
		invoice.setLineItems(items);
		invoice.setCompany(companyRepository.findOne(clientId));
		invoice.setCreatedBy(creator);
		invoice.setCreatedOn(now);
		invoice.setInvoiceNumber(invoice.getInvoiceNumber());
		invoiceRepository.save(invoice);
		
		return "redirect:/invoices";
	}	
	
}

















