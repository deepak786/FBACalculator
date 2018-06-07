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

package com.fbacalculator;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.text.TextUtils;

import com.fbacalculator.library.FBA;
import com.fbacalculator.library.FBAFee;

public class MainActivityViewModel extends ViewModel {
    // category
    private MutableLiveData<String> category = new MutableLiveData<>();
    // revenue
    private MutableLiveData<String> itemPrice = new MutableLiveData<>();
    private MutableLiveData<String> shipping = new MutableLiveData<>();
    // dimensions
    private MutableLiveData<String> length = new MutableLiveData<>();
    private MutableLiveData<String> width = new MutableLiveData<>();
    private MutableLiveData<String> height = new MutableLiveData<>();
    private MutableLiveData<String> weight = new MutableLiveData<>();
    private MutableLiveData<Boolean> isApparel = new MutableLiveData<>();
    // view observers
    private MutableLiveData<Integer> errorRes = new MutableLiveData<>();
    private MutableLiveData<FBAFee> fbaFee = new MutableLiveData<>();
    private MutableLiveData<Boolean> hideKeyboard = new MutableLiveData<>();

    public MutableLiveData<String> getCategory() {
        return category;
    }

    public void setCategory(MutableLiveData<String> category) {
        this.category = category;
    }

    public MutableLiveData<String> getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(MutableLiveData<String> itemPrice) {
        this.itemPrice = itemPrice;
    }

    public MutableLiveData<String> getShipping() {
        return shipping;
    }

    public void setShipping(MutableLiveData<String> shipping) {
        this.shipping = shipping;
    }

    public MutableLiveData<String> getLength() {
        return length;
    }

    public void setLength(MutableLiveData<String> length) {
        this.length = length;
    }

    public MutableLiveData<String> getWidth() {
        return width;
    }

    public void setWidth(MutableLiveData<String> width) {
        this.width = width;
    }

    public MutableLiveData<String> getHeight() {
        return height;
    }

    public void setHeight(MutableLiveData<String> height) {
        this.height = height;
    }

    public MutableLiveData<String> getWeight() {
        return weight;
    }

    public void setWeight(MutableLiveData<String> weight) {
        this.weight = weight;
    }

    public MutableLiveData<Boolean> getIsApparel() {
        return isApparel;
    }

    public void setIsApparel(MutableLiveData<Boolean> isApparel) {
        this.isApparel = isApparel;
    }

    public MutableLiveData<Integer> getErrorRes() {
        return errorRes;
    }

    public MutableLiveData<FBAFee> getFbaFee() {
        return fbaFee;
    }

    public MutableLiveData<Boolean> getHideKeyboard() {
        return hideKeyboard;
    }

    /**
     * calculate the fee
     */
    public void calculate() {
        // category validation
        if (TextUtils.isEmpty(category.getValue())) {
            errorRes.postValue(R.string.emptyCategory);
            return;
        }

        // item price validation
        double $p;
        if (isValidNumber(itemPrice.getValue())) {
            $p = getDoubleValue(itemPrice.getValue());
        } else {
            errorRes.postValue(R.string.invalidItemPrice);
            return;
        }

        // shipping price validation
        double $s;
        if (isValidNumber(shipping.getValue())) {
            $s = getDoubleValue(shipping.getValue());
        } else {
            errorRes.postValue(R.string.invalidShippingPrice);
            return;
        }
        // total revenue
        double revenue = $p + $s;

        // length validation
        double dimenL;
        if (isValidNumber(length.getValue()))
            dimenL = getDoubleValue(length.getValue());
        else {
            errorRes.postValue(R.string.invalidLength);
            return;
        }

        // width validation
        double dimenW;
        if (isValidNumber(width.getValue()))
            dimenW = getDoubleValue(width.getValue());
        else {
            errorRes.postValue(R.string.invalidWidth);
            return;
        }

        // height validation
        double dimenH;
        if (isValidNumber(height.getValue()))
            dimenH = getDoubleValue(height.getValue());
        else {
            errorRes.postValue(R.string.invalidHeight);
            return;
        }

        // weight validation
        double wt;
        if (isValidNumber(weight.getValue()))
            wt = getDoubleValue(weight.getValue());
        else {
            errorRes.postValue(R.string.invalidWeight);
            return;
        }

        // is apparel
        boolean apparel = false;
        if (isApparel.getValue() != null && isApparel.getValue())
            apparel = true;

        // hide the keyboard
        hideKeyboard.postValue(true);
        // use the library
        FBA.Builder builder = new FBA.Builder();
        builder.setCategory(category.getValue());
        builder.setPrice(revenue);
        builder.setLength(dimenL);
        builder.setWidth(dimenW);
        builder.setHeight(dimenH);
        builder.setWeight(wt);
        builder.setApparel(apparel);
        fbaFee.postValue(builder.calculate());
    }

    private boolean isValidNumber(String value) {
        if (TextUtils.isEmpty(value)) return false;
        try {
            Double.parseDouble(value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private double getDoubleValue(String value) {
        return Double.parseDouble(value);
    }
}
