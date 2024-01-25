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
import es.iescarrillo.tickets_marp.models.Ticket;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditTicket extends AppCompatActivity {

    Button btnBack,btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_ticket);

        Ticket ticket = (Ticket) getIntent().getSerializableExtra("ticket");

        btnSave = findViewById(R.id.btnSave);
        btnBack = findViewById(R.id.btnBack);
        TextView tvDetailsId = findViewById(R.id.tvDetailsId);
        TextView tvDescription = findViewById(R.id.tvDescription);
         EditText etamount = findViewById(R.id.etAmount);

        tvDetailsId.setText(ticket.getId().toString());
        tvDescription.setText(ticket.getCreationDate().toString());


        GoldenRaceApiService apiService = GoldenRaceApiClient.getClient().create(GoldenRaceApiService.class);

        btnSave.setOnClickListener(v -> {

            ticket.setTotalAmount(Double.parseDouble(etamount.getText().toString()));

            Call editTicket = apiService.updateTicket(ticket.getId(), ticket);


            editTicket.enqueue(new Callback<Ticket>() {
                @Override
                public void onResponse(Call<Ticket> call, Response<Ticket> response) {
                    if(response.isSuccessful()){
                        Ticket editTicket  = response.body();
                        Log.i("Successfull ticket loaded", editTicket.toString());
                        Toast.makeText(getApplicationContext(), "Ticket edtitado correctamente", Toast.LENGTH_SHORT).show();
                    }else{
                        Log.i("Ticket Error", "Error to upload Ticket");
                    }
                }

                @Override
                public void onFailure(Call call, Throwable t) {

                }
            });
            Intent back = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(back);
        });

    }
}