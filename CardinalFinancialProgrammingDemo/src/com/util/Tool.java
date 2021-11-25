package com.util;

public class Tool
{
    public String code;
    public String brand;
    public String type;
    public double charge;

    // Would rather use a properties file to store this information
    private static final String codeCHNS = "CHNS";
    private static final String codeLADW = "LADW";
    private static final String codeJAKD = "JAKD";
    private static final String codeJAKR = "JAKR";

    private static final String brandCHNS = "Stihl";
    private static final String brandLADW = "Werner";
    private static final String brandJAKD = "DeWalt";
    private static final String brandJAKR = "Ridgid";

    private static final String typeChainsaw = "Chainsaw";
    private static final String typeLadder = "Ladder";
    private static final String typeJackhammer = "Jackhammer";

    private static final double chargeChainsaw = 1.49;
    private static final double chargeLadder = 1.99;
    private static final double chargeJackhammer = 2.99;

    private static final String message = "INVALID TOOL CODE";

    public Tool(String code) throws Exception
    {
        // Set tool information from tool code
        switch (code)
        {
            case codeCHNS:
                setCode(code);
                setBrand(brandCHNS);
                setType(typeChainsaw);
                setCharge(chargeChainsaw);
            case codeLADW:
                setCode(code);
                setBrand(brandLADW);
                setType(typeLadder);
                setCharge(chargeLadder);
            case codeJAKD:
                setCode(code);
                setBrand(brandJAKD);
                setType(typeJackhammer);
                setCharge(chargeJackhammer);
            case codeJAKR:
                setCode(code);
                setBrand(brandJAKR);
                setType(typeJackhammer);
                setCharge(chargeJackhammer);
            default:
                throw new Exception(message);
        }
        
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getBrand()
    {
        return brand;
    }

    public void setBrand(String brand)
    {
        this.brand = brand;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public double getCharge()
    {
        return charge;
    }

    public void setCharge(double charge)
    {
        this.charge = charge;
    }
}
