package es.iescarrillo.tickets_marp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import es.iescarrillo.tickets_marp.R;
import es.iescarrillo.tickets_marp.apiClients.GoldenRaceApiClient;
import es.iescarrillo.tickets_marp.apiServices.GoldenRaceApiService;
import es.iescarrillo.tickets_marp.models.DetailsTicket;
import es.iescarrillo.tickets_marp.models.Ticket;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditDetails extends AppCompatActivity {

    DetailsTicket detailsTicket;

    EditText etDescriptionDetails, etAmountDetails;
    TextView tvID;

    Button btnBackDetails, btnDeleteDetails,btnSaveDetails;

    Ticket ticket;

    DetailsTicket2 detailsTicket2;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_details);

        Intent intent = getIntent();

        detailsTicket = new DetailsTicket();
        if(intent != null){
            detailsTicket = (DetailsTicket) intent.getSerializableExtra("detailsTicket");
        }

        ticket = new Ticket();
        if(intent != null){
            ticket = (Ticket) intent.getSerializableExtra("ticket");
        }

        btnSaveDetails = findViewById(R.id.btnSaveDetails);
        btnBackDetails = findViewById(R.id.btnBackDetails);
        btnDeleteDetails = findViewById(R.id.btnDeleteDEtails);
        etAmountDetails = findViewById(R.id.etAmount);
        etDescriptionDetails = findViewById(R.id.etDescription);
        tvID = findViewById(R.id.tvEditDetailsId);

        Log.i("DetailsTicket", detailsTicket.toString());
        tvID.setText(String.valueOf(detailsTicket.getId()));
        etDescriptionDetails.setText((detailsTicket.getDescription().toString()));
        etAmountDetails.setText(String.valueOf(detailsTicket.getAmount()).toString());

// newDetailsTicket.setAmount(Double.parseDouble(etPrecioDetails.getText().toString()));


        GoldenRaceApiService apiService = GoldenRaceApiClient.getClient().create(GoldenRaceApiService.class);

        btnSaveDetails.setOnClickListener(v -> {
            detailsTicket.setAmount(Double.valueOf(etAmountDetails.getText().toString()));
            detailsTicket.setDescription(etDescriptionDetails.getText().toString());


            Call <DetailsTicket>updateDetails = apiService.updateDetail(detailsTicket.getId(),detailsTicket);


            updateDetails.enqueue(new Callback<DetailsTicket>() {
                @Override
                public void onResponse(Call <DetailsTicket>call, Response <DetailsTicket>response) {
                    if(response.isSuccessful()){
                        Toast.makeText(getApplicationContext(), "Detalle editado correctamente", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call <DetailsTicket>call, Throwable t) {

                }
            });
            Intent back = new Intent(getApplicationContext(), DetailsTicket2.class);
            back.putExtra("ticket",ticket);
            back.putExtra("detailsTicket", detailsTicket);
            startActivity(back);
        });

        btnBackDetails.setOnClickListener(v -> onBackPressed());

        btnDeleteDetails.setOnClickListener(v -> {

            Call delete = apiService.deleteDetail(detailsTicket.getId());

            delete.enqueue(new Callback<DetailsTicket>() {
                @Override
                public void onResponse(Call<DetailsTicket> call, Response <DetailsTicket>response) {
                    if (response.isSuccessful()) {
                        DetailsTicket details = response.body();
                        Toast.makeText(getApplicationContext(), "Detalle eliminado correctamente", Toast.LENGTH_SHORT).show();

                       // updateTotalAmount();

                    } else {
                        Log.i("Ticket Error", "Error al cargar el detalle del ticket");
                    }
                }

                @Override
                public void onFailure(Call<DetailsTicket> call, Throwable t) {

                }
            });

            Intent back22 = new Intent(getApplicationContext(), DetailsTicket2.class);
            back22.putExtra("ticket",ticket);
            back22.putExtra("detailsTicket", detailsTicket);
            startActivity(back22);



        });

    }
}
