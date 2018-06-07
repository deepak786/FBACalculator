package com.fbacalculator;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.fbacalculator.databinding.ActivityMainBinding;
import com.fbacalculator.library.Constants;
import com.fbacalculator.library.FBAFee;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        MainActivityViewModel model = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        binding.setLifecycleOwner(this);
        // set item price to 0
        model.getItemPrice().postValue("0");
        // set shipping fee to 0 for FBA
        model.getShipping().postValue("0.00");
        // set the view model to binding
        binding.setViewModel(model);

        model.getHideKeyboard().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean != null && aBoolean)
                    hideKeyboard();
            }
        });
        model.getErrorRes().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                if (integer != null)
                    showToast(integer);
            }
        });
        model.getFbaFee().observe(this, new Observer<FBAFee>() {
            @Override
            public void onChanged(@Nullable FBAFee fbaFee) {
                if (fbaFee != null) {
                    showFeeDialog(fbaFee);
                }
            }
        });

        // click listeners
        binding.selectCategory.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuInfo:
                Intent infoActivity = new Intent(this, Info.class);
                startActivity(infoActivity);
                break;
        }
        return false;
    }

    /**
     * show toast
     */
    private void showToast(int id) {
        Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
    }

    /**
     * show dialog
     */
    private void showFeeDialog(FBAFee fbaFee) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.fbaFeeDetails);
        builder.setMessage(fbaFee.toString());
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    /**
     * hide keyboard
     */
    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.selectCategory:
                ArrayList<String> categories = Constants.PRODUCT_CATEGORIES;
                Collections.sort(categories);
                showCategoryDialog(categories);
                break;
        }
    }

    /**
     * category chooser alert dialog
     */
    private void showCategoryDialog(final ArrayList<String> categories) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.selectCategory)
                .setItems(categories.toArray(new CharSequence[categories.size()]), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        binding.selectCategory.setText(categories.get(which));
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }
}
