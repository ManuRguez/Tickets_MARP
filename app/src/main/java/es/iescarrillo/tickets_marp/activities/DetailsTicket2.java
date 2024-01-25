package es.iescarrillo.tickets_marp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import es.iescarrillo.tickets_marp.R;
import es.iescarrillo.tickets_marp.adapters.DetailsTicketsAdapter;
import es.iescarrillo.tickets_marp.apiClients.GoldenRaceApiClient;
import es.iescarrillo.tickets_marp.apiServices.GoldenRaceApiService;
import es.iescarrillo.tickets_marp.models.DetailsTicket;
import es.iescarrillo.tickets_marp.models.Ticket;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsTicket2 extends AppCompatActivity {
    Button btnEdit,btnDelete;
    Ticket ticket;

    ListView lvDetailsTickets;

    DetailsTicketsAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_ticket2);



        btnEdit=findViewById(R.id.btnEdit);
        btnDelete = findViewById(R.id.btnDelete);

        Ticket ticket = (Ticket) getIntent().getSerializableExtra("ticket");
        DetailsTicket detailsTicket = new DetailsTicket();


        TextView tvDetailsId = findViewById(R.id.tvDetailsId);
        TextView tvDescription = findViewById(R.id.tvDescription);
        TextView tvAmount = findViewById(R.id.tvAmount);

        tvDetailsId.setText(ticket.getId().toString());
        tvDescription.setText(ticket.getCreationDate().toString());
        tvAmount.setText(ticket.getTotalAmount().toString());


        //Detalles de los detalles
        GoldenRaceApiService apiService = GoldenRaceApiClient.getClient().create(GoldenRaceApiService.class);
        Call<List<DetailsTicket>> call2 = apiService.getDetails(ticket.getId());
        lvDetailsTickets = findViewById(R.id.lvDetailsTickets);


        btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), EditTicket.class);
            intent.putExtra("ticket", ticket);
            startActivity(intent);
        });

        btnDelete.setOnClickListener(v -> {

            Call<Void> call = apiService.deleteTicket(ticket.getId());

            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if(response.isSuccessful()){
                        Void deleteTicket = response.body();

                        Toast.makeText(getApplicationContext(), "Ticket eliminado correctamente", Toast.LENGTH_SHORT).show();
                    }else{
                        Log.i("Ticket Error", "Error to upload Ticket");
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {

                }
            });
            Intent back = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(back);


        });

        call2.enqueue(new Callback<List<DetailsTicket>>() {
            @Override
            public void onResponse(Call<List<DetailsTicket>> call, Response<List<DetailsTicket>> response) {
                List<DetailsTicket> listDetails = response.body();
                Log.i("DetailsTicket",listDetails.toString());

                adapter = new DetailsTicketsAdapter(getApplicationContext(),listDetails);

                lvDetailsTickets.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<DetailsTicket>> call, Throwable t) {

            }
        });





    }
}
