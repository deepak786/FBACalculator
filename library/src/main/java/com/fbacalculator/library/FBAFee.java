/*
 * Copyright (c) 2018, Deepak Goyal under Apache License.
 * All rights reserved.
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * - Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * - Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 */

package com.fbacalculator.library;

/**
 * Class contains the breakdown of FBA fees
 */
public class FBAFee {
    private double fulfillmentFee;
    private double monthlyStorageFee;
    private String sizeTier;
    private double amazonReferralFee;
    private double variableClosingFee;
    private double revenue;
    private double shippingCredit;

    public double getFulfillmentFee() {
        return fulfillmentFee;
    }

    public void setFulfillmentFee(double fulfillmentFee) {
        this.fulfillmentFee = fulfillmentFee;
    }

    public double getMonthlyStorageFee() {
        return monthlyStorageFee;
    }

    public void setMonthlyStorageFee(double monthlyStorageFee) {
        this.monthlyStorageFee = monthlyStorageFee;
    }

    public String getSizeTier() {
        return sizeTier;
    }

    public void setSizeTier(String sizeTier) {
        this.sizeTier = sizeTier;
    }

    public double getAmazonReferralFee() {
        return amazonReferralFee;
    }

    public void setAmazonReferralFee(double amazonReferralFee) {
        this.amazonReferralFee = amazonReferralFee;
    }

    public double getVariableClosingFee() {
        return variableClosingFee;
    }

    public void setVariableClosingFee(double variableClosingFee) {
        this.variableClosingFee = variableClosingFee;
    }

    public double getRevenue() {
        return revenue;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }

    public double getShippingCredit() {
        return shippingCredit;
    }

    public void setShippingCredit(double shippingCredit) {
        this.shippingCredit = shippingCredit;
    }

    public double getSellerProceeds() {
        double sellerProceeds = getRevenue() -
                (getAmazonReferralFee()
                        + getVariableClosingFee()
                        + getFulfillmentFee()
                        + getMonthlyStorageFee());
        return formatDouble(sellerProceeds);
    }

    @Override
    public String toString() {
        return "Size Tier: \t" + getSizeTier()
                + "\nRevenue: \t$" + getRevenue()
                + "\nAmazon Referral Fee: \t$" + getAmazonReferralFee()
                + "\nVariable Closing Fee: \t$" + getVariableClosingFee()
                + "\nFulfillment Fee: \t$" + getFulfillmentFee()
                + "\nMonthly Storage Fee: \t$" + getMonthlyStorageFee()
                + "\nSeller Proceeds: \t$" + getSellerProceeds()
                + "\nShipping Credit: \t$" + getShippingCredit();
    }

    private double formatDouble(double val) {
        val = val * 100;
        val = Math.round(val);
        val = val / 100;
        return val;
    }
}
