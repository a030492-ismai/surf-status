package com.example.user.surfstatus;


public class Praia {

    protected int praiaId;
    protected String nomePraia;
    //protected String condicaoActual;
    protected String urlPraia;
    protected boolean listar;

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
//    public void setCondicaoActual(String s) {
//        condicaoActual = s;
//    }
    public void setUrlPraia(String s){
        urlPraia = s;
    }
    public String getUrlPraia(){
        return urlPraia;
    }
    public void setListar(boolean b){listar = b; }
    public boolean getListar(){return listar; }


}
