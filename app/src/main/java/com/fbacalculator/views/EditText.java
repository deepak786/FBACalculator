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
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

import com.fbacalculator.R;

public class EditText extends AppCompatEditText {

    public EditText(Context context) {
        super(context);
        init();
    }

    public EditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EditText(Context context, AttributeSet attrs, int defStyleAttr) {
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
    }
}
