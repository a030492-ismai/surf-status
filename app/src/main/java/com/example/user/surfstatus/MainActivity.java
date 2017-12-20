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
    TextView text2;
    TextView[] texts = new TextView[2];

    String[] condicoesPraias;

    Praia praiaTeste = new Praia(1);
    Praia praiaTeste2 = new Praia(2);
    Praia[] praias;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        praiaTeste.setNomePraia("praia de teste");
        praiaTeste.setUrlPraia("http://beachcam.meo.pt/reports/praia-do-moledo/");
        praiaTeste2.setNomePraia("praia de teste 2");
        praiaTeste2.setUrlPraia("http://beachcam.meo.pt/reports/praia-da-mariana/");

        praias = new Praia[2];
        praias[0] = praiaTeste;
        praias[1] = praiaTeste2;


        botao = findViewById(R.id.button);
        text = findViewById(R.id.textView);
        text2 = findViewById(R.id.textView2);
        condicoesPraias = new String[0];

        texts[0] = text;
        texts[1] = text2;


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

        for(int i = 0; i < 2; ++i) {
            texts[i].setText("a carregar...");
            new AsyncT().execute(praias[i].getUrlPraia(), ""+praias[i].getId());
        }

    }

    public class AsyncT extends AsyncTask<String, Long , String[]> {

        @Override
        protected void onPreExecute() {
//            text.setText("a carregar...");
        }

        @Override
        protected String[] doInBackground(String... s) {
            //return Comunicar.contactar2("beachcam.sapo.pt", "/reports/praia-do-moledo/", 80);
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
        protected void onProgressUpdate(Long... progress) {

        }

        @Override
        protected void onPostExecute(String[] finalString) {
            texts[Integer.parseInt(finalString[1]) - 1].setText(finalString[0]);

        }
    }
}
