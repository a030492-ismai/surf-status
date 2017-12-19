package com.example.user.surfstatus;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

    Button botao;
    TextView text;

    String[] condicoesPraias;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botao = findViewById(R.id.button);
        text = findViewById(R.id.textView);
        condicoesPraias = new String[0];



        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i = 0; i < 1; ++i) {
                    new AsyncT().execute(i);
                }
            }
        });

    }


    public class AsyncT extends AsyncTask<Integer, Long , String> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(Integer... i) {
            return Comunicar.contactar2("beachcam.sapo.pt", "/reports/praia-do-moledo/", 80);
        }

        @Override
        protected void onProgressUpdate(Long... progress) {

        }

        @Override
        protected void onPostExecute(String finalString) {
//
//        listaClientes = null;

//        StringReader is = new StringReader(finalString);
//        SaxXmlClientListHandler handler = new SaxXmlClientListHandler();
//        SaxXmlParser parser = new SaxXmlParser();
//        parser.setHandler(handler);
//
//        parser.parse(is);
//        listaClientes = handler.obterElementos();
//
//        adp = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, listaClientes);
//        list.setAdapter(adp);
            text.setText(finalString);
        }
    }
}
