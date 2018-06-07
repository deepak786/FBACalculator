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

package com.fbacalculator.views;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.AttributeSet;
import android.view.View;

import com.fbacalculator.R;

import java.text.NumberFormat;

public class PriceEditText extends AppCompatEditText implements TextWatcher, View.OnClickListener {

    public PriceEditText(Context context) {
        super(context);
        init();
    }

    public PriceEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PriceEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * initialize
     */
    private void init() {
        setBackgroundResource(R.drawable.background_edit_text);

        setCompoundDrawablesWithIntrinsicBounds(R.drawable.dollar, 0, 0, 0);
        setCompoundDrawablePadding(10);

        setTextColor(Color.BLACK);
        setHintTextColor(Color.parseColor("#A8A8A8"));
        int padding = 20;
        setPadding(padding, padding, padding, padding);

        setLongClickable(false);
        setTextIsSelectable(false);

        setInputType(InputType.TYPE_CLASS_NUMBER);
        setKeyListener(DigitsKeyListener.getInstance("0123456789"));

        addTextChangedListener(this);
        setText("0");
        setOnClickListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        removeTextChangedListener(this);
        double d;
        try {
            String value = s.toString().replaceAll("\\D", "");
            d = Double.parseDouble(value);
        } catch (Exception e) {
            d = 0;
        }
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(2);
        numberFormat.setMinimumFractionDigits(2);
        numberFormat.setGroupingUsed(false);
        setText(numberFormat.format(d / 100));
        setSelection(getText().length());
        addTextChangedListener(this);
    }

    @Override
    public void onClick(View v) {
        setSelection(getText().length());
    }

    @Override
    public void onSelectionChanged(int start, int end) {
        CharSequence text = getText();
        if (text != null) {
            if (start != text.length() || end != text.length()) {
                setSelection(text.length(), text.length());
                return;
            }
        }
        super.onSelectionChanged(start, end);
    }
}