package com.ericknathan.symbian.bookala;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.ericknathan.symbian.bookala.adapters.AddressAdapter;
import com.ericknathan.symbian.bookala.database.SQLiteProvider;
import com.ericknathan.symbian.bookala.models.Address;

import java.util.List;

public class AddressListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_list);

        Bundle extras = getIntent().getExtras();
        int userId = extras.getInt("cod_usuario");
        List<Address> books = SQLiteProvider.getInstance(this).listAddresses(userId);

        RecyclerView recyclerView = findViewById(R.id.recyclerview_book_list);
        AddressAdapter adapter = new AddressAdapter(books);
        recyclerView.setAdapter(adapter);
    }
}