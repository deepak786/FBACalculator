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

import java.util.ArrayList;
import java.util.HashMap;

public class Constants {
    // item type
    static final String OVERSIZE = "Oversize";
    static final String STANDARD = "Standard";

    static final double CUBIC_FEET_FACTOR = 12 * 12 * 12;
    static final double DIMENSIONAL_WEIGHT_FACTOR = 139;

    static final double APPAREL_FEE = 0.40;

    // size tier
    static class SizeTier {
        static final String SMALL_STANDARD = "Small Standard";
        static final String LARGE_STANDARD = "Large Standard";
        static final String SMALL_OVERSIZE = "Small oversize";
        static final String MEDIUM_OVERSIZE = "Medium oversize";
        static final String LARGE_OVERSIZE = "Large oversize";
        static final String SPECIAL_OVERSIZE = "Special oversize";
    }

    // storage fees
    static class StorageFees {
        static final double OVERSIZE_JAN_SEP = 0.48;
        static final double OVERSIZE_OCT_DEC = 1.20;

        static final double STANDARD_JAN_SEP = 0.69;
        static final double STANDARD_OCT_DEC = 2.40;
    }

    // oversize dimens
    static class OversizeDimens {
        static final double WEIGHT = 20;
        static final double MAX = 18;
        static final double MIN = 8;
        static final double MEDIAN = 14;
    }

    // small standard size dimens
    static class StandardSmallDimens {
        static final double WEIGHT = 0.75;
        static final double MAX = 15;
        static final double MIN = 0.75;
        static final double MEDIAN = 12;
    }

    // special oversize dimensions
    static class SpecialOversizeDimens {
        static final double WEIGHT = 150;
        static final double MAX = 108;
        static final double GIRTH_LENGTH = 165;
    }

    // large oversize dimensions
    static class LargeOversizeDimens {
        static final double WEIGHT = 150;
        static final double MAX = 108;
        static final double GIRTH_LENGTH = 165;
    }

    // medium oversize dimensions
    static class MediumOversizeDimens {
        static final double WEIGHT = 150;
        static final double MAX = 108;
        static final double GIRTH_LENGTH = 130;
    }

    // small oversize dimens
    static class SmallOversizeDimens {
        static final double WEIGHT = 70;
        static final double MAX = 60;
        static final double MEDIAN = 30;
        static final double GIRTH_LENGTH = 130;
    }

    // packaging weight
    static class PackagingWeightFee {
        static final double STANDARD = 0.25;
        static final double OVERSIZE = 1.00;
    }

    // FBA fulfillment fees
    static class FulfillmentFee {
        static final double SMALL_STANDARD = 2.41;
        static final double LARGE_STANDARD_1LB_OR_LESS = 3.19;
        static final double LARGE_STANDARD_1LB_TO_2LB = 4.71;

        static double LARGE_STANDARD_OVER_2LB(double outboundWeight) {
            return 4.71 + (0.38 * (outboundWeight - 2));
        }

        static double SMALL_OVERSIZE(double outboundWeight) {
            if (outboundWeight <= 2)
                return 8.13;
            return 8.13 + (0.38 * (outboundWeight - 2));
        }

        static double MEDIUM_OVERSIZE(double outboundWeight) {
            if (outboundWeight <= 2)
                return 9.44;
            return 9.44 + (0.38 * (outboundWeight - 2));
        }

        static double LARGE_OVERSIZE(double outboundWeight) {
            if (outboundWeight <= 90)
                return 73.18;
            return 73.18 + (0.79 * (outboundWeight - 90));
        }

        static double SPECIAL_OVERSIZE(double outboundWeight) {
            if (outboundWeight <= 90)
                return 137.32;
            return 137.32 + (0.91 * (outboundWeight - 90));
        }
    }

