package com.example.user.surfstatus;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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

import static com.example.user.surfstatus.Praia.listaPraias;
import static com.example.user.surfstatus.Praia.listaPraiasListar;


public class MainActivity extends ListActivity {

    FloatingActionButton bActualizar;
    ListView list;
    AdaptadorBaseDados bd;
    String urlListaPraias = "http://beachcam.meo.pt/reports/";
    ArrayList<String> arraylistPraias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bd = new AdaptadorBaseDados(this).open();

        bActualizar = findViewById(R.id.bActualizar);

        list = getListView();

//        ArrayList<String> arraylistPraias = new ArrayList<>(listaPraias.size());

        bActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actualizarListaPraias();
            }
        });

        setListAdap();
        if(bd.getPraiasListar().size() == 0){
            actualizarListaPraias();
        }
    }


    protected void setListAdap(){
        arraylistPraias = new ArrayList<>(bd.getSize());

        for(int i = 1; i < bd.getSize() + 1; ++i){
            arraylistPraias.add(bd.getNomePraia(i));
        }

        ToggleButtonListAdapter adap = new ToggleButtonListAdapter(this, arraylistPraias);
        list.setAdapter(adap);
    }

    @SuppressLint("StaticFieldLeak")
    public void actualizarListaPraias() {
        Toast.makeText(this, "a actualizar lista de praias...", Toast.LENGTH_LONG).show();
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
        ArrayList<Praia> listaPraiasTemp = new ArrayList<>();
        Elements els = fulldoc.select(".beachesContainer a");
        for(int k = 0; k < els.size() - 1; ++k){
            Praia umaPraia = new Praia(k);
            umaPraia.setNomePraia(els.get(k).text());
            umaPraia.setUrlPraia("http://beachcam.meo.pt" + els.get(k).attr("href"));
            listaPraiasTemp.add(umaPraia);
        }
        Praia.addPraias(listaPraiasTemp);
        actualizarBDPraias(listaPraiasTemp);
        setListAdap();
        Toast.makeText(this, "lista de praias actualizada", Toast.LENGTH_LONG).show();
    }

    private void actualizarBDPraias(List<Praia> listaPraias) {
        bd.dropPraias();
        for (Praia praia : listaPraias){
            bd.inserirPraia(praia.getNomePraia(), praia.getUrlPraia());
        }
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
            textView.setTextColor(Color.WHITE);
            final Switch toggleButton =  rowView.findViewById(R.id.bMostrar);
            toggleButton.setChecked(bd.getListarPraia(position+1));

            toggleButton.setOnClickListener(new View.OnClickListener() {
                private final ArrayList<String> values = arraylistPraias;

                @Override
                public void onClick(View v) {
                    if(toggleButton.isChecked()){
                        bd.updateListar(bd.getNomePraia(position+1), 1);
                    }
                    else{
                        bd.updateListar(bd.getNomePraia(position+1), 0);
                    }
                }
            });

            textView.setText(values.get(position));

            return rowView;
        }
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
}



