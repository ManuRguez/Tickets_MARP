package es.iescarrillo.tickets_marp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import es.iescarrillo.tickets_marp.R;
import es.iescarrillo.tickets_marp.models.Ticket;

public class TicketsAdapter extends ArrayAdapter<Ticket> {
    public TicketsAdapter(Context context, List<Ticket> tickets) {
        super(context, 0, tickets);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Ticket ticket = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_ticket, parent, false);
        }

        TextView tvID = convertView.findViewById(R.id.tvID); // Asegúrate de que tienes un TextView con el ID correcto en tu layout
        TextView tvCreationDate = convertView.findViewById(R.id.tvCreationDate);
        TextView tvTotalAmount = convertView.findViewById(R.id.tvTotalAccount);
        TextView tvVersion = convertView.findViewById(R.id.tvVersion);

        //Modificamos al texto a mostrar

        tvID.setText("ID: " + ticket.getId());
        tvCreationDate.setText("Creation Date: " + ticket.getCreationDate());
        tvTotalAmount.setText("Total amount " + ticket.getTotalAmount());
        tvVersion.setText("Version " + ticket.getVersion());



        return convertView;
    }
}
