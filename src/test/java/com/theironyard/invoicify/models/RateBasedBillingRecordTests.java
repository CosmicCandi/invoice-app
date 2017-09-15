package com.theironyard.invoicify.models;

import static org.assertj.core.api.Assertions.*;

import java.sql.Date;

import org.junit.Before;
import org.junit.Test;
import org.meanbean.test.BeanTester;
import org.meanbean.test.Configuration;
import org.meanbean.test.ConfigurationBuilder;

public class RateBasedBillingRecordTests {

	private RateBasedBillingRecord record;
	
	@Before
	public void setup() {
		record = new RateBasedBillingRecord();
	}
	
	@Test
	public void test_getters_and_setters() {
		Configuration configuration = new ConfigurationBuilder()
			.ignoreProperty("createdOn")
			.ignoreProperty("createdBy")
			.ignoreProperty("client")
			.ignoreProperty("lineItem")
			.build();
		new BeanTester().testBean(RateBasedBillingRecord.class, configuration);
	}
	
	@Test
	public void test_createdOn_is_not_null_by_default() {
		Date actual = record.getCreatedOn();
		
		assertThat(actual).isNotNull();
	}
	
	@Test
	public void test_createdOn_gets_and_sets() {
		Date date = Date.valueOf("2017-02-02");
		record.setCreatedOn(date);
		
		Date actual = record.getCreatedOn();
		
		assertThat(actual).isEqualTo(date);
	}
	
	@Test
	public void test_createdBy_is_null_by_default() {
		User actual = record.getCreatedBy();
		
		assertThat(actual).isNull();
	}
	
	@Test
	public void test_createdBy_gets_and_sets() {
		User user = new User();
		record.setCreatedBy(user);
		
		User actual = record.getCreatedBy();
		
		assertThat(actual).isEqualTo(user);
	}
	
	@Test
	public void test_lineItem_is_null_by_default() {
		InvoiceLineItem actual = record.getLineItem();
		
		assertThat(actual).isNull();
	}
	
	@Test
	public void test_lineItem_gets_and_sets() {
		InvoiceLineItem lineItem = new InvoiceLineItem();
		record.setLineItem(lineItem);
		
		InvoiceLineItem actual = record.getLineItem();
		
		assertThat(actual).isEqualTo(lineItem);
	}
	
	@Test
	public void test_client_is_null_by_default() {
		Company actual = record.getClient();
		
		assertThat(actual).isNull();
	}
	
	@Test
	public void test_client_gets_and_sets() {
		Company client = new Company();
		record.setClient(client);
		
		Company actual = record.getClient();
		
		assertThat(actual).isEqualTo(client);
	}
	
	@Test
	public void test_getTotal_returns_product_of_rate_and_quantity() {
		double rate = Math.random();
		double quantity = Math.random();
		record.setRate(rate);
		record.setQuantity(quantity);
		
		double actual = record.getTotal();
		
		assertThat(actual).isCloseTo(rate * quantity, within(0.00001));
	}
	
}
