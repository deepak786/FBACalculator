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

import java.util.Arrays;
import java.util.Calendar;

public class FBA {
    private Builder builder;

    private FBA(Builder builder) {
        this.builder = builder;
    }

    /**
     * check item type (standard or oversize)
     *
     * @param dimensions package dimens (length, width, height) in inches
     * @param weight     package weight in pounds
     * @return item type
     */
    private String standard_or_oversize(double[] dimensions, double weight) {
        // Determine if object is standard size or oversize
        double length = dimensions[0];
        double width = dimensions[1];
        double height = dimensions[2];

        if (weight > Constants.OversizeDimens.WEIGHT
                || Max(length, width, height) > Constants.OversizeDimens.MAX
                || Min(length, width, height) > Constants.OversizeDimens.MIN
                || Median(dimensions) > Constants.OversizeDimens.MEDIAN) {
            return Constants.OVERSIZE;
        }
        return Constants.STANDARD;
    }

    /**
     * get the monthly storage fees of product
     */
    private double getMonthlyStorageFees() {
        double dimensions[] = builder.dimensions;
        double weight = builder.weight;

        // get current month
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
        double cubicFeet = getCubicFeet(dimensions);
        String standard_or_oversize = standard_or_oversize(dimensions, weight);
        double perCubicFees;
        if (currentMonth >= 0 && currentMonth <= 8) {
            // January - September
            if (standard_or_oversize.equals(Constants.OVERSIZE))
                perCubicFees = Constants.StorageFees.OVERSIZE_JAN_SEP;
            else
                perCubicFees = Constants.StorageFees.STANDARD_JAN_SEP;
        } else {
            // October - December
            if (standard_or_oversize.equals(Constants.OVERSIZE))
                perCubicFees = Constants.StorageFees.OVERSIZE_OCT_DEC;
            else
                perCubicFees = Constants.StorageFees.STANDARD_OCT_DEC;
        }
        return formatDouble(cubicFeet * perCubicFees);
    }

    /**
     * calculate Fulfillment Fees
     */
    private double getFulfillmentFees() {
        double dimensions[] = builder.dimensions;
        double weight = builder.weight;

        double fulfillmentFees = 0;

        double dimensionalWeight = getDimensionalWeight(dimensions);
        String size_tier = getSizeTier(dimensions, weight);
        double outboundWeight = getOutboundWeight(weight, dimensionalWeight, size_tier);
        // calculate fulfillment fee
        switch (size_tier) {
            case Constants.SizeTier.SMALL_STANDARD:
                fulfillmentFees = Constants.FulfillmentFee.SMALL_STANDARD;
                break;
            case Constants.SizeTier.LARGE_STANDARD:
                if (outboundWeight <= 1)
                    fulfillmentFees = Constants.FulfillmentFee.LARGE_STANDARD_1LB_OR_LESS;
                else if (outboundWeight > 1 && outboundWeight <= 2)
                    fulfillmentFees = Constants.FulfillmentFee.LARGE_STANDARD_1LB_TO_2LB;
                else
                    fulfillmentFees = Constants.FulfillmentFee.LARGE_STANDARD_OVER_2LB(outboundWeight);
                break;
            case Constants.SizeTier.SMALL_OVERSIZE:
                fulfillmentFees = Constants.FulfillmentFee.SMALL_OVERSIZE(outboundWeight);
                break;
            case Constants.SizeTier.MEDIUM_OVERSIZE:
                fulfillmentFees = Constants.FulfillmentFee.MEDIUM_OVERSIZE(outboundWeight);
                break;
            case Constants.SizeTier.LARGE_OVERSIZE:
                fulfillmentFees = Constants.FulfillmentFee.LARGE_OVERSIZE(outboundWeight);
                break;
            case Constants.SizeTier.SPECIAL_OVERSIZE:
                fulfillmentFees = Constants.FulfillmentFee.SPECIAL_OVERSIZE(outboundWeight);
                break;
        }

        if (builder.isApparel)
            fulfillmentFees += Constants.APPAREL_FEE;

        // TODO (Standard Non-Media and $40 special handling fee applies to plasma and projection large-screen televisions with screens 42" or larger. )
        return formatDouble(fulfillmentFees);
    }

