package test;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertThrows;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import com.checkout.CheckoutHandler;
import com.util.RentalAgreement;

public class CheckoutHandlerTest {

    @Test
    public void testScenario1()
    {
        System.out.println("|---------- Test Scenario 01 ----------|");
        String toolCode = "JAKR";
        LocalDate checkoutDate = LocalDate.of(2015, 9, 3);
        int rentalDays = 5;
        double discount = 101;
        CheckoutHandler checkoutHandler = new CheckoutHandler(toolCode, rentalDays, discount, checkoutDate);

        Exception thrown = assertThrows(Exception.class, () -> {
                checkoutHandler.completeCheckout();
        });

        assertTrue(thrown.getMessage().contains("Invalid discount percentage."));
        System.out.println(thrown.getMessage());
        System.out.println("|--------------------------------------|\n");
    }

    @Test
    public void testScenario2()
    {
        System.out.println("|---------- Test Scenario 02 ----------|");
        String toolCode = "LADW";
        LocalDate checkoutDate = LocalDate.of(2020, 7, 2);
        int rentalDays = 3;
        double discount = 10;
        BigDecimal expectedFinalCharge = new BigDecimal(3.58).setScale(2, RoundingMode.HALF_UP);
        CheckoutHandler checkoutHandler = new CheckoutHandler(toolCode, rentalDays, discount, checkoutDate);
        RentalAgreement rentalAgreement = new RentalAgreement();
        try
        {
            rentalAgreement = checkoutHandler.completeCheckout();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        assertTrue(rentalAgreement.getFinalCharge().equals(expectedFinalCharge));
    }

    @Test
    public void testScenario3()
    {
        System.out.println("|---------- Test Scenario 03 ----------|");
        String toolCode = "CHNS";
        LocalDate checkoutDate = LocalDate.of(2015, 7, 2);
        int rentalDays = 5;
        double discount = 25;
        BigDecimal expectedFinalCharge = new BigDecimal(3.35).setScale(2, RoundingMode.HALF_UP);
        CheckoutHandler checkoutHandler = new CheckoutHandler(toolCode, rentalDays, discount, checkoutDate);
        RentalAgreement rentalAgreement = new RentalAgreement();
        try
        {
            rentalAgreement = checkoutHandler.completeCheckout();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        assertTrue(rentalAgreement.getFinalCharge().equals(expectedFinalCharge));
    }

    @Test
    public void testScenario4()
    {
        System.out.println("|---------- Test Scenario 04 ----------|");
        String toolCode = "JAKD";
        LocalDate checkoutDate = LocalDate.of(2015, 9, 3);
        int rentalDays = 6;
        double discount = 0;
        BigDecimal expectedFinalCharge = new BigDecimal(8.97).setScale(2, RoundingMode.HALF_UP);
        CheckoutHandler checkoutHandler = new CheckoutHandler(toolCode, rentalDays, discount, checkoutDate);
        RentalAgreement rentalAgreement = new RentalAgreement();
        try
        {
            rentalAgreement = checkoutHandler.completeCheckout();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        assertTrue(rentalAgreement.getFinalCharge().equals(expectedFinalCharge));
    }

    @Test
    public void testScenario5()
    {
        System.out.println("|---------- Test Scenario 05 ----------|");
        String toolCode = "JAKR";
        LocalDate checkoutDate = LocalDate.of(2015, 7, 2);
        int rentalDays = 9;
        double discount = 0;
        BigDecimal expectedFinalCharge = new BigDecimal(17.94).setScale(2, RoundingMode.HALF_UP);
        CheckoutHandler checkoutHandler = new CheckoutHandler(toolCode, rentalDays, discount, checkoutDate);
        RentalAgreement rentalAgreement = new RentalAgreement();
        try
        {
            rentalAgreement = checkoutHandler.completeCheckout();
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        assertTrue(rentalAgreement.getFinalCharge().equals(expectedFinalCharge));
    }

    @Test
    public void testScenario6()
    {
        System.out.println("|---------- Test Scenario 06 ----------|");
        String toolCode = "JAKR";
        LocalDate checkoutDate = LocalDate.of(2020, 7, 2);
        int rentalDays = 4;
        double discount = 50;
        BigDecimal expectedFinalCharge = new BigDecimal(1.49).setScale(2, RoundingMode.HALF_UP);
        CheckoutHandler checkoutHandler = new CheckoutHandler(toolCode, rentalDays, discount, checkoutDate);
        RentalAgreement rentalAgreement = new RentalAgreement();
        try
        {
            rentalAgreement = checkoutHandler.completeCheckout();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        assertTrue(rentalAgreement.getFinalCharge().equals(expectedFinalCharge));
    }
}
