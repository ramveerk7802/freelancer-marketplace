package com.rvcode.freelancerMarketplace.escrow.payment;


import com.razorpay.RazorpayException;
import com.rvcode.freelancerMarketplace.escrow.payment.dto.PaymentVerificationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class RazorpayController {

    private final RazorpayService razorpayService;

    @PostMapping("/verify")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<String> verifyPayment(@RequestBody PaymentVerificationRequest request) throws RazorpayException {
        boolean success = razorpayService.verifyPayment(request);
        if(success){
            return ResponseEntity.ok("Payment verified successfully. Project is now in progress.");
        }else {
            return ResponseEntity.badRequest().body("Payment verification failed");
        }
    }
}
