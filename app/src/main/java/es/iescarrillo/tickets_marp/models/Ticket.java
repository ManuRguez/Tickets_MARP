package es.iescarrillo.tickets_marp.models;

import android.telecom.Call;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import kotlin.jvm.JvmSerializableLambda;

public class Ticket implements Serializable {

    @SerializedName("creationDate")
    @Expose
    private String creationDate;

    @SerializedName("totalAmount")
    @Expose
    private Double totalAmount;

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("version")
    @Expose
    private int version;


    private List<DetailsTicket> detailTicketList;


    public Ticket(){

    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public List<DetailsTicket> getDetailTicketList() {
        return detailTicketList;
    }

    public void setDetailTicketList(List<DetailsTicket> detailTicketList) {
        this.detailTicketList = detailTicketList;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "creationDate='" + creationDate + '\'' +
                ", totalAmount=" + totalAmount +
                ", id=" + id +
                ", version=" + version +
                ", detailTicketList=" + detailTicketList +
                '}';
    }
}