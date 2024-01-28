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

    private Button btnAddDetails, btnCancelDetails;

    private EditText etPrecioDetails, etDescriptionDetails;

    private Ticket ticket;

    private DetailsTicket detailsTicket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_details);


      Intent intent = getIntent();

      ticket = new Ticket();
      if (intent != null){
          ticket = (Ticket) intent.getSerializableExtra("ticket");
      }

        etPrecioDetails = findViewById(R.id.etPrecioDetails);
        etDescriptionDetails = findViewById(R.id.etDescriptionDetails);
        btnAddDetails = findViewById(R.id.btnAddDetails);
        btnCancelDetails = findViewById(R.id.btnCancelDetails);

        GoldenRaceApiService apiService = GoldenRaceApiClient.getClient().create(GoldenRaceApiService.class);

        btnAddDetails.setOnClickListener(v -> {

            DetailsTicket newDetailsTicket = new DetailsTicket();
            newDetailsTicket.setTicket(ticket);
            newDetailsTicket.setAmount(Double.valueOf(etPrecioDetails.getText().toString()));
            newDetailsTicket.setDescription(etDescriptionDetails.getText().toString());
            newDetailsTicket.setId(0);

            Call details = apiService.postDetail(newDetailsTicket);

            details.enqueue(new Callback<DetailsTicket>() {
                @Override
                public void onResponse(Call<DetailsTicket> call, Response<DetailsTicket> response) {
                    if (response.isSuccessful()) {
                        DetailsTicket details = response.body();
                        Log.i("Successful ticket loaded", details.toString());
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

            Intent back = new Intent(getApplicationContext(), DetailsTicket2.class);
            back.putExtra("ticket", ticket);
            startActivity(back);
        });

        btnCancelDetails.setOnClickListener(v -> onBackPressed());
    }
}