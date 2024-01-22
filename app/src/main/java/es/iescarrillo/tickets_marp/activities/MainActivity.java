package es.iescarrillo.tickets_marp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.List;

import es.iescarrillo.tickets_marp.R;
import es.iescarrillo.tickets_marp.adapters.TicketsAdapter;
import es.iescarrillo.tickets_marp.apiClients.GoldenRaceApiClient;
import es.iescarrillo.tickets_marp.apiServices.GoldenRaceApiService;
import es.iescarrillo.tickets_marp.models.Ticket;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ListView lvTicket;

    TicketsAdapter ticketsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GoldenRaceApiService goldenRaceApiService = GoldenRaceApiClient.getClient().create(GoldenRaceApiService.class);

        Call<List<Ticket>> call = goldenRaceApiService.getTickets();

        call.enqueue(new Callback<List<Ticket>>() {
            @Override
            public void onResponse(Call<List<Ticket>> call, Response<List<Ticket>> response) {

                List<Ticket> list = response.body();

                Log.i("Ticket",list.toString());

                ticketsAdapter = new TicketsAdapter(getApplicationContext(),list);

                lvTicket = findViewById(R.id.lvTicket);

                lvTicket.setAdapter(ticketsAdapter);
            }

            @Override
            public void onFailure(Call<List<Ticket>> call, Throwable t) {
                Log.e("API Call Failure", t.getMessage());
            }
        });


    }
}