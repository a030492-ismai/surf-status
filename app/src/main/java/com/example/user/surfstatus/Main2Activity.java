package com.example.user.surfstatus;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {
    ListView list;
    List<Praia> listaPraias = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        list = findViewById(android.R.id.list);

        if(listaPraias.size() == 0){
            Toast.makeText(this, "nao tem praias adicionadas", Toast.LENGTH_LONG).show();
        }

        ArrayAdapter<Praia> adap = new ArrayAdapter<Praia>(
                this, android.R.layout.simple_list_item_1, listaPraias);
        list.setAdapter(adap);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        return true;
    }
}
