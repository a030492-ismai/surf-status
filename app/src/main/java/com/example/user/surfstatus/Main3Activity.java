package com.example.user.surfstatus;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class Main3Activity extends AppCompatActivity {

    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        text = findViewById(R.id.textView);

        Intent y = getIntent();

        String[] condicao = new String[2];
        condicao[0] = y.getStringExtra("url");

        getConditionDescription(condicao[0]);
    }

    @SuppressLint("StaticFieldLeak")
    private void getConditionDescription(String url) {
        new AsyncTask<String, Void, String[]>() {
            @Override
            protected void onPreExecute(){
                text.setText("a actualizar descricao...");
            }
            @Override
            protected String[] doInBackground(String... s) {
                Document fulldoc = null;

                try {
                    fulldoc = Jsoup.connect(s[0]).get();
                    s[0] = fulldoc.select("div.conditionDescription").first().text();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return s;
            }

            @Override
            protected void onPostExecute(String[] s) {
                text.setText(s[0]);
            }
        }.execute(url);
    }
}
