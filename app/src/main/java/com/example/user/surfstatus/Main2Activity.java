package com.example.user.surfstatus;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static com.example.user.surfstatus.Praia.listaPraias;

public class Main2Activity extends AppCompatActivity {
    ListView list;
//    public static List<Praia> listaPraias = new ArrayList<>();
    FloatingActionButton bActualizarPraias;
    Button bAdicionarPraias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        list = findViewById(android.R.id.list);
        bActualizarPraias = findViewById(R.id.bActualizarPraias);
        bAdicionarPraias = (Button) findViewById(R.id.bAdicionarPraias);
//        listaPraias.add(-1, new Praia());



        bActualizarPraias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO refresh lista praias main

            }
        });


        if(listaPraias.size() == 0){
            Toast.makeText(this, "nao tem praias adicionadas", Toast.LENGTH_LONG).show();
        }
        else{
            ArrayAdapter<Praia> adap = new ArrayAdapter<Praia>(this, android.R.layout.simple_list_item_1, listaPraias);
            list.setAdapter(adap);
        }


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

    public void actualizarListaPraias(){
        ArrayAdapter<Praia> adap = new ArrayAdapter<Praia>(
                this, android.R.layout.simple_list_item_1, listaPraias);
        list.setAdapter(adap);
    }

}
