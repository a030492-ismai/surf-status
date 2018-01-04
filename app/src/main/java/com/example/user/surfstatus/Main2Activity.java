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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class Main2Activity extends AppCompatActivity {
    ListView list;
    FloatingActionButton bActualizarPraias;
    Button bAdicionarPraias;
    AdaptadorBaseDados bd;
    ArrayAdapter adap;
    ArrayList<String[]> listaPraias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        list = findViewById(android.R.id.list);
        list.setOnItemClickListener(
                new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View view,
                                            int position, long id) {
                        Object o = list.getItemAtPosition(position);
//                        ecraDetalhes(Main3Activity.class, listaPraiasListar.get(position).getUrlPraia());
                    }
                }
        );
        bActualizarPraias = findViewById(R.id.bActualizarPraias);
        bAdicionarPraias = findViewById(R.id.bAdicionarPraias);
        bd = new AdaptadorBaseDados(this).open();

        setAdap();



        bActualizarPraias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateCondicoes();

            }
        });


    }

    @SuppressLint("StaticFieldLeak")
    private void updateCondicoes() {

        for (final String[] praia : listaPraias){

            new AsyncTask<String, Long , String[]>() {

                @Override
                protected void onPreExecute(){
                    praia[1] = "a carregar...";
                    list.setAdapter(adap);
                }
            @Override
            protected String[] doInBackground(String... s) {
                Document doc;
                try {
                    doc = Jsoup.connect(s[2]).get();
                    s[1] = doc.select("div.classificationDescription").first().text();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return s;
            }

            @Override
            protected void onPostExecute(String[] s) {
                bd.updateCondicaoPraia(s);
                list.setAdapter(adap);
            }
            }.execute(praia);
        }

    }

    private void setAdap() {
        listaPraias = new ArrayList<>(bd.getSize());
        listaPraias = bd.getPraiasListar();

        adap  = new ArrayAdapter(this, android.R.layout.simple_list_item_2, android.R.id.text1, listaPraias){
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                View view = super.getView(position, convertView, parent);
                TextView text1 = view.findViewById(android.R.id.text1);
                TextView text2 = view.findViewById(android.R.id.text2);

                text1.setText(listaPraias.get(position)[0]);
                text2.setText(listaPraias.get(position)[1]);
                return view;
            }
        };

        list.setAdapter(adap);
    }


    @Override
    public void onResume(){
        super.onResume();
        bd.open();
        setAdap();
        list.setAdapter(adap);
    }
    @Override
    public void onPause(){
        super.onPause();
        bd.close();
    }

    @Override
    public void onStop(){
        super.onStop();
        bd.close();
    }

    private void ecraDetalhes(Class<?> ecraDetalhes, String url){
        Intent y = new Intent(this, ecraDetalhes);
        y.putExtra("url", url);
        startActivity(y);
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

}
