package es.iescarrillo.tickets_marp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
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

public class DetailsTicket2 extends AppCompatActivity {

    //Declaramos componentes
    Button btnEdit,btnDelete,btnInsertar,btnBack2;
    Ticket ticket;

    private ArrayList<DetailsTicket> details;
    ListView lvDetailsTickets;

    DetailsTicketsAdapter adapter;

    GoldenRaceApiService apiService;
    TextView tvAmount;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_ticket2);


        //Inicializamos componentes
        btnInsertar = findViewById(R.id.btnInsertar);
        btnBack2 = findViewById(R.id.btnback2);
        btnEdit=findViewById(R.id.btnEdit);
        btnDelete = findViewById(R.id.btnDelete);
        lvDetailsTickets = findViewById(R.id.lvDetailsTickets);


        //Recuperamos datos del intent
         ticket = (Ticket) getIntent().getSerializableExtra("ticket");

         //Creamos un objeto de DetailsTicket
        DetailsTicket detailsTicket = new DetailsTicket();

        //Incializacion de componentes
        TextView tvDetailsId = findViewById(R.id.tvDetailsId);
        TextView tvDescription = findViewById(R.id.tvDescription);
        tvAmount = findViewById(R.id.tvAmount);

        //Comprobamos que el ticket es distinto de nulo y le guardamos los datos en sus respectivos campos
        if (ticket != null) {
            tvDetailsId.setText(String.valueOf(ticket.getId()));
            tvDescription.setText(String.valueOf(ticket.getCreationDate()));
            tvAmount.setText(String.valueOf(ticket.getTotalAmount()));
        }


        //Declaramos un arraylist
        details = new ArrayList<>();


        //Boton de editar al que el que nos llevará a la clase de EditTicket
        btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), EditTicket.class);
            intent.putExtra("ticket", ticket);
            startActivity(intent);
        });

        //Boton de eliminar Ticket que al hacer clic elimina el ticket de la api y una vez eliminado volvera a la MainActivity
        btnDelete.setOnClickListener(v -> {


            //Llamada al servicicio
            Call<Void> call = apiService.deleteTicket(ticket.getId());

            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if(response.isSuccessful()){
                        Void deleteTicket = response.body();

                        //Toast informativo para el usuario
                        Toast.makeText(getApplicationContext(), "Ticket eliminado correctamente", Toast.LENGTH_SHORT).show();


                    }else{
                        Log.i("Ticket Error", "Error to upload Ticket");
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {

                }
            });

            //Intent para una vez que se elimine el Ticket retroceda hacia la pantalla principal, llevadonos el objeto ticket ya que si no da errores
            Intent back = new Intent(getApplicationContext(), MainActivity.class);
            back.putExtra("ticket", ticket);
            startActivity(back);


        });


        //Aqui vamos a cargar los detalles de los ticket

        //Declaramos adaptador
        adapter = new DetailsTicketsAdapter(getApplicationContext(),details);

        //Declaramos servicio
        apiService = GoldenRaceApiClient.getClient().create(GoldenRaceApiService.class);


        //Llamada al servicio
        Call call2 = apiService.getDetails(ticket.getId());


        call2.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {

                //Aqui introducirmos un if cargando los datos en un arrayList en el que lo recorre y actualizamos los precios.
                if(response.isSuccessful()){
                    ArrayList<DetailsTicket> detailsList = (ArrayList<DetailsTicket>) response.body();

                    for (DetailsTicket detailsTicket1 : detailsList){
                        details.add(detailsTicket1);

                    }
                    lvDetailsTickets.setAdapter(adapter);

                    calcularTotalAmoun();
                }

            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });

        //Funcionalidad al boton de insertar que nos llevará a la activity de InsertDetails para insertar Detalles de los ticket
        btnInsertar.setOnClickListener(v -> {
            Intent insert = new Intent(getApplicationContext(), InsertDetails.class);
            insert.putExtra("ticket",ticket);
            startActivity(insert);
        });

        //Boton de volver que volvera a la MainActivity
        btnBack2.setOnClickListener(v -> {
            Intent volver2 = new Intent(getApplicationContext(), MainActivity.class);
            volver2.putExtra("ticket", ticket);
            volver2.putExtra("detailsTickets", detailsTicket );
            startActivity(volver2);
        });



        //ListView que mostrará todos los detalles de los ticket
        lvDetailsTickets.setOnItemClickListener((parent, view, position, id) -> {

            DetailsTicket selectedDetailsTicket = (DetailsTicket) parent.getItemAtPosition(position);
            selectedDetailsTicket.setTicket(ticket);

            Intent intent = new Intent(getApplicationContext(), EditDetails.class);
            intent.putExtra("detailsTicket",selectedDetailsTicket);
            intent.putExtra("ticket",ticket);
            startActivity(intent);
        });

    }


    //Metodo que suma todos los amount de los detalles de los ticket y modifica el atributo totalAmount de los Ticket a la suma total de estos

    public void calcularTotalAmoun(){
        double Amount = 0.0;

        if(!details.isEmpty()){

            for (DetailsTicket de : details){
                Amount += de.getAmount();
            }
            ticket.setTotalAmount(Amount);
            tvAmount.setText(ticket.getTotalAmount().toString());

            Call updateAmount = apiService.updateTicket(ticket.getId(), ticket);

            updateAmount.enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) {
                    if (response.isSuccessful()){
                        Toast.makeText(getApplicationContext(), "Precio total actualizado correctamente", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call call, Throwable t) {

                }
            });
        }
    }


}


