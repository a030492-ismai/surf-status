package com.example.user.surfstatus;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class MainActivity extends Activity {

    Button botao;
    TextView text;

    String[] condicoesPraias;

    Praia praiaTeste = new Praia(1);
    Praia praiaTeste2 = new Praia(2);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        praiaTeste.setNomePraia("praia de teste");
        praiaTeste.setUrlPraia("http://beachcam.meo.pt/reports/praia-do-moledo/");
        praiaTeste2.setNomePraia("praia de teste 2");
        praiaTeste2.setUrlPraia("http://beachcam.meo.pt/reports/praia-da-mariana/");


        botao = findViewById(R.id.button);
        text = findViewById(R.id.textView);
        condicoesPraias = new String[0];



        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        //        for(int i = 0; i < 1; ++i) {
        //            new AsyncT().execute(i);
        //        }
                getBeachReportAll();
            }
        });

    }

    protected void getBeachReportAll(){

        for(int i = 0; i < 1; ++i) {
            new AsyncT().execute(praiaTeste.getUrlPraia());
        }

    }

    public class AsyncT extends AsyncTask<String, Long , String> {

        @Override
        protected void onPreExecute() {
            text.setText("a carregar...");
        }

        @Override
        protected String doInBackground(String... s) {
            //return Comunicar.contactar2("beachcam.sapo.pt", "/reports/praia-do-moledo/", 80);
            Document doc;
            String condicao = null;
            try {
                doc = Jsoup.connect(s[0]).get();
                condicao = doc.select("div.classificationDescription").first().text();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return condicao;
        }

        @Override
        protected void onProgressUpdate(Long... progress) {

        }

        @Override
        protected void onPostExecute(String finalString) {

            text.setText(finalString);

        }
    }
}
