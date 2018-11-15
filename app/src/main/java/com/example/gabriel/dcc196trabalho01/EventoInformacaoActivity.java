package com.example.gabriel.dcc196trabalho01;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EventoInformacaoActivity extends AppCompatActivity {

    private Evento recuperado;
    private RecyclerView rv_ListaParticipantesEvento;
    private TextView nomeEvento;
    private TextView localEvento;
    private TextView dataEvento;
    private TextView facilitadorEvento;
    private TextView vagasEvento;
    private TextView inscritosEvento;
    private TextView descricaoEvento;
    private Cursor cursor;
    private EventoDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evento_informacao);

        nomeEvento = findViewById(R.id.txtNomeEventoInformacao);
        localEvento = findViewById(R.id.txtLocalEventoInformacao);
        dataEvento = findViewById(R.id.txtDataEventoInformacao);
        facilitadorEvento = findViewById(R.id.txtFacilitadorEventoInformacao);
        vagasEvento = findViewById(R.id.txtVagasEventoInformacao);
        inscritosEvento = findViewById(R.id.txtInscritosEventoInformacao);
        descricaoEvento = findViewById(R.id.txtDescricaoEventoInformacao);
        rv_ListaParticipantesEvento = findViewById(R.id.rv_ListaDeInscritosEventoInformacao);

        dbHelper = new EventoDbHelper(getApplicationContext());

        Bundle extras = getIntent().getExtras();

        Intent intent = getIntent();
        Integer registro = extras.getInt("position");
        preencheInfos(registro);

        rv_ListaParticipantesEvento.setLayoutManager(new LinearLayoutManager(this));
        //rv_ListaParticipantesEvento.setAdapter(new ParticipanteAdapter(recuperado.getParticipanteList()));

    }

    public void preencheInfos(Integer registro)
    {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String []visao = {
                AppContract.Evento.COLUMN_NAME_REGISTRO,
                AppContract.Evento.COLUMN_NAME_NOME,
                AppContract.Evento.COLUMN_NAME_LOCAL,
                AppContract.Evento.COLUMN_NAME_DATA,
                AppContract.Evento.COLUMN_NAME_FACILITADOR,
                AppContract.Evento.COLUMN_NAME_DESCRICAO,
                AppContract.Evento.COLUMN_NAME_INSCRITOS,
                AppContract.Evento.COLUMN_NAME_MAXINSCRITOS,
        };

        String select = AppContract.Evento.COLUMN_NAME_REGISTRO+" = ?";
        String [] selectArgs = {String.valueOf(registro)};

        cursor = db.query(AppContract.Evento.TABLE_NAME, visao,select,selectArgs,null,null, null);

        int idxNum = cursor.getColumnIndexOrThrow(AppContract.Evento.COLUMN_NAME_REGISTRO);
        int idxNome = cursor.getColumnIndexOrThrow(AppContract.Evento.COLUMN_NAME_NOME);
        int idxLocal = cursor.getColumnIndexOrThrow(AppContract.Evento.COLUMN_NAME_LOCAL);
        int idxData = cursor.getColumnIndexOrThrow(AppContract.Evento.COLUMN_NAME_DATA);
        int idxFacilitador = cursor.getColumnIndexOrThrow(AppContract.Evento.COLUMN_NAME_FACILITADOR);
        int idxDescricao = cursor.getColumnIndexOrThrow(AppContract.Evento.COLUMN_NAME_DESCRICAO);
        int idxInscritos = cursor.getColumnIndexOrThrow(AppContract.Evento.COLUMN_NAME_INSCRITOS);
        int idxMaxInscritos = cursor.getColumnIndexOrThrow(AppContract.Evento.COLUMN_NAME_MAXINSCRITOS);

        cursor.moveToPosition(0);

        nomeEvento.setText("Nome: " + cursor.getString(idxNome));
        localEvento.setText("Local: " + cursor.getString(idxLocal));
        dataEvento.setText("Data: ");
        facilitadorEvento.setText("Facilitador: " + cursor.getString(idxFacilitador));
        vagasEvento.setText("Vagas: " + String.valueOf(cursor.getInt(idxMaxInscritos)));
        inscritosEvento.setText("Inscritos: " + String.valueOf(cursor.getInt(idxInscritos)));
        descricaoEvento.setText("Descrição: " + cursor.getString(idxDescricao));
    }
}
