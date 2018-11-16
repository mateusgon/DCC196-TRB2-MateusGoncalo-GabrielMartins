package com.example.gabriel.dcc196trabalho01;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ParticipanteActivity extends AppCompatActivity {

    private static final int REQUEST_CADPARTICIPANTE = 1;

    private Button btnCadastrarParticipante;
    private RecyclerView rvListaParticipantes;
    private TextView txtTotalParticipantes;
    private ParticipanteAdapter adapter;
    private ParticipanteDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participante);

        dbHelper = new ParticipanteDbHelper(getApplicationContext());

        btnCadastrarParticipante = findViewById(R.id.btn_cadastrarParticipante);
        txtTotalParticipantes = findViewById(R.id.txt_totalParticipantes);

        rvListaParticipantes = (RecyclerView) findViewById(R.id.rv_listaParticipantes);
        rvListaParticipantes.setLayoutManager(new LinearLayoutManager(this));
        rvListaParticipantes.setAdapter(new ParticipanteAdapter(getParticipantes()));

        adapter = new ParticipanteAdapter(getParticipantes());
        adapter.setOnClickListener(new ParticipanteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Intent intent = new Intent(ParticipanteActivity.this, ParticipanteInformacaoActivity.class);
                intent.putExtra("registro", position);
                startActivity(intent);

            }
        });
        rvListaParticipantes.setAdapter(adapter);

        int total = getParticipantes().getCount();
        txtTotalParticipantes.setText("Total de Participantes: " + total);

        btnCadastrarParticipante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ParticipanteActivity.this, CadastroParticipanteActivity.class);
                startActivityForResult(intent, ParticipanteActivity.REQUEST_CADPARTICIPANTE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ParticipanteActivity.REQUEST_CADPARTICIPANTE && resultCode == Activity.RESULT_OK){
            int total = getParticipantes().getCount();
            txtTotalParticipantes.setText("Total de Participantes: " + total);
        }
        adapter.setCursor(getParticipantes());
    }

    private Cursor getParticipantes()
    {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String []visao = {
                AppContract.Evento.COLUMN_NAME_REGISTRO,
                AppContract.Evento.COLUMN_NAME_NOME,
        };
        String sort = AppContract.Participante.COLUMN_NAME_NOME+ " ASC";
        return db.query(AppContract.Participante.TABLE_NAME, visao,null,null,null,null, sort);
    }
}