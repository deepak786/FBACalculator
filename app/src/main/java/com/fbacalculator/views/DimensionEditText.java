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

import com.fbacalculator.R;

public class DimensionEditText extends AppCompatEditText implements TextWatcher {

    public DimensionEditText(Context context) {
        super(context);
        init();
    }

    public DimensionEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DimensionEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * initialize
     */
    private void init() {
        setBackgroundResource(R.drawable.background_edit_text);

        setTextColor(Color.BLACK);
        setHintTextColor(Color.parseColor("#A8A8A8"));
        int padding = 20;
        setPadding(padding, padding, padding, padding);

        setLongClickable(false);
        setTextIsSelectable(false);

        setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        setKeyListener(DigitsKeyListener.getInstance("0123456789."));

        addTextChangedListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.toString().contains("."))
            setKeyListener(DigitsKeyListener.getInstance("0123456789"));
        else
            setKeyListener(DigitsKeyListener.getInstance("0123456789."));
    }
}