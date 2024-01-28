package es.iescarrillo.tickets_marp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import es.iescarrillo.tickets_marp.R;
import es.iescarrillo.tickets_marp.apiClients.GoldenRaceApiClient;
import es.iescarrillo.tickets_marp.apiServices.GoldenRaceApiService;
import es.iescarrillo.tickets_marp.models.Ticket;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InsertTicket extends AppCompatActivity {

    EditText etPrecio;
    Button  btnAdd,btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_ticket);

        etPrecio = findViewById(R.id.etPrecio);
        btnAdd =findViewById(R.id.btnAdd);
        btnCancel = findViewById(R.id.btnCancel);

        GoldenRaceApiService apiService = GoldenRaceApiClient.getClient().create(GoldenRaceApiService.class);

        Ticket ticket = new Ticket();


        LocalDateTime now = LocalDateTime.now();

        String pattern = "dd/MM/yyyy HH:mm:ss";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

        String formatedDate = now.format(formatter);

        btnAdd.setOnClickListener(v -> {

            ticket.setId(0);
            ticket.setTotalAmount(Double.parseDouble(etPrecio.getText().toString()));


            ticket.setCreationDate(formatedDate);

            Call<Ticket> call = apiService.postTicket(ticket);


            call.enqueue(new Callback<Ticket>() {
                @Override
                public void onResponse(Call<Ticket> call, Response<Ticket> response) {

                    if(response.isSuccessful()){
                        Ticket createdTicket = response.body();
                        Log.i("Successfull ticket loaded", createdTicket.toString());
                    }else{
                        Log.i("Ticket Error", "Error to upload Ticket");
                    }
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                }
            });

            Intent back = new Intent(getApplicationContext(), MainActivity.class);
            back.putExtra("ticket",ticket);
            startActivity(back);
        });

        btnCancel.setOnClickListener(v -> {
            Intent back2 = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(back2);
        });

    }
}
