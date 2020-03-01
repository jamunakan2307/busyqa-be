package com.busyqa.crm.repo;

import javax.transaction.Transactional;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import com.busyqa.crm.pojo.payment.PaymentRecord;

@Repository
@Transactional
public interface PaymentRecordRepo extends PagingAndSortingRepository<PaymentRecord, Long> {

}

