package com.example.user.surfstatus;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorBaseDados {
    private AjudaUsoBaseDados dbHelper;
    private SQLiteDatabase database;
    public AdaptadorBaseDados(Context context) {
        dbHelper = new AjudaUsoBaseDados(context.getApplicationContext());
    }
    public AdaptadorBaseDados open() {
        database = dbHelper.getWritableDatabase();
        return this;
    }
    public void close() {
        dbHelper.close();
    }

    public void inserirPraia(String oNome, String oUrl) {
        ContentValues values = new ContentValues() ;
        values.put("nomePraia", oNome);
        values.put("url", oUrl);
        database.insert("praias", null, values);
    }

    public void dropPraias(){
        database.execSQL("DROP TABLE IF EXISTS praias");
        database.execSQL("CREATE TABLE praias(_id integer primary key autoincrement, nomePraia varchar(40) unique, condicaoActual varchar(40), url varchar(60), listar bit default 0)");

    }



    private Cursor obterTodosRegistos() {
        String[] colunas = new String[5];
        colunas[0] = "_id";
        colunas[1] = "nomePraia";
        colunas[2] = "condicaoActual";
        colunas[3] = "url";
        colunas[4] = "listar";
        return database.query("praias", colunas, null, null, null, null, "_id");
    }
//    public List<String> obterTodosTelefones() {
//        ArrayList<String> telefones = new ArrayList<String>();
//        Cursor cursor = obterTodosRegistos();
//        if (cursor.moveToFirst()) {
//            do {
//                telefones.add(cursor.getString(2));
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
//        return telefones;
//    }
//    public List<String> obterTodosNomes() {
//        ArrayList<String> nomes = new ArrayList<String>();
//        Cursor cursor = obterTodosRegistos();
//        if (cursor.moveToFirst()) {
//            do {
//                nomes.add(cursor.getString(0));
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
//        return nomes;
//    }
//    public List<String> obterTodasMoradas() {
//        ArrayList<String> moradas = new ArrayList<String>();
//        Cursor cursor = obterTodosRegistos();
//        if (cursor.moveToFirst()) {
//            do {
//                moradas.add(cursor.getString(1));
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
//        return moradas;
//    }
//    public int obterTodosCampos(List<Integer> osIds, List<String> osNomes, List<String> asMoradas, List<String> osTelefones) {
//        String[] colunas = new String[4];
//        colunas[0] = "_id";
//        colunas[1] = "nome";
//        colunas[2] = "morada";
//        colunas[3] = "telefone";
//        Cursor c = database.query("contactos", colunas, null, null, null, null, null);
//
//        if (c.moveToFirst()) {
//            do {
//                osIds.add(c.getInt(0));
//                osNomes.add(c.getString(1));
//                asMoradas.add(c.getString(2));
//                osTelefones.add(c.getString(3));
//            } while (c.moveToNext());
//        }
//        c.close();
//        return osIds.size();
//    }
//    public boolean existe(String umNome) {
//        Cursor cursor = database.rawQuery(
//                "select nome from contactos where nome=?", new String[] { umNome });
//        boolean b = cursor.getCount() >= 1;
//        cursor.close();
//        return b;
//    }
//    public long insertNomeMoradaTelefone(String oNome, String aMorada, String oTelefone) {
//        ContentValues values = new ContentValues() ;
//        values.put("nome", oNome);
//        values.put("morada", aMorada);
//        values.put("telefone", oTelefone);
//        return database.insert("contactos", null, values);
//    }
//
//    public int deleteNome(String oNome) {
//        String whereClause = "nome = ?";
//        String[] whereArgs = new String[1];
//        whereArgs[0] = oNome;
//        return database.delete("contactos", whereClause, whereArgs);
//    }
//    public int deleteTodosNomes() {
//        return database.delete("contactos", null, null);
//    }
//
//    public String[] obterContacto(String nome){
//        String[] contacto = new String[3];
//        String whereClause = "nome = ?";
//        String[] whereArgs = new String[1];
//        whereArgs[0] = nome;
//        Cursor cursor = database.query("contactos", null, whereClause, whereArgs, null, null, null);
//        cursor.moveToFirst();
//
//        contacto[0] = cursor.getString(1);
//        contacto[1] = cursor.getString(2);
//        contacto[2] = cursor.getString(3);
//
//        return contacto;
//    }
//
    public void updateListar(String _nomePraia, int _listar) {
        String whereClause = "nomePraia = ?";
        String[] whereArgs = new String[1];
        whereArgs[0] = _nomePraia;
        ContentValues values = new ContentValues();
        values.put("listar", _listar);
        database.update("praias", values, whereClause, whereArgs);
    }


    public int getSize() {
        return (int)DatabaseUtils.queryNumEntries(database, "praias");
    }

//    public String[] getPraia(int i) {
//        String[] nomePraia = new String[5];
//        String whereClause = "_id = ?";
//        String whereArgs[] = new String[1];
//        whereArgs[0] = i + "";
//        Cursor cursor = database.query("praias", null, whereClause, whereArgs, null, null, null);
//        cursor.moveToFirst();
//
//        nomePraia[0] = cursor.getString(0);
//        nomePraia[1] = cursor.getString(1);
//        nomePraia[2] = cursor.getString(2);
//        nomePraia[3] = cursor.getString(3);
//        nomePraia[4] = cursor.getString(4);
//
//        return nomePraia;
//    }

    public String getNomePraia(int i) {
        String nomePraia;
        String whereClause = "_id = ?";
        String whereArgs[] = new String[1];
        whereArgs[0] = i + "";
        Cursor cursor = database.query("praias", null, whereClause, whereArgs, null, null, null);
        cursor.moveToFirst();

        nomePraia = cursor.getString(1);
        cursor.close();

        return nomePraia;
    }

    public String getUrl(String s) {
        String url;
        String whereClause = "nomePraia = ?";
        String whereArgs[] = new String[1];
        whereArgs[0] = s;
        Cursor cursor = database.query("praias", null, whereClause, whereArgs, null, null, null);
        cursor.moveToFirst();

        url = cursor.getString(3);
        cursor.close();

        return url;
    }

    public boolean getListarPraia(int i){
        boolean listarPraia = false;
        String whereClause = "_id = ?";
        String whereArgs[] = new String[1];
        whereArgs[0] = i + "";
        Cursor cursor = database.query("praias", null, whereClause, whereArgs, null, null, null);
        cursor.moveToFirst();

        if(cursor.getString(4).equals("1")){
            listarPraia = true;
        }
        else{
            listarPraia = false;
        }

        cursor.close();

        return listarPraia;
    }

    public ArrayList<String[]> getPraiasListar(){
        ArrayList<String[]> praiasListar = new ArrayList<>();

        Cursor cursor = obterTodosRegistos();
        if (cursor.moveToFirst()) {
            do {
                if(cursor.getString(4).equals("1")){
                    String[] s = new String[4];
                    s[0] = cursor.getString(1) +"";//nome
                    s[1] = cursor.getString(2) +"";//condicao
                    s[2] = cursor.getString(3) +"";//url
                    s[3] = cursor.getString(4) +"";//listar
                    praiasListar.add(s);
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        return praiasListar;
    }

    public void updateCondicaoPraia(String[] s) {
        String whereClause = "nomePraia = ?";
        String[] whereArgs = new String[1];
        whereArgs[0] = s[0];
        ContentValues values = new ContentValues();
        values.put("condicaoActual", s[1]);
        database.update("praias", values, whereClause, whereArgs);
    }


}