    /**
     * get product size tier @{{@link Constants.SizeTier}}
     */
    private String getSizeTier(double dimensions[], double weight) {
        double length = dimensions[0];
        double width = dimensions[1];
        double height = dimensions[2];

        String standard_or_oversize = standard_or_oversize(dimensions, weight);

        double median = Median(dimensions);
        double max = Max(length, width, height);
        double min = Min(length, width, height);

        if (standard_or_oversize.equals(Constants.STANDARD)) {
            // standard
            if (weight <= Constants.StandardSmallDimens.WEIGHT
                    && max <= Constants.StandardSmallDimens.MAX
                    && min <= Constants.StandardSmallDimens.MIN
                    && median <= Constants.StandardSmallDimens.MEDIAN) {
                // small standard size
                return Constants.SizeTier.SMALL_STANDARD;
            } else {
                // large standard size
                return Constants.SizeTier.LARGE_STANDARD;
            }
        } else {
            // oversize
            double girthLength = lengthPlusGirth(dimensions);
            if (weight <= Constants.SmallOversizeDimens.WEIGHT
                    && max <= Constants.SmallOversizeDimens.MAX
                    && median <= Constants.SmallOversizeDimens.MEDIAN
                    && girthLength <= Constants.SmallOversizeDimens.GIRTH_LENGTH) {
                // small oversize
                return Constants.SizeTier.SMALL_OVERSIZE;
            } else if (weight <= Constants.MediumOversizeDimens.WEIGHT
                    && max <= Constants.MediumOversizeDimens.MAX
                    && girthLength <= Constants.MediumOversizeDimens.GIRTH_LENGTH) {
                // medium oversize
                return Constants.SizeTier.MEDIUM_OVERSIZE;
            } else if (weight <= Constants.LargeOversizeDimens.WEIGHT
                    && max <= Constants.LargeOversizeDimens.MAX
                    && girthLength <= Constants.LargeOversizeDimens.GIRTH_LENGTH) {
                // large oversize
                return Constants.SizeTier.LARGE_OVERSIZE;
            } else {
                // special oversize
                return Constants.SizeTier.SPECIAL_OVERSIZE;
            }
        }
    }

    /**
     * get outbound weight
     */
    private double getOutboundWeight(double weight, double dimensionalWeight, String size_tier) {
        switch (size_tier) {
            case Constants.SizeTier.SMALL_STANDARD:
            case Constants.SizeTier.LARGE_STANDARD:
                if (weight <= 1)
                    return Math.ceil(weight + Constants.PackagingWeightFee.STANDARD);
                return Math.ceil(Math.max(weight, dimensionalWeight) +
                        Constants.PackagingWeightFee.STANDARD);
            case Constants.SizeTier.SMALL_OVERSIZE:
            case Constants.SizeTier.MEDIUM_OVERSIZE:
            case Constants.SizeTier.LARGE_OVERSIZE:
                return Math.ceil(Math.max(weight, dimensionalWeight) +
                        Constants.PackagingWeightFee.OVERSIZE);
            case Constants.SizeTier.SPECIAL_OVERSIZE:
                return Math.ceil(weight + Constants.PackagingWeightFee.OVERSIZE);
        }
        return 0;
    }

    /**
     * get amazon referral fee
     */
    private double getReferralFee(String category, double price) {
        double referralFeePer = Constants.REFERRAL_FEE_PERCENTAGE.get(category);
        double minReferralFee = Constants.REFERRAL_FEE_MINIMUM.get(category);

        switch (category) {
            case "Collectible Coins":
                if (price > 250 && price <= 1000) {
                    return Math.max(250 * referralFeePer, minReferralFee)
                            + ((price - 250) * 0.1);
                } else if (price > 1000) {
                    return Math.max(250 * referralFeePer, minReferralFee)
                            + (750 * 0.1)
                            + ((price - 1000) * 0.06);
                }
                break;
            case "Electronics Accessories":
            case "Electronics (Accessories)":
                if (price > 100) {
                    return Math.max((100 * referralFeePer) + ((price - 100) * 0.08), minReferralFee);
                }
                break;
            case "Fashion Jewelry":
            case "Fine Jewelry":
            case "Jewelry":
                if (price > 250) {
                    return Math.max((250 * referralFeePer) + ((price - 250) * 0.05), minReferralFee);
                }
                break;
            case "Grocery & Gourmet Food":
                if (price > 15) {
                    return Math.max((15 * referralFeePer) + ((price - 15) * 0.15), minReferralFee);
                }
                break;
            case "Shoes, Handbags & Sunglasses":
                if (price > 75) {
                    return Math.max((75 * referralFeePer) + ((price - 75) * 0.18), minReferralFee);
                }
                break;
            case "Watches":
                if (price > 1500) {
                    return Math.max((1500 * referralFeePer) + ((price - 1500) * 0.03), minReferralFee);
                }
                break;
            case "Major Appliances":
                if (price > 300) {
                    return Math.max((300 * referralFeePer) + ((price - 300) * 0.08), minReferralFee);
                }
                break;
            case "Entertainment Collectibles":
            case "Sports Collectibles":
                if (price > 100 && price <= 1000) {
                    return (100 * referralFeePer) + ((price - 100) * 0.1);
                } else if (price > 1000) {
                    return (100 * referralFeePer) + (900 * 0.1) + ((price - 1000) * 0.06);
                }
                break;
            case "Fine Art":
                if (price > 100 && price <= 1000) {
                    return Math.max(100 * referralFeePer, minReferralFee)
                            + ((price - 100) * 0.15);
                } else if (price > 1000 && price <= 5000) {
                    return Math.max(100 * referralFeePer, minReferralFee)
                            + (900 * 0.15)
                            + ((price - 1000) * 0.1);
                } else if (price > 5000) {
                    return Math.max(100 * referralFeePer, minReferralFee)
                            + (900 * 0.15)
                            + (4000 * 0.1)
                            + ((price - 5000) * 0.05);
                }
                break;
        }
        return Math.max(price * referralFeePer, minReferralFee);
    }

