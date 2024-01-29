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

    //Declaracion de los componentes
    ListView lvTicket;

    TicketsAdapter ticketsAdapter;

    Button btnAgregar;
    Ticket ticket;

    DetailsTicket2 detailsTicket2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Recupramos los datos del intent
        Intent intent2 = getIntent();

        //Declacion del servicio
        GoldenRaceApiService goldenRaceApiService = GoldenRaceApiClient.getClient().create(GoldenRaceApiService.class);


        //Inicializacion de los componentes
        lvTicket = findViewById(R.id.lvTicket);
        btnAgregar = findViewById(R.id.btnAgregar);

        //Llamada al servicio
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

        //Aqui ponemos que cuando se haga clic en un elemento de la lista nos lleve a la activity de DetailsTicket2 y nos llevamos el objeto Ticket para recuperarlo alli
        lvTicket.setOnItemClickListener((parent, view, position, id) -> {
            Ticket selectecTicket = (Ticket) parent.getItemAtPosition(position);

            Intent intent = new Intent(getApplicationContext(), DetailsTicket2.class);

            intent.putExtra("ticket", selectecTicket);

            startActivity(intent);
        });


        //Este boton de agregar nos lleva a la pantalla de InserTicket para poder introducir Tickets
        btnAgregar.setOnClickListener( v -> {
            Intent intent = new Intent(this, InsertTicket.class);
            intent.putExtra("ticket", ticket);
            startActivity(intent);
        });



    }
}