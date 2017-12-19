package com.example.user.surfstatus;

/**
 * Created by user on 12/7/17.
 */

public class Praia {

    protected int praiaId;
    protected String nomePraia;
    protected String condicaoActual;
    protected String urlPraia;

    public Praia(int id) {
        praiaId = id;
    }
    public Praia() {
        praiaId = -1;
    }
    public void setId(int id) {
        praiaId = id;
    }
    public int getId() {
        return praiaId;
    }
    public void setNomePraia(String s) {
        nomePraia = s;
    }
    public String getNomePraia() {
        return nomePraia;
    }
    public void setCondicaoActual(String s) {
        condicaoActual = s;
    }
    public void setUrlPraia(String s){
        urlPraia = s;
    }
    public String getUrlPraia(){
        return urlPraia;
    }
}
