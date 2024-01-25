package es.iescarrillo.tickets_marp.apiClients;

import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GoldenRaceApiClient {


    private static final String BASE_URL = "https://goldenraceapi.onrender.com/golden-race/";

    private static Retrofit retrofit = null;

    public static Retrofit getClient(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().serializeNulls().create()))
                    .build();

        }
        return retrofit;
    }
}