    /**
     * get variable closing fee
     */
    private double getVariableClosingFee(String category) {
        if (Constants.VARIABLE_CLOSING_FEE.containsKey(category))
            return Constants.VARIABLE_CLOSING_FEE.get(category);
        return 0.0;
    }

    /**
     * get dimensional weight
     */
    private double getDimensionalWeight(double dimensions[]) {
        return (dimensions[0] * dimensions[1] * dimensions[2]) / Constants.DIMENSIONAL_WEIGHT_FACTOR;
    }

    /**
     * get cubic feet
     */
    private double getCubicFeet(double dimensions[]) {
        return (dimensions[0] * dimensions[1] * dimensions[2]) / Constants.CUBIC_FEET_FACTOR;
    }

    /**
     * calculate length plus girth
     */
    private double lengthPlusGirth(double dimensions[]) {
        double length = dimensions[0];
        double width = dimensions[1];
        double height = dimensions[2];

        double median = Median(dimensions);
        double max = Max(length, width, height);
        double min = Min(length, width, height);

        double girth = (min + median) * 2;
        return max + girth;
    }


    private double Max(double a, double b, double c) {
        return Math.max(Math.max(a, b), c);
    }

    private double Min(double a, double b, double c) {
        return Math.min(Math.min(a, b), c);
    }

    private double Median(double dimensions[]) {
        // sort the dimensions array
        Arrays.sort(dimensions);
        int length = dimensions.length;
        if (length % 2 == 0)
            return (dimensions[length / 2] + dimensions[(length / 2) - 1]) / 2;
        else
            return dimensions[(length - 1) / 2];
    }

    private double formatDouble(double val) {
        val = val * 100;
        val = Math.round(val);
        val = val / 100;
        return val;
    }

    /**
     * calculate the FBA fee
     *
     * @return {{@link FBAFee}} Model class
     */
    FBAFee getFeeDetails() {
        FBAFee fbaFee = new FBAFee();
        fbaFee.setRevenue(builder.getPrice());
        fbaFee.setMonthlyStorageFee(getMonthlyStorageFees());
        fbaFee.setFulfillmentFee(getFulfillmentFees());
        fbaFee.setSizeTier(getSizeTier(builder.dimensions, builder.weight));
        fbaFee.setVariableClosingFee(getVariableClosingFee(builder.getCategory()));
        fbaFee.setAmazonReferralFee(formatDouble(getReferralFee(builder.getCategory(), builder.getPrice())));

        return fbaFee;
    }

    /**
     * FBA Builder class to provide the details to calculate the FBA fee.
     */
    public static final class Builder {
        private FBA fba;

        private String category;
        private double price; // item Price + shipping
        private double length;
        private double width;
        private double height;
        private double dimensions[] = new double[3]; // we need to store length, width and height only
        private double weight;
        private boolean isApparel;

        public Builder() {
            // create instance of FBA
            fba = new FBA(this);
        }

        public String getCategory() {
            return category;
        }

        public Builder setCategory(String category) {
            this.category = category;
            return this;
        }

        public double getPrice() {
            return price;
        }

        public Builder setPrice(double price) {
            this.price = price;
            return this;
        }

        public double getLength() {
            return length;
        }

        public Builder setLength(double length) {
            this.length = length;
            this.dimensions[0] = length;
            return this;
        }

        public double getWidth() {
            return width;
        }

        public Builder setWidth(double width) {
            this.width = width;
            this.dimensions[1] = width;
            return this;
        }

        public double getHeight() {
            return height;
        }

        public Builder setHeight(double height) {
            this.height = height;
            this.dimensions[2] = height;
            return this;
        }

        public double getWeight() {
            return weight;
        }

        public Builder setWeight(double weight) {
            this.weight = weight;
            return this;
        }

        public boolean isApparel() {
            return isApparel;
        }

        public Builder setApparel(boolean apparel) {
            isApparel = apparel;
            return this;
        }

        /**
         * calculate fee
         */
        public FBAFee calculate() {
            return fba.getFeeDetails();
        }
    }
}
