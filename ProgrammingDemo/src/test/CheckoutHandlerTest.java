package test;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertThrows;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.checkout.CheckoutHandler;
import com.util.RentalAgreement;

public class CheckoutHandlerTest {

    @Test
    public void testScenario1()
    {
        String toolCode = "JAKR";
        LocalDate checkoutDate = LocalDate.of(2015, 9, 3);
        int rentalDays = 5;
        double discount = 101;
        CheckoutHandler checkoutHandler = new CheckoutHandler(toolCode, rentalDays, discount, checkoutDate);

        assertThrows(Exception.class, () -> {
            checkoutHandler.completeCheckout();
        });

    }

    @Test
    public void testScenario2()
    {
        String toolCode = "LADW";
        LocalDate checkoutDate = LocalDate.of(2020, 7, 2);
        int rentalDays = 3;
        double discount = 10;
        BigDecimal expectedFinalCharge = new BigDecimal("5.37");
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

        assertTrue(rentalAgreement.getFinalCharge().toString().equals(expectedFinalCharge.toString()));
    }

    @Test
    public void testScenario3()
    {
        String toolCode = "CHNS";
        LocalDate checkoutDate = LocalDate.of(2015, 7, 2);
        int rentalDays = 5;
        double discount = 25;
        BigDecimal expectedFinalCharge = new BigDecimal("5.59");
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

        assertTrue(rentalAgreement.getFinalCharge().toString().equals(expectedFinalCharge.toString()));
    }

    @Test
    public void testScenario4()
    {
        String toolCode = "JAKD";
        LocalDate checkoutDate = LocalDate.of(2015, 9, 3);
        int rentalDays = 6;
        double discount = 0;
        BigDecimal expectedFinalCharge = new BigDecimal("17.94");
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

        assertTrue(rentalAgreement.getFinalCharge().toString().equals(expectedFinalCharge.toString()));
    }

    @Test
    public void testScenario5()
    {
        String toolCode = "JAKR";
        LocalDate checkoutDate = LocalDate.of(2015, 7, 2);
        int rentalDays = 9;
        double discount = 0;
        BigDecimal expectedFinalCharge = new BigDecimal("26.91");
        CheckoutHandler checkoutHandler = new CheckoutHandler(toolCode, rentalDays, discount, checkoutDate);
        RentalAgreement rentalAgreement = new RentalAgreement();
        try
        {
            rentalAgreement = checkoutHandler.completeCheckout();
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        assertTrue(rentalAgreement.getFinalCharge().toString().equals(expectedFinalCharge.toString()));
    }

    @Test
    public void testScenario6()
    {
        String toolCode = "JAKR";
        LocalDate checkoutDate = LocalDate.of(2020, 7, 2);
        int rentalDays = 4;
        double discount = 50;
        BigDecimal expectedFinalCharge = new BigDecimal("5.98");
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

        assertTrue(rentalAgreement.getFinalCharge().toString().equals(expectedFinalCharge.toString()));
    }

    
}
