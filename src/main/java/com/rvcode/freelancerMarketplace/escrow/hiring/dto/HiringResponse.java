package com.rvcode.freelancerMarketplace.escrow.hiring.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


// Response sent to frontend to initiate payment
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HiringResponse{
    String razorpayOrderId;
    BigDecimal amount;
    String currency;
    String razorpayKeyId;
}
