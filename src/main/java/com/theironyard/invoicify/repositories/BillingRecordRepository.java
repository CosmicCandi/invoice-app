package com.theironyard.invoicify.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.theironyard.invoicify.models.BillingRecord;

public interface BillingRecordRepository extends JpaRepository<BillingRecord, Long> {
	
	List<BillingRecord> findByLineItemIsNullAndClientId(long clientId);

	List<BillingRecord> findByIdIn(long[] recordIds);

}
