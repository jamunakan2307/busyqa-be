package com.busyqa.crm.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.busyqa.crm.model.PaymentHistroy;
import com.busyqa.crm.model.User;

public interface PaymentHistoryRespository extends JpaRepository<PaymentHistroy, Long> {

	List<PaymentHistroy> findByuserOrderByScheduledPaymentDateAsc(User user);

	PaymentHistroy save(PaymentHistroy paymentHistroy);

	void deleteById(int id);
}
