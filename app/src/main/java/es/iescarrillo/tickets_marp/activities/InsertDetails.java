package es.iescarrillo.tickets_marp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
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

public class InsertDetails extends AppCompatActivity {

    //Declaracion de los componentes
    private Button btnAddDetails, btnCancelDetails;

    private EditText etPrecioDetails, etDescriptionDetails;

    private Ticket ticket;
    DetailsTicket detailsTicket = new DetailsTicket();


    private DetailsTicket2 detailsTicket2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_details);


        //Recuperamos datos del intent
      Intent intent = getIntent();

      ticket = new Ticket();
      if (intent != null){
          ticket = (Ticket) intent.getSerializableExtra("ticket");
      }


      //Inicializacion de los componentes
        etPrecioDetails = findViewById(R.id.etPrecioDetails);
        etDescriptionDetails = findViewById(R.id.etDescriptionDetails);
        btnAddDetails = findViewById(R.id.btnAddDetails);
        btnCancelDetails = findViewById(R.id.btnCancelDetails);


        //DEclaracion del servicio
        GoldenRaceApiService apiService = GoldenRaceApiClient.getClient().create(GoldenRaceApiService.class);


        //Bototn de  Insetar detalles de los ticket
        btnAddDetails.setOnClickListener(v -> {

            //Aqui creamos una instancia de DetailsTicket y le introducimos los datos
            DetailsTicket newDetailsTicket = new DetailsTicket();
            newDetailsTicket.setTicket(ticket);
            newDetailsTicket.setAmount(Double.parseDouble(etPrecioDetails.getText().toString()));
            newDetailsTicket.setDescription(etDescriptionDetails.getText().toString());
            newDetailsTicket.setId(0);


            //Llamada al servicio
            Call details = apiService.postDetail(newDetailsTicket);

            details.enqueue(new Callback<DetailsTicket>() {
                @Override
                public void onResponse(Call<DetailsTicket> call, Response<DetailsTicket> response) {
                    if (response.isSuccessful()) {
                        DetailsTicket details = response.body();
                        Log.i("Successful ticket loaded", details.toString());
                       // detailsTicket2.updateTotalAmount();
                        Toast.makeText(getApplicationContext(), "Detalle añadido correctamente", Toast.LENGTH_SHORT).show();

                    } else {
                        Log.i("Ticket Error", "Error al cargar el detalle del ticket");
                    }
                }

                @Override
                public void onFailure(Call<DetailsTicket> call, Throwable t) {
                    // Manejar la falla según tus requisitos
                }
            });

            //Inteint que cuando se realiza esta inserccion de datos nos devuelve a DetailsATicket2
            Intent back = new Intent(getApplicationContext(), DetailsTicket2.class);
            back.putExtra("ticket", ticket);
            back.putExtra("detailsTickets", detailsTicket );
            startActivity(back);
        });

        //Boton de cancelar que te devuelve a la activity anterior
        btnCancelDetails.setOnClickListener(v -> onBackPressed());
    }
}