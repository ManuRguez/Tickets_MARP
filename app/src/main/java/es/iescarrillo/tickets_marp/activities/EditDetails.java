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


    //Declaracion de los componentes
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


        //Recuperamos los datos del intent tanto de los detalles y de los ticket
        Intent intent = getIntent();

        detailsTicket = new DetailsTicket();
        if(intent != null){
            detailsTicket = (DetailsTicket) intent.getSerializableExtra("detailsTicket");
        }

        ticket = new Ticket();
        if(intent != null){
            ticket = (Ticket) intent.getSerializableExtra("ticket");
        }

        //Inicializacion de los componentes
        btnSaveDetails = findViewById(R.id.btnSaveDetails);
        btnBackDetails = findViewById(R.id.btnBackDetails);
        btnDeleteDetails = findViewById(R.id.btnDeleteDEtails);
        etAmountDetails = findViewById(R.id.etAmount);
        etDescriptionDetails = findViewById(R.id.etDescription);
        tvID = findViewById(R.id.tvEditDetailsId);

        Log.i("DetailsTicket", detailsTicket.toString());

        //Setamos los atributos introducimos un TextView que no se pueda modificar y dos Editque son los atributos que vamos  poder modificar
        tvID.setText(String.valueOf(detailsTicket.getId()));
        etDescriptionDetails.setText((detailsTicket.getDescription().toString()));
        etAmountDetails.setText(String.valueOf(detailsTicket.getAmount()).toString());


        //Declaracion del servicio
        GoldenRaceApiService apiService = GoldenRaceApiClient.getClient().create(GoldenRaceApiService.class);

        //Le damos funcionalidad al boton de Guardar  este boton guarda la edicion de los detalles de los Tickets
        btnSaveDetails.setOnClickListener(v -> {
            detailsTicket.setAmount(Double.valueOf(etAmountDetails.getText().toString()));
            detailsTicket.setDescription(etDescriptionDetails.getText().toString());

            //Llamada al servicio de Update Details
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

            //Intent que al realizar la modificación y te devuelve a la pantalla de DetailsTicket2
            Intent back = new Intent(getApplicationContext(), DetailsTicket2.class);
            back.putExtra("ticket",ticket);
            back.putExtra("detailsTicket", detailsTicket);
            startActivity(back);
        });

        //Boton de volver que te lleva a la pantalla de la que volvemos
        btnBackDetails.setOnClickListener(v -> onBackPressed());

        //Boton de borrar detalles de los Tickets
        btnDeleteDetails.setOnClickListener(v -> {

            //Llamada al servicio
            Call delete = apiService.deleteDetail(detailsTicket.getId());

            delete.enqueue(new Callback<DetailsTicket>() {
                @Override
                public void onResponse(Call<DetailsTicket> call, Response <DetailsTicket>response) {
                    if (response.isSuccessful()) {
                        DetailsTicket details = response.body();
                        Toast.makeText(getApplicationContext(), "Detalle eliminado correctamente", Toast.LENGTH_SHORT).show();

                    } else {
                        Log.i("Ticket Error", "Error al cargar el detalle del ticket");
                    }
                }

                @Override
                public void onFailure(Call<DetailsTicket> call, Throwable t) {

                }
            });


            //Intent que una vez que realizas el borrado te lleva hacia la vetnana DetailsTicket2
            Intent back22 = new Intent(getApplicationContext(), DetailsTicket2.class);
            back22.putExtra("ticket",ticket);
            back22.putExtra("detailsTicket", detailsTicket);
            startActivity(back22);



        });

    }
}
