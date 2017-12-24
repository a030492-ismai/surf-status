package com.example.user.surfstatus;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ListActivity {

    Button bActualizar;
    ListView list;

    String urlListaPraias = "http://beachcam.meo.pt/reports/";
    List<Praia> listaPraias = new ArrayList<>();
    ArrayList<String> arraylistPraias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bActualizar = findViewById(R.id.bActualizar);
        list = getListView();

        ArrayList<String> arraylistPraias = new ArrayList<>(listaPraias.size());
        for(Object object : listaPraias){
            arraylistPraias.add(object != null ? object.toString() : null);
        }
//        ArrayAdapter<String> adap = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arraylistPraias);
//        setListAdapter(adap);
        ToggleButtonListAdapter adap = new ToggleButtonListAdapter(this, arraylistPraias);
        list.setAdapter(adap);

        bActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actualizarListaPraias();
            }
        });

    }

    @Override
    public void onListItemClick(ListView parent, View v, int position, long id) {
//        ecraDetalhes(Main3Activity.class, osItensDaLista.get(position));
    }

    protected void actualizarReports(){
        arraylistPraias = new ArrayList<>(listaPraias.size());
        for(int i = 0; i < listaPraias.size() -1; ++i){
            arraylistPraias.add(listaPraias.get(i).getNomePraia());
        }
//        ArrayAdapter<String> adap = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arraylistPraias);
//        setListAdapter(adap);

        ToggleButtonListAdapter adap = new ToggleButtonListAdapter(this, arraylistPraias);
        list.setAdapter(adap);

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
                protected Document doInBackground(String... s) {
                    Document fulldoc = null;
                    try {
                        fulldoc = Jsoup.connect(s[0]).get();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return fulldoc;
                }

                @Override
                protected void onPostExecute(Document fulldoc) {
                    actualizarListaPraiasCont(fulldoc);
                }
            }.execute(urlListaPraias);
    }

    private void actualizarListaPraiasCont(Document fulldoc) {
        listaPraias = new ArrayList<>();;
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

    public class ToggleButtonListAdapter extends ArrayAdapter<String> {
        private final Context context;
        private final ArrayList<String> values;

        public ToggleButtonListAdapter(Context context, ArrayList<String> values) {
            super(context, R.layout.activity_main, values);
            this.context = context;
            this.values = values;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.layout_linhas, parent, false);
            TextView textView = rowView.findViewById(R.id.items);
            Switch toggleButton =  rowView.findViewById(R.id.bMostrar);

            toggleButton.setOnClickListener(new View.OnClickListener() {
                private final ArrayList<String> values = arraylistPraias;

                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), values.get(position) + " checked", Toast.LENGTH_LONG).show();
                }
            });

            textView.setText(values.get(position));

            return rowView;
        }
    }


}



