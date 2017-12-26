package com.example.user.surfstatus;

import android.annotation.SuppressLint;
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

import static com.example.user.surfstatus.Praia.listaPraias;
import static com.example.user.surfstatus.Praia.listaPraiasListar;


public class MainActivity extends ListActivity {

    Button bActualizar;
    ListView list;

    String urlListaPraias = "http://beachcam.meo.pt/reports/";

    ArrayList<String> arraylistPraias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bActualizar = findViewById(R.id.bActualizar);
        list = getListView();

        ArrayList<String> arraylistPraias = new ArrayList<>(listaPraias.size());


        bActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actualizarListaPraias();
            }
        });

    }

    @Override
    public void onListItemClick(ListView parent, View v, int position, long id) {
        //TODO delete?
    }

    protected void setListAdap(){
        arraylistPraias = new ArrayList<>(listaPraias.size());
        for(int i = 0; i < listaPraias.size() -1; ++i){
            arraylistPraias.add(listaPraias.get(i).getNomePraia());
        }

        ToggleButtonListAdapter adap = new ToggleButtonListAdapter(this, arraylistPraias);
        list.setAdapter(adap);

    }

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
        ArrayList<Praia> listaPraiasTemp = new ArrayList<>();
        Elements els = fulldoc.select(".beachesContainer a");
        for(int k = 0; k < els.size() - 1; ++k){
            Praia umaPraia = new Praia(k);
            umaPraia.setNomePraia(els.get(k).text());
            umaPraia.setUrlPraia("http://beachcam.meo.pt" + els.get(k).attr("href"));
//            listaPraias.add(umaPraia);
            listaPraiasTemp.add(umaPraia);
        }
        Praia.addPraias(listaPraiasTemp);
//        actualizarBDPraias(listaPraias);
        setListAdap();

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
            final Switch toggleButton =  rowView.findViewById(R.id.bMostrar);

            toggleButton.setOnClickListener(new View.OnClickListener() {
                private final ArrayList<String> values = arraylistPraias;

                @Override
                public void onClick(View v) {
                    if(toggleButton.isChecked()){
                        listaPraias.get(position).setListar(true);
                        listaPraiasListar.add(listaPraias.get(position));

                        //TODO alterar na bd
                    }
                    else{
                        listaPraias.get(position).setListar(false);
                        listaPraiasListar.remove(listaPraias.get(position));
                        //TODO alterar na bd
                    }
                    Toast.makeText(getContext(), listaPraias.get(position).getNomePraia()+ " " + listaPraias.get(position).getListar(), Toast.LENGTH_LONG).show();
                }
            });

            textView.setText(values.get(position));

            return rowView;
        }
    }


}



