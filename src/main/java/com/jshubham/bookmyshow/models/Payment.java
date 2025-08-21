package com.jshubham.bookmyshow.models;

import com.jshubham.bookmyshow.models.enums.PaymentMode;
import com.jshubham.bookmyshow.models.enums.PaymentProvider;
import com.jshubham.bookmyshow.models.enums.PaymentStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Payment extends BaseModel{
    private String referenceNumber; // unique identifier of a payment returned be third-party payment gateway
    private double amount;

    @Enumerated(EnumType.ORDINAL)
    private PaymentProvider paymentProvider;

    @Enumerated(EnumType.ORDINAL)
    private PaymentMode paymentMode;

    @Enumerated(EnumType.ORDINAL)
    private PaymentStatus paymentStatus;
}
