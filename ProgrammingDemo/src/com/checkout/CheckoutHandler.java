package com.checkout;

import java.time.DayOfWeek;
import java.time.LocalDate;
import static java.time.temporal.TemporalAdjusters.firstInMonth;

import com.util.RentalAgreement;
import com.util.Tool;

public class CheckoutHandler {

    private String exceptionMessage;

    private LocalDate holidayJul;
    private LocalDate holidaySep;

    private String toolCode;
    private int dayCount;
    private double discountPercent;
    LocalDate checkoutDate;

    public CheckoutHandler(String toolCode, int dayCount, double discountPercent, LocalDate checkoutDate)
    {
        this.toolCode = toolCode;
        this.dayCount = dayCount;
        this.discountPercent = discountPercent;
        this.checkoutDate = checkoutDate;

        this.holidayJul = getIndependenceDay(checkoutDate.getYear());
        this.holidaySep = getLaborDay(checkoutDate.getYear());
    }

    public RentalAgreement completeCheckout() throws Exception
    {
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

        // Create our agreement and tool objects
        RentalAgreement rentalAgreement = new RentalAgreement();
        Tool tool = new Tool(toolCode);

        rentalAgreement.setToolCode(tool.getCode());
        rentalAgreement.setToolBrand(tool.getBrand());
        rentalAgreement.setToolType(tool.getType());
        rentalAgreement.setDailyCharge(tool.getCharge());
        rentalAgreement.setChargeDays(dayCount);
        rentalAgreement.setDiscountPercent(discountPercent);
        rentalAgreement.setCheckoutDate(checkoutDate);

        
        return rentalAgreement;
    }

    private int totalDaysCalculator(int chargeableDayCount, LocalDate checkoutDate)
    {
        LocalDate returnDate = checkoutDate.plusDays(dayCount);
        
        // Check if checkout period covers Independence Day
        if (checkoutDate.isBefore(holidayJul) && returnDate.isAfter(holidayJul))
        {
            chargeableDayCount--;
        }
        // Check if checkout period covers Labor Day
        if (checkoutDate.isBefore(holidaySep) && returnDate.isAfter(holidaySep))
        {
            chargeableDayCount--;
        }

        return dayCount;
    }

    private LocalDate getIndependenceDay(int year)
    {
        return LocalDate.of(year, 7, 4);
    }

    private LocalDate getLaborDay(int year)
    {
        return LocalDate.of(year, 9, 1).with(firstInMonth(DayOfWeek.MONDAY));
    }
}
