package com.util;

import java.math.BigDecimal;
import java.time.LocalDate;

public class RentalAgreement
{
    private String toolCode;
    private String toolType;
    private String toolBrand;
    private double dailyCharge;
    private int rentalDays;
    private LocalDate checkoutDate;
    private LocalDate dueDate;
    private int chargeDays;
    private double preDiscountCharge;
    private double discountPercent;
    private BigDecimal discountAmount;
    private BigDecimal finalCharge;

    public RentalAgreement() {}

    public String getToolCode()
    {
        return toolCode;
    }

    public void setToolCode(String toolCode)
    {
        this.toolCode = toolCode;
    }

    public String getToolType()
    {
        return toolType;
    }

    public void setToolType(String toolType)
    {
        this.toolType = toolType;
    }

    public String getToolBrand()
    {
        return toolBrand;
    }

    public void setToolBrand(String toolBrand)
    {
        this.toolBrand = toolBrand;
    }

    public double getDailyCharge()
    {
        return dailyCharge;
    }

    public void setDailyCharge(double dailyCharge)
    {
        this.dailyCharge = dailyCharge;
    }

    public int getRentalDays()
    {
        return rentalDays;
    }

    public void setRentalDays(int rentalDays)
    {
        this.rentalDays = rentalDays;
    }

    public LocalDate getCheckoutDate()
    {
        return checkoutDate;
    }

    public void setCheckoutDate(LocalDate checkoutDate)
    {
        this.checkoutDate = checkoutDate;
    }

    public LocalDate getDueDate()
    {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate)
    {
        this.dueDate = dueDate;
    }

    public int getChargeDays() 
    {
        return chargeDays;
    }

    public void setChargeDays(int chargeDays)
    {
        this.chargeDays = chargeDays;
    }

    public double getPreDiscountCharge()
    {
        return preDiscountCharge;
    }

    public void setPreDiscountCharge(double preDiscountCharge)
    {
        this.preDiscountCharge = preDiscountCharge;
    }

    public double getDiscountPercent()
    {
        return discountPercent;
    }

    public void setDiscountPercent(double discountPercent)
    {
        this.discountPercent = discountPercent;
    }

    public BigDecimal getDiscountAmount()
    {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount)
    {
        this.discountAmount = discountAmount;
    }

    public BigDecimal getFinalCharge()
    {
        return finalCharge;
    }

    public void setFinalCharge(BigDecimal finalCharge)
    {
        this.finalCharge = finalCharge;
    }
}
