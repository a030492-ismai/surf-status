package com.example.user.surfstatus;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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



//    private Cursor obterTodosRegistos() {
//        String[] colunas = new String[3];
//        colunas[0] = "nome";
//        colunas[1] = "morada";
//        colunas[2] = "telefone";
//        return database.query("contactos", colunas, null, null, null, null, "nome");
//    }
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
//    public int updateNome(String nomeAntigo, String nome, String morada, String telefone) {
//        String whereClause = "nome = ?";
//        String[] whereArgs = new String[1];
//        whereArgs[0] = nomeAntigo;
//        ContentValues values = new ContentValues();
//        values.put("nome", nome);
//        values.put("morada", morada);
//        values.put("telefone", telefone);
//        return database.update("contactos", values, whereClause, whereArgs);
//    }
}