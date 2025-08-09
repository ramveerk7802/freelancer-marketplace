package com.rvcode.freelancerMarketplace.escrow.payment;

import com.rvcode.freelancerMarketplace.escrow.payment.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {
}
