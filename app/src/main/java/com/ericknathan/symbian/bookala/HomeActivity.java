package com.ericknathan.symbian.bookala;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.ericknathan.symbian.bookala.helpers.MenuHelper;

public class HomeActivity extends AppCompatActivity {
    final MenuHelper menuHelper = new MenuHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menuHelper.createOptionsMenu(menu));
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(menuHelper.handleMenuOptions(item));
    }
}