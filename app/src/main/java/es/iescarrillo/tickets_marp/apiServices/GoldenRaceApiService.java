package es.iescarrillo.tickets_marp.apiServices;

import java.util.List;

import es.iescarrillo.tickets_marp.activities.DetailsTicket2;
import es.iescarrillo.tickets_marp.models.DetailsTicket;
import es.iescarrillo.tickets_marp.models.Ticket;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface GoldenRaceApiService {

    @GET("api/tickets/")
    Call<List<Ticket>> getTickets();

    @GET("api2/detailTickets/{id}")
    Call<List<DetailsTicket>> getDetails(@Path("id")Integer id);


    /**
     * Publica un nuevo ticket en el servidor.
     *
     * @param ticket El objeto Ticket que se enviará en el cuerpo de la solicitud, debe ir con el ID a 0.
     * @return Una llamada de Retrofit que contiene el Ticket creado, con el ID autoasignado.
     */
    @POST("api/ticket/")
    Call<Ticket> postTicket(@Body Ticket ticket);

    /**
     * Elimina un ticket del servidor.
     *
     * @param ticketId El ID del ticket que se eliminará.
     * @return Una llamada de Retrofit sin cuerpo de respuesta, indicando éxito o fallo.
     */
    @DELETE("api/ticket/{id}")
    Call<Void> deleteTicket(@Path("id") int ticketId);


    /**
     * Actualiza un ticket existente en el servidor.
     *@param ticketId El ID del ticket que se actualizará
     * @param ticket   El objeto Ticket actualizado que se enviará en el cuerpo de la solicitud.
     * @return Una llamada de Retrofit que contiene el Ticket actualizado.
     */
    @PUT("api/ticket/{id}")
    Call<Ticket> updateTicket(@Path("id") int ticketId, @Body Ticket ticket);


    @POST("api2/detailTicket/")
    Call<DetailsTicket> postDetail(@Body DetailsTicket detailsTicket);

    @DELETE("api2/detailTicket/{id}")
    Call<Void> deleteDetail(@Path("id") int detailId);



    @PUT("api2/detailTicket/{id}")
    Call<DetailsTicket> updateDetail(@Path("id") int detailId, @Body DetailsTicket detailsTicket);

}
