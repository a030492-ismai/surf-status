package com.example.user.surfstatus;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    Button botao;
    TextView text;
//    TextView text2;
//    TextView[] texts = new TextView[2];

//    String[] condicoesPraias;
//
//    Praia praiaTeste = new Praia(1);
//    Praia praiaTeste2 = new Praia(2);
//    Praia[] praias;
    List<Praia> listaPraias = new ArrayList<>();
    String urlListaPraias = "http://beachcam.meo.pt/reports/";
    Document doc;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        praiaTeste.setNomePraia("praia de teste");
//        praiaTeste.setUrlPraia("http://beachcam.meo.pt/reports/praia-do-moledo/");
//        praiaTeste2.setNomePraia("praia de teste 2");
//        praiaTeste2.setUrlPraia("http://beachcam.meo.pt/reports/praia-da-mariana/");
//
//        praias = new Praia[2];
//        praias[0] = praiaTeste;
//        praias[1] = praiaTeste2;


        botao = findViewById(R.id.button);
        text = findViewById(R.id.textView);
//        text2 = findViewById(R.id.textView2);
//        condicoesPraias = new String[0];

//        texts[0] = text;
//        texts[1] = text2;


        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        //        for(int i = 0; i < 1; ++i) {
        //            new AsyncT().execute(i);
        //        }
//                actualizarReports();
                actualizarListaPraias();
            }
        });

    }

    protected void actualizarReports(){
        text.setText("");
        for(int i = 0; i < listaPraias.size() -1; ++i) {
//            texts[i].setText("a carregar...");
//            new AsyncT().execute(praias[i].getUrlPraia(), ""+praias[i].getId(), "getBeachReport");
            text.append(listaPraias.get(i).getNomePraia() + "\n");

        }

    }

//    public class AsyncT extends AsyncTask<String, Long , String[]> {
//
//        @Override
//        protected void onPreExecute() {
////            text.setText("a carregar...");
//        }
//
//        @Override
//        protected String[] doInBackground(String... s) {
//
//            if(s[2] == "getBeachReport") {
//                Document doc;
//                String condicao = null;
//                try {
//                    doc = Jsoup.connect(s[0]).get();
//                    condicao = doc.select("div.classificationDescription").first().text();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                s[0] = condicao;
//
//                return s;
//            }
//
//        }
//
//        @Override
//        protected void onProgressUpdate(Long... progress) {
//
//        }
//
//        @Override
//        protected void onPostExecute(String[] finalString) {
////            texts[Integer.parseInt(finalString[1]) - 1].setText(finalString[0]);
////            texts[0].setText(finalString[0]);
////            texts[1].setText(finalString[1]);
//        }
//    }

    @SuppressLint("StaticFieldLeak")
    public void actualizarListaPraias() {

            new AsyncTask<String, Void, Document>() {
                @Override
                protected void onPreExecute() {}
                @Override
                protected Document doInBackground(String... s) {
                    Document fulldoc = null;
//                    String[] praia = new String[2];
                    try {
                        fulldoc = Jsoup.connect(s[0]).get();
//                        praia[0] = doc.select(".beachesContainer a").get(0).text();
//                        praia[1] = "http://beachcam.meo.pt" + doc.select(".beachesContainer a").get(0).attr("href").toString();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return fulldoc;
                }
                @Override
                protected void onProgressUpdate(Void... voids) {}

                @Override
                protected void onPostExecute(Document fulldoc) {
                    actualizarListaPraiasCont(fulldoc);
                }
            }.execute(urlListaPraias);
    }

    private void actualizarListaPraiasCont(Document fulldoc) {
        Elements els = fulldoc.select(".beachesContainer a");
        for(int k = 0; k < els.size() - 1; ++k){
            Praia umaPraia = new Praia(k);
            umaPraia.setNomePraia(els.get(k).text());
            umaPraia.setUrlPraia("http://beachcam.meo.pt" + els.get(k).attr("href"));
            listaPraias.add(umaPraia);
        }
        actualizarBDPraias(listaPraias);
        actualizarReports();
    }

    private void actualizarBDPraias(List<Praia> listaPraias) {
//        TODO
//        BD
    }


}



