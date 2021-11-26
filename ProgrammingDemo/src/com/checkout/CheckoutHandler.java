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

    /**
     * Complete checkout process by filling out Rental Agreement
     * @return
     * @throws Exception
     */
    public RentalAgreement completeCheckout() throws Exception
    {
        // Check discount percent and day count for valid values
        if (this.dayCount <  1)
        {
            exceptionMessage = "Invalid number of days. Rental day count must be 1 or more days.";
            throw new Exception(exceptionMessage);
        }
        if (this.discountPercent > 100 || this.discountPercent < 0)
        {
            exceptionMessage = "Invalid discount percentage. Discount percentage must be between 0-100";
            throw new Exception(exceptionMessage);
        }

        // Create our agreement and tool objects
        RentalAgreement rentalAgreement = new RentalAgreement();
        Tool tool = new Tool(toolCode);

        String toolBrand = tool.getBrand();
        String toolType = tool.getType();
        double toolCharge = tool.getCharge();
        LocalDate dueDate = checkoutDate.plusDays(dayCount);
        int totalChargeableDays = totalChargeableDays(tool);
        double preDiscountCharge = totalChargeableDays * toolCharge;
        double discountAmount = discountPercent * preDiscountCharge;
        double finalCharge = preDiscountCharge - discountAmount;

        rentalAgreement.setToolCode(this.toolCode);
        rentalAgreement.setToolType(toolType);
        rentalAgreement.setToolBrand(toolBrand);
        rentalAgreement.setRentalDays(this.dayCount);
        rentalAgreement.setCheckoutDate(this.checkoutDate);
        rentalAgreement.setDueDate(dueDate);
        rentalAgreement.setDailyCharge(toolCharge);
        rentalAgreement.setChargeDays(totalChargeableDays);
        rentalAgreement.setPreDiscountCharge(preDiscountCharge);
        rentalAgreement.setDiscountPercent(this.discountPercent);
        rentalAgreement.setDiscountAmount(discountAmount);
        rentalAgreement.setFinalCharge(finalCharge);
        
        return rentalAgreement;
    }

    /**
     * Counts total chargeable days. Starts with total day count and subtracts weekends and/or holidays.
     * @param tool
     * @return
     */
    private int totalChargeableDays(Tool tool)
    {
        int chargeableDays = this.dayCount;
        LocalDate currentDay = this.checkoutDate;

        for (int i = 1; i <= this.dayCount; i++)
        {
            switch (tool.getType())
            {
                // Don't charge on holidays
                case Tool.typeLadder:
                    if (isHoliday(currentDay)) chargeableDays--;

                // Don't charge on weekends
                case Tool.typeChainsaw:
                    if (isWeekendDay(currentDay)) chargeableDays--;

                // Don't charge on holidays or weekend
                case Tool.typeJackhammer:
                    if (isHoliday(currentDay) || isWeekendDay(currentDay)) chargeableDays--;
            }
            currentDay.plusDays(1);
        }
        return chargeableDays;
    }

    /**
     * Checks if given day is a weekend day.
     * @param day
     * @return
     */
    private boolean isWeekendDay(LocalDate day)
    {
        if (day.getDayOfWeek() == DayOfWeek.SATURDAY || day.getDayOfWeek() == DayOfWeek.SUNDAY) return true;

        return false;
    }

    /**
     * Checks if given day is a holiday.
     * @param day
     * @return
     */
    private boolean isHoliday(LocalDate day)
    {
        // Check if checkout period covers Independence Day
        if (day == holidayJul || day == holidaySep) return true;

        return false;
    }

    /**
     * Gets Independence Day date for year of rental.
     * @param year
     * @return
     */
    private LocalDate getIndependenceDay(int year)
    {
        return LocalDate.of(year, 7, 4);
    }

    /**
     * Gets Labor Day date for year of rental.
     * @param year
     * @return
     */
    private LocalDate getLaborDay(int year)
    {
        return LocalDate.of(year, 9, 1).with(firstInMonth(DayOfWeek.MONDAY));
    }
}
