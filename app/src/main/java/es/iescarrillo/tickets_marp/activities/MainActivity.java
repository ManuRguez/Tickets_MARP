package es.iescarrillo.tickets_marp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import es.iescarrillo.tickets_marp.R;
import es.iescarrillo.tickets_marp.adapters.TicketsAdapter;
import es.iescarrillo.tickets_marp.apiClients.GoldenRaceApiClient;
import es.iescarrillo.tickets_marp.apiServices.GoldenRaceApiService;
import es.iescarrillo.tickets_marp.models.DetailsTicket;
import es.iescarrillo.tickets_marp.models.Ticket;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ListView lvTicket;

    TicketsAdapter ticketsAdapter;

    Button btnAgregar;
    Ticket ticket;

    DetailsTicket2 detailsTicket2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent2 = getIntent();

        GoldenRaceApiService goldenRaceApiService = GoldenRaceApiClient.getClient().create(GoldenRaceApiService.class);

        lvTicket = findViewById(R.id.lvTicket);
        btnAgregar = findViewById(R.id.btnAgregar);
        Call<List<Ticket>> call = goldenRaceApiService.getTickets();
        call.enqueue(new Callback<List<Ticket>>() {
            @Override
            public void onResponse(Call<List<Ticket>> call, Response<List<Ticket>> response) {

                List<Ticket> list = response.body();

                Log.i("Ticket",list.toString());

                ticketsAdapter = new TicketsAdapter(getApplicationContext(),list);



                lvTicket.setAdapter(ticketsAdapter);



            }

            @Override
            public void onFailure(Call<List<Ticket>> call, Throwable t) {
                Log.e("API Call Failure", t.getMessage());
            }
        });


        lvTicket.setOnItemClickListener((parent, view, position, id) -> {
            Ticket selectecTicket = (Ticket) parent.getItemAtPosition(position);

            Intent intent = new Intent(getApplicationContext(), DetailsTicket2.class);

            intent.putExtra("ticket", selectecTicket);

            startActivity(intent);
        });


        btnAgregar.setOnClickListener( v -> {
            Intent intent = new Intent(this, InsertTicket.class);
            intent.putExtra("ticket", ticket);
            startActivity(intent);
        });



    }
}