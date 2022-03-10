package com.ericknathan.symbian.bookala.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ericknathan.symbian.bookala.R;
import com.ericknathan.symbian.bookala.models.Address;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressViewHolder> {
    private final List<Address> addresses;

    public AddressAdapter(List<Address> dataSet) {
        addresses = dataSet;
    }

    public static class AddressViewHolder extends RecyclerView.ViewHolder {
        private final TextView textAddressCep, textAddressNumber, textAddressComplement;

        public AddressViewHolder(@NonNull View itemView) {
            super(itemView);

            textAddressCep = itemView.findViewById(R.id.card_address_cep);
            textAddressNumber = itemView.findViewById(R.id.card_address_number);
            textAddressComplement = itemView.findViewById(R.id.card_address_complement);
        }

        public void setAddressData(Address address) {
            textAddressCep.setText(address.getCEP());
            textAddressNumber.setText(address.getNumber());
            textAddressComplement.setText(address.getComplement());
        }
    }

    @NonNull
    @Override
    public AddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == 0) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_address, parent, false);
            return new AddressViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull AddressViewHolder holder, int position) {
        Address address = addresses.get(position);
        holder.setAddressData(address);
    }

    @Override
    public int getItemCount() {
        return addresses.size();
    }
}
