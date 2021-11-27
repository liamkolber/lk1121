package com.checkout;

import static java.time.temporal.TemporalAdjusters.firstInMonth;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;

import com.util.RentalAgreement;
import com.util.Tool;

public class CheckoutHandler {

    private String exceptionMessage;

    private LocalDate holidayJul;
    private LocalDate holidaySep;

    private String toolCode;
    private int dayCount;
    private double discountPercent;
    private LocalDate checkoutDate;
    private LocalDate dueDate;

    public CheckoutHandler(String toolCode, int dayCount, double discountPercent, LocalDate checkoutDate)
    {
        this.toolCode = toolCode;
        this.dayCount = dayCount;
        this.discountPercent = discountPercent;
        this.checkoutDate = checkoutDate;
        this.dueDate = checkoutDate.plusDays(dayCount);

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
        int totalChargeableDays = totalChargeableDays(tool);
        double preDiscountCharge = totalChargeableDays * toolCharge;
        BigDecimal discountAmount = new BigDecimal((discountPercent/100.0) * preDiscountCharge).setScale(2, RoundingMode.HALF_UP);
        BigDecimal finalCharge = new BigDecimal(preDiscountCharge - discountAmount.doubleValue()).setScale(2, RoundingMode.HALF_UP);

        // Setting all values
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

        outputAgreement(rentalAgreement);
        
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
                    break;

                // Don't charge on weekends
                case Tool.typeChainsaw:
                    if (isWeekendDay(currentDay)) chargeableDays--;
                    break;

                // Don't charge on holidays or weekend
                case Tool.typeJackhammer:
                    if (isHoliday(currentDay) && isWeekendDay(currentDay))
                    {
                        // Independence day falls on Saturday, so recognize on Friday before
                        if (currentDay.getDayOfWeek().equals(DayOfWeek.SATURDAY))
                        {
                            // Checking if checkout day was on or before Friday
                            if (this.checkoutDate.isEqual(currentDay.minusDays(1)) || this.checkoutDate.isBefore(currentDay.minusDays(1))) chargeableDays--;
                        }
                        // Independence day falls on Sunday, so recognize on Monday after
                        else if (currentDay.getDayOfWeek().equals(DayOfWeek.SUNDAY))
                        {
                            // Checking if checkout day is after Monday
                            if (this.dueDate.isAfter(currentDay.plusDays(1))) chargeableDays--;
                        }
                    }
                    if (isHoliday(currentDay) || isWeekendDay(currentDay)) chargeableDays--;
                    break;
            }
            currentDay = currentDay.plusDays(1);
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
        if (day.isEqual(holidayJul) || day.isEqual(holidaySep)) return true;

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

    /**
     * Prints rental agreement with proper formatting for values that need it
     * @param rentalAgreement
     */
    public void outputAgreement(RentalAgreement rentalAgreement){
        System.out.println("|---------- Rental Agreement ----------|");
        System.out.println("Tool Code: " + rentalAgreement.getToolCode());
        System.out.println("Tool Type: " + rentalAgreement.getToolType());
        System.out.println("Tool Brand: " + rentalAgreement.getToolBrand());
        System.out.println("Rental Days: " + rentalAgreement.getRentalDays());
        System.out.println("Checkout Date: " + rentalAgreement.getCheckoutDate().format(DateTimeFormatter.ofPattern("MM/dd/yy")));
        System.out.println("Due Date: " + rentalAgreement.getDueDate().format(DateTimeFormatter.ofPattern("MM/dd/yy")));
        System.out.println("Daily Rental Charge: " + NumberFormat.getCurrencyInstance().format(rentalAgreement.getDailyCharge()));
        System.out.println("Charge Days: " + rentalAgreement.getChargeDays());
        System.out.println("Pre-discount Charge: " + NumberFormat.getCurrencyInstance().format(rentalAgreement.getPreDiscountCharge()));
        System.out.println("Discount: " + rentalAgreement.getDiscountPercent() + "%");
        System.out.println("Discount Amount: " + NumberFormat.getCurrencyInstance().format(rentalAgreement.getDiscountAmount()));
        System.out.println("Final Charge: " + NumberFormat.getCurrencyInstance().format(rentalAgreement.getFinalCharge()));
        System.out.println("|--------------------------------------|\n");
    }
}
