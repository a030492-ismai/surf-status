package com.example.user.surfstatus;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.user.surfstatus.Praia.listaPraias;
import static com.example.user.surfstatus.Praia.listaPraiasListar;

public class Main2Activity extends AppCompatActivity {
    ListView list;
//    public static List<Praia> listaPraias = new ArrayList<>();
    FloatingActionButton bActualizarPraias;
    Button bAdicionarPraias;
    ArrayAdapter<Praia> adap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        list = findViewById(android.R.id.list);
        bActualizarPraias = findViewById(R.id.bActualizarPraias);
        bAdicionarPraias = (Button) findViewById(R.id.bAdicionarPraias);
//        listaPraias.add(-1, new Praia());

        adap = new ArrayAdapter<Praia>(this, android.R.layout.simple_list_item_1, listaPraiasListar);
   //     list.setAdapter(adap);


        bActualizarPraias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(Praia praia : listaPraiasListar){
                    actualizarCondicoes(praia);
                }
//                actualizarListaPraias();

            }
        });


        if(listaPraias.size() == 0){
            Toast.makeText(this, "nao tem praias adicionadas", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.bAdicionarPraias) {
            Intent main1Intent = new Intent(this,MainActivity.class);
            startActivity(main1Intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

//    public void actualizarListaPraias(){
//
//
//        ArrayAdapter<Praia> adap = new ArrayAdapter<Praia>(this, android.R.layout.simple_list_item_1, listaPraiasListar);
//        list.setAdapter(adap);
//
//    }

    @SuppressLint("StaticFieldLeak")
    public void actualizarCondicoes(final Praia praia){
        new AsyncTask<String, Long , String[]>() {

            @Override
            protected String[] doInBackground(String... s) {
                Document doc;
                String condicao = null;
                try {
                    doc = Jsoup.connect(s[0]).get();
                    condicao = doc.select("div.classificationDescription").first().text();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                s[0] = condicao;

                return s;
            }

            @Override
            protected void onPostExecute(String[] s) {


                praia.setCondicaoActual(s[0]);

                list.setAdapter(adap);

            }
        }.execute(praia.getUrlPraia());
    }

    @Override
    public void onResume(){
        super.onResume();
        list.setAdapter(adap);
    }

}
