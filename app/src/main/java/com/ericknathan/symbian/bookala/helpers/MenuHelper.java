package com.ericknathan.symbian.bookala.helpers;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import com.ericknathan.symbian.bookala.AddressListActivity;
import com.ericknathan.symbian.bookala.HomeActivity;
import com.ericknathan.symbian.bookala.R;
import com.ericknathan.symbian.bookala.RegisterAddressActivity;
import com.ericknathan.symbian.bookala.SignInActivity;

public class MenuHelper {
    private final Activity activity;

    public MenuHelper(Activity activity) {
        this.activity = activity;
    }

    public Menu createOptionsMenu(Menu menu) {
        this.activity.getMenuInflater().inflate(R.menu.menu, menu);
        return menu;
    }

    @SuppressLint("NonConstantResourceId")
    public MenuItem handleMenuOptions(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.menu_homepage:
                this.activity.startActivity(new Intent(this.activity, HomeActivity.class));
                break;
            case R.id.menu_register_address:
                intent  = new Intent(this.activity, RegisterAddressActivity.class);
                intent.putExtra("cod_usuario", LoginHelper.getUserId());
                intent.putExtra("back_activity", "home");
                this.activity.startActivity(intent);
                break;
            case R.id.menu_address_list:
                intent  = new Intent(this.activity, AddressListActivity.class);
                intent.putExtra("cod_usuario", LoginHelper.getUserId());
                this.activity.startActivity(intent);
                break;
            case R.id.menu_logout:
                LoginHelper.setUserId();
                this.activity.startActivity(new Intent(this.activity, SignInActivity.class));
                break;
        }
        return item;
    }
}
