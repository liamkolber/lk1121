package com.checkout;

import java.util.Date;

import com.util.RentalAgreement;
import com.util.Tool;

public class CheckoutHandler {

    private String exceptionMessage;

    public CheckoutHandler() {}

    public RentalAgreement completeCheckout(String toolCode, int dayCount, double discountPercent, Date checkoutDate) throws Exception
    {
        // Create our object
        RentalAgreement rentalAgreement = new RentalAgreement();
        Tool tool = new Tool(toolCode);

        rentalAgreement.setToolCode(toolCode);
        rentalAgreement.setChargeDays(dayCount);
        rentalAgreement.setDiscountPercent(discountPercent);
        rentalAgreement.setCheckoutDate(checkoutDate);

        // Check discount percent and day count for valid values
        if (dayCount <  1)
        {
            exceptionMessage = "Invalid number of days. Rental day count must be 1 or more days.";
            throw new Exception(exceptionMessage);
        }
        if (discountPercent > 100 || discountPercent < 0)
        {
            exceptionMessage = "Invalid discount percentage. Discount percentage must be between 0-100";
            throw new Exception(exceptionMessage);
        }



        return rentalAgreement;
    }
}