    static final HashMap<String, Double> REFERRAL_FEE_PERCENTAGE = new HashMap<String, Double>() {{
        put("Amazon Device Accessories", 0.45);
        put("Amazon Kindle", 0.15);
        put("Automotive & Powersports", 0.12); // TODO
        put("Baby Products", 0.15);
        put("Baby Products (Excluding Apparel)", 0.15);
        put("Baby Products (excluding Baby Apparel)", 0.15);
        put("Beauty", 0.15);
        put("Books", 0.15);
        put("Clothing & Accessories", 0.17);
        put("Collectible Coins", 0.15);
        put("Electronics (Accessories)", 0.15);
        put("Electronics Accessories", 0.15);
        put("Electronics (Consumer)", 0.08);
        put("Consumer Electronics", 0.08);
        put("Fashion Jewelry", 0.2);
        put("Fine Jewelry", 0.2);
        put("Jewelry", 0.2);
        put("Fine Art", 0.2);
        put("Grocery & Gourmet Food", 0.08);
        put("Health & Personal Care", 0.15);
        put("Health & Personal Care (including Personal Care Appliances)", 0.15);
        put("Home & Garden", 0.15);
        put("Home & Garden (including Pet Supplies)", 0.15);
        put("Industrial & Scientific", 0.12);
        put("Industrial & Scientific (including Food Service and Janitorial & Sanitation)", 0.12);
        put("Luggage & Travel Accessories", 0.15);
        put("Tools & Home Improvement", 0.15); // TODO
        put("Office Products", 0.15);
        put("Outdoors", 0.15);
        put("Personal Computers", 0.06);
        put("Shoes, Handbags & Sunglasses", 0.15);
        put("Sports", 0.15);
        put("Sports (excluding Sports Collectibles)", 0.15);
        put("Sports Collectibles", 0.2);
        put("Toys & Games", 0.15);
        put("Video Games", 0.15);
        put("Video Game Consoles", 0.08);
        put("Video Games & Video Game Consoles", 0.15);
        put("Watches", 0.16);
        put("Camera and Photo", 0.08);
        put("Cell Phone Devices", 0.08);
        put("DVD", 0.15);
        put("Furniture & Decor", 0.15);
        put("Kitchen", 0.15);
        put("Major Appliances", 0.15);
        put("Music", 0.15);
        put("Musical Instruments", 0.15);
        put("Software & Computer", 0.15);
        put("Software & Computer Games", 0.15);
        put("Unlocked Cell Phones", 0.08);
        put("Video & DVD", 0.15);
        put("Video, DVD & Blu-Ray", 0.15);
        put("3D Printed Products", 0.12);
        put("Collectible Books", 0.15);
        put("Entertainment Collectibles", 0.2);
        put("Gift Cards", 0.2);
        put("Everything Else", 0.15);


        // NOT FOUND
//        put("Handmade");
//        put("Historical & Advertising Collectibles");
//        put("Professional Services");
    }};

