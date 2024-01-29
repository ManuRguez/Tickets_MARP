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

    //Declaracion de componentes
    EditText etPrecio;
    Button  btnAdd,btnCancel;
    DetailsTicket2 detailsTicket2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_ticket);


        //Inicializacion de componentes
        etPrecio = findViewById(R.id.etPrecio);
        btnAdd =findViewById(R.id.btnAdd);
        btnCancel = findViewById(R.id.btnCancel);


        //Llamada al servicio
        GoldenRaceApiService apiService = GoldenRaceApiClient.getClient().create(GoldenRaceApiService.class);


        //Creacion de un Ticket
        Ticket ticket = new Ticket();


        //Creamos una variable LocalDatetime para y le damos el formato necesario y se lo introducimos para que asi no de error
         LocalDateTime now = LocalDateTime.now();

        String pattern = "dd/MM/yyyy HH:mm:ss";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

        String formatedDate = now.format(formatter);


        //Este boton inserta Ticket en la Api
        btnAdd.setOnClickListener(v -> {

            //Setemos Id a 0, le pasamos la fecha formateada, y el total amount lo seteamos al EditText
            ticket.setId(0);
            ticket.setTotalAmount(Double.parseDouble(etPrecio.getText().toString()));


            ticket.setCreationDate(formatedDate);


            //Llamada al servicio
            Call<Ticket> call = apiService.postTicket(ticket);


            call.enqueue(new Callback<Ticket>() {
                @Override
                public void onResponse(Call<Ticket> call, Response<Ticket> response) {

                    if(response.isSuccessful()){
                        Ticket createdTicket = response.body();
                        Log.i("Successfull ticket loaded", createdTicket.toString());
                        Toast.makeText(getApplicationContext(), "Ticket creado correctamente", Toast.LENGTH_SHORT).show();
                    }else{
                        Log.i("Ticket Error", "Error to upload Ticket");
                    }
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                }
            });

            //Intent que cuando se añade el Tikcet nos dirigie a la Main Activity
            Intent back = new Intent(getApplicationContext(), MainActivity.class);
            back.putExtra("ticket",ticket);
            startActivity(back);
        });


        //Boton de cancelar que nos llevara a la MainActivity y no se llevará a cabo la inserción
        btnCancel.setOnClickListener(v -> {
            Intent back2 = new Intent(getApplicationContext(), MainActivity.class);
            back2.putExtra("ticket", ticket);
            startActivity(back2);
        });

    }
}
