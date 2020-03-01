package com.busyqa.crm.repo;

import javax.transaction.Transactional;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import com.busyqa.crm.pojo.payment.PaymentPlanWeek;

@Repository
@Transactional
public interface PaymentPlanWeekRepo extends PagingAndSortingRepository<PaymentPlanWeek, Long> {

}

