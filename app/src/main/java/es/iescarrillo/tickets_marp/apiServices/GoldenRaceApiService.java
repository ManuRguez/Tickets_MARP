package es.iescarrillo.tickets_marp.apiServices;

import java.util.List;

import es.iescarrillo.tickets_marp.models.Ticket;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface GoldenRaceApiService {

    @GET ("tickets/")
    Call<List<Ticket>> getTickets();


    @POST ("tickets/")
    Call <Ticket> postTicket(@Body Ticket ticket);

}