    static final HashMap<String, Double> REFERRAL_FEE_MINIMUM = new HashMap<String, Double>() {{
        put("Amazon Device Accessories", 1.0);
        put("Amazon Kindle", 0.0);
        put("Automotive & Powersports", 1.0);
        put("Baby Products", 1.0);
        put("Baby Products (Excluding Apparel)", 1.0);
        put("Baby Products (excluding Baby Apparel)", 1.0);
        put("Beauty", 1.0);
        put("Books", 0.0);
        put("Clothing & Accessories", 1.0);
        put("Collectible Coins", 1.0);
        put("Electronics (Accessories)", 1.0);
        put("Electronics Accessories", 1.0);
        put("Electronics (Consumer)", 1.0);
        put("Consumer Electronics", 1.0);
        put("Fashion Jewelry", 2.0);
        put("Fine Jewelry", 2.0);
        put("Jewelry", 2.0);
        put("Fine Art", 1.0);
        put("Grocery & Gourmet Food", 0.0);
        put("Health & Personal Care", 1.0);
        put("Health & Personal Care (including Personal Care Appliances)", 1.0);
        put("Home & Garden", 1.0);
        put("Home & Garden (including Pet Supplies)", 1.0);
        put("Industrial & Scientific", 1.0);
        put("Industrial & Scientific (including Food Service and Janitorial & Sanitation)", 1.0);
        put("Luggage & Travel Accessories", 1.0);
        put("Tools & Home Improvement", 1.0);
        put("Office Products", 1.0);
        put("Outdoors", 1.0);
        put("Personal Computers", 1.0);
        put("Shoes, Handbags & Sunglasses", 1.0);
        put("Sports", 1.0);
        put("Sports (excluding Sports Collectibles)", 1.0);
        put("Sports Collectibles", 0.0);
        put("Toys & Games", 1.0);
        put("Video Games", 0.0);
        put("Video Game Consoles", 0.0);
        put("Video Games & Video Game Consoles", 0.0);
        put("Watches", 2.0);
        put("Camera and Photo", 1.0);
        put("Cell Phone Devices", 1.0);
        put("DVD", 0.0);
        put("Furniture & Decor", 1.0);
        put("Kitchen", 1.0);
        put("Major Appliances", 1.0);
        put("Music", 0.0);
        put("Musical Instruments", 1.0);
        put("Software & Computer", 0.0);
        put("Software & Computer Games", 0.0);
        put("Unlocked Cell Phones", 1.0);
        put("Video & DVD", 0.0);
        put("Video, DVD & Blu-Ray", 0.0);
        put("3D Printed Products", 0.0);
        put("Collectible Books", 0.0);
        put("Entertainment Collectibles", 0.0);
        put("Gift Cards", 0.0);
        put("Everything Else", 0.0);
    }};

    // variable closing fee
    static final HashMap<String, Double> VARIABLE_CLOSING_FEE = new HashMap<String, Double>() {{
        put("Books", 1.80);
        put("DVD", 1.80);
        put("Music", 1.80);
        put("Software & Computer", 1.80);
        put("Software & Computer Games", 1.80);
        put("Video Games", 1.80);
        put("Video", 1.80);
        put("Video Game Consoles", 1.80);
        put("Video Games & Video Game Consoles", 1.80);
    }};

    // Product Categories
    public static final ArrayList<String> PRODUCT_CATEGORIES = new ArrayList<String>() {{
        add("Amazon Device Accessories");
        add("Amazon Kindle");
        add("Automotive & Powersports");
        add("Baby Products (Excluding Apparel)");
        add("Beauty");
        add("Books");
        add("Clothing & Accessories");
        add("Collectible Coins");
        add("Electronics (Accessories)");
        add("Electronics (Consumer)");
        add("Jewelry");
        add("Fine Art");
        add("Grocery & Gourmet Food");
        add("Health & Personal Care (including Personal Care Appliances)");
        add("Home & Garden (including Pet Supplies)");
        add("Industrial & Scientific (including Food Service and Janitorial & Sanitation)");
        add("Luggage & Travel Accessories");
        add("Tools & Home Improvement");
        add("Office Products");
        add("Outdoors");
        add("Personal Computers");
        add("Shoes, Handbags & Sunglasses");
        add("Sports (excluding Sports Collectibles)");
        add("Sports Collectibles");
        add("Toys & Games");
        add("Video Games");
        add("Video Game Consoles");
        add("Watches");
        add("Camera and Photo");
        add("Cell Phone Devices");
        add("DVD");
        add("Furniture & Decor");
        add("Kitchen");
        add("Major Appliances");
        add("Music");
        add("Musical Instruments");
        add("Software & Computer");
        add("Software & Computer Games");
        add("Unlocked Cell Phones");
        add("Video, DVD & Blu-Ray");
        add("3D Printed Products");
        add("Collectible Books");
        add("Entertainment Collectibles");
        add("Gift Cards");
        add("Everything Else");
    }};
}
