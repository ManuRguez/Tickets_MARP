package es.iescarrillo.tickets_marp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import es.iescarrillo.tickets_marp.R;
import es.iescarrillo.tickets_marp.models.DetailsTicket;
import es.iescarrillo.tickets_marp.models.Ticket;

public class DetailsTicketsAdapter extends ArrayAdapter<DetailsTicket> {

    public DetailsTicketsAdapter(Context context, List<DetailsTicket> detailsTickets) {
        super(context, 0, detailsTickets);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DetailsTicket detailsTicket = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_detailsticket, parent, false);
        }

        TextView tvID = convertView.findViewById(R.id.tvID);
        TextView tvDescription = convertView.findViewById(R.id.tvDescription);
        TextView tvAmount = convertView.findViewById(R.id.tvAmount);

        // Corregir la línea para establecer el texto de tvID
        tvID.setText("ID: " + String.valueOf(detailsTicket.getId()));
        tvDescription.setText("Description: " + detailsTicket.getDescription());
        tvAmount.setText("Amount: " + String.valueOf(detailsTicket.getAmount()));

        return convertView;
    }
}
