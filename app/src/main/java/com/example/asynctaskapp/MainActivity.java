package com.example.asynctaskapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    ArrayList<String> pokemonList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        recyclerView = findViewById(R.id.recyclerView1);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerViewAdapter = new RecyclerViewAdapter(pokemonList);
        recyclerView.setAdapter(recyclerViewAdapter);

        final Button button = findViewById(R.id.pokemonButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DownloadPokemonTask(MainActivity.this).execute();
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

class DownloadPokemonTask extends AsyncTask<Void, Integer, ArrayList<String>> {

    private ProgressDialog dialog;

    public DownloadPokemonTask(Context context) {

        this.dialog = new ProgressDialog(context);

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        this.dialog.setMessage("Downloading data...");
        dialog.show();
    }

    @Override
    protected ArrayList<String> doInBackground(Void... voids) {

        ArrayList<String> newPokemonList = new ArrayList<>();

        for (int i = 0; i <= 3; i++) {

            try {
                Thread.sleep(1000);
                newPokemonList.add(simulateCalltoAPI(i));

            } catch (InterruptedException e) {


            }
        }

        return newPokemonList;
    }

    //This method simulate a call to API
    private String simulateCalltoAPI(int i) {

        switch (i) {

            case 1:
                return  "Bulbasaur";
            case 2:
                return "Ivysaur";
            case 3:
                return "Venusaur";
            default:
                return "Invalid number";

        }
    }

    @Override
    protected void onPostExecute(ArrayList<String> result) {
        super.onPostExecute(result);
        pokemonList.clear();
        pokemonList.addAll(result);
        recyclerViewAdapter.notifyDataSetChanged();
        dialog.dismiss();

    }

    }
}



