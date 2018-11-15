package com.example.gabriel.dcc196trabalho01;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ParticipanteInformacaoActivity extends AppCompatActivity {

    private static final int REQUEST_EDITINFO = 1;

    private Participante participante;

    private Button btnEditarInformacoes;
    private RecyclerView rvListaEventosInscrito;
    private RecyclerView rvListaEventosNaoInscrito;
    private TextView txtNomeParticipante;
    private TextView txtEmailParticipante;
    private TextView txtCPFParticipante;
    private TextView txtEventoCadastrados;
    private TextView txtEventosNaoCadastrados;
    private ParticipanteInformacaoAdapter adapter;
    private ParticipanteInformacaoAdapter adapter2;
    private List<Evento> eventosInscritos;
    private List<Evento> eventosTodos;
    private List<Evento> eventosDisponiveis;
    private Cursor cursor;
    private EventoDbHelper dbHelperEvento;
    private ParticipanteDbHelper dbHelperParticipante;
    private ParticipanteEventoDbHelper dbHelperParticipanteEvento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participante_informacao);

        eventosTodos = new ArrayList<>();
        eventosInscritos = new ArrayList<>();

        dbHelperEvento = new EventoDbHelper(getApplicationContext());
        dbHelperParticipanteEvento = new ParticipanteEventoDbHelper(getApplicationContext());
        dbHelperParticipante = new ParticipanteDbHelper(getApplicationContext());

        final Bundle extras = getIntent().getExtras();

        btnEditarInformacoes = (Button) findViewById(R.id.btn_EditInfo2);
        txtNomeParticipante = findViewById(R.id.txt_NomeParticipanteInfo2);
        txtCPFParticipante = findViewById(R.id.txt_CPFParticipanteInfo2);
        txtEmailParticipante = findViewById(R.id.txt_EmailParticipanteInfo2);
        txtEventoCadastrados = findViewById(R.id.txt_EventosCadastrados2);
        txtEventosNaoCadastrados = findViewById(R.id.txt_EventosDisponiveis2);
        rvListaEventosInscrito = (RecyclerView) findViewById(R.id.rv_ListaEventosCadastrados);
        rvListaEventosNaoInscrito = (RecyclerView) findViewById(R.id.rv_ListaEventosDisponíveis);

        participante = preencheInfos(extras.getInt("registro"));

        btnEditarInformacoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ParticipanteInformacaoActivity.this, ParticipanteEditActivity.class);
                intent.putExtra("registro", extras.getInt("registro"));
                startActivityForResult(intent, ParticipanteInformacaoActivity.REQUEST_EDITINFO);
            }
        });

        rvListaEventosInscrito.setLayoutManager(new LinearLayoutManager(this));
        rvListaEventosNaoInscrito.setLayoutManager(new LinearLayoutManager(this));

        preencherTodosEventos();
        preencherEventosInscritosENaoInscritos(extras.getInt("registro"));

        adapter = new ParticipanteInformacaoAdapter(eventosInscritos);

        if (participante.getEventos().size() == 0)
        {
            eventosDisponiveis = eventosTodos;
            adapter2 = new ParticipanteInformacaoAdapter(eventosDisponiveis);
        }
        else
        {
            adapter2 = new ParticipanteInformacaoAdapter(eventosDisponiveis);
        }

        adapter.setOnParticipanteLongClickListener(new ParticipanteInformacaoAdapter.OnParticipanteLongClickListener() {
            @Override
            public void onParticipanteLongClick(View participanteView, int position) {
                SQLiteDatabase db = dbHelperParticipanteEvento.getReadableDatabase();
                String []visao = {
                        AppContract.ParticipanteEvento.COLUMN_NAME_REGISTRO,
                        AppContract.ParticipanteEvento.COLUMN_NAME_PARTICIPANTE,
                        AppContract.ParticipanteEvento.COLUMN_NAME_EVENTO,
                };

                String select = AppContract.ParticipanteEvento.COLUMN_NAME_EVENTO+" = ?";
                String [] selectArgs = {String.valueOf(position)};

                cursor = db.query(AppContract.ParticipanteEvento.TABLE_NAME, visao,select,selectArgs,null,null, null);

                for (int i = 0; i < cursor.getCount(); i++)
                {
                    int idxNum = cursor.getColumnIndexOrThrow(AppContract.ParticipanteEvento.COLUMN_NAME_REGISTRO);
                    int idxNumParticipante = cursor.getColumnIndexOrThrow(AppContract.ParticipanteEvento.COLUMN_NAME_PARTICIPANTE);
                    int idxNumEvento = cursor.getColumnIndexOrThrow(AppContract.ParticipanteEvento.COLUMN_NAME_EVENTO);

                    cursor.moveToPosition(i);
                    if (extras.getInt("registro") == cursor.getInt(idxNumParticipante) && position == cursor.getInt(idxNumEvento))
                    {
                        String select2 = AppContract.ParticipanteEvento.COLUMN_NAME_REGISTRO+" = ?";
                        String [] selectArgs2 = {String.valueOf(cursor.getInt(idxNum))};
                        db.delete(AppContract.ParticipanteEvento.TABLE_NAME, select2, selectArgs2);
                    }
                }

                preencherEventosInscritosENaoInscritos(extras.getInt("registro"));
                adapter.setEventos(eventosInscritos);
                adapter.notifyDataSetChanged();
                adapter2.setEventos(eventosDisponiveis);
                adapter2.notifyDataSetChanged();
            }
        });
        rvListaEventosInscrito.setAdapter(adapter);

        adapter2.setOnParticipanteLongClickListener(new ParticipanteInformacaoAdapter.OnParticipanteLongClickListener() {
            @Override
            public void onParticipanteLongClick(View participanteView, int position) {
                SQLiteDatabase db = dbHelperParticipanteEvento.getWritableDatabase();
                ContentValues valores = new ContentValues();
                valores.put(AppContract.ParticipanteEvento.COLUMN_NAME_PARTICIPANTE, extras.getInt("registro"));
                valores.put(AppContract.ParticipanteEvento.COLUMN_NAME_EVENTO, position);
                long id = db.insert(AppContract.ParticipanteEvento.TABLE_NAME,null, valores);
                preencherEventosInscritosENaoInscritos(extras.getInt("registro"));
                adapter2.setEventos(eventosDisponiveis);
                adapter2.notifyDataSetChanged();
                adapter.setEventos(eventosInscritos);
                adapter.notifyDataSetChanged();

//                  Toast.makeText(getApplicationContext(), "Evento já está lotado", Toast.LENGTH_SHORT).show();
            }
        });
        rvListaEventosNaoInscrito.setAdapter(adapter2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ParticipanteInformacaoActivity.REQUEST_EDITINFO && resultCode == Activity.RESULT_OK){
            Participante p = (Participante) data.getSerializableExtra("participante");
            List<Participante> participanteList = ModelDAO.getParticipanteInstance();
            for (Participante parts: participanteList) {
                if (parts.getNome().equals(participante.getNome()) && parts.getCpf().equals(participante.getCpf()) && parts.getEmail().equals(participante.getEmail()))
                {
                    parts.setNome(p.getNome());
                    parts.setCpf(p.getCpf());
                    parts.setEmail(p.getEmail());
                    txtNomeParticipante.setText("Nome: " +p.getNome());
                    txtEmailParticipante.setText("Email: " + p.getEmail());
                    txtCPFParticipante.setText("CPF: " + p.getCpf());
                    break;
                }
            }
        }
    }

    public Participante preencheInfos(Integer registro)
    {
        SQLiteDatabase db = dbHelperParticipante.getReadableDatabase();
        String []visao = {
                AppContract.Participante.COLUMN_NAME_REGISTRO,
                AppContract.Participante.COLUMN_NAME_NOME,
                AppContract.Participante.COLUMN_NAME_CPF,
                AppContract.Participante.COLUMN_NAME_EMAIL,
        };

        String select = AppContract.Participante.COLUMN_NAME_REGISTRO+" = ?";
        String [] selectArgs = {String.valueOf(registro)};

        cursor = db.query(AppContract.Participante.TABLE_NAME, visao,select,selectArgs,null,null, null);

        int idxNome = cursor.getColumnIndexOrThrow(AppContract.Participante.COLUMN_NAME_NOME);
        int idxCPF = cursor.getColumnIndexOrThrow(AppContract.Participante.COLUMN_NAME_CPF);
        int idxEmail = cursor.getColumnIndexOrThrow(AppContract.Participante.COLUMN_NAME_EMAIL);

        cursor.moveToPosition(0);

        Participante participante = new Participante(cursor.getString(idxNome), cursor.getString(idxEmail) ,cursor.getString(idxCPF), null);

        txtNomeParticipante.setText("Nome: " +participante.getNome());
        txtEmailParticipante.setText("Email: " + participante.getEmail());
        txtCPFParticipante.setText("CPF: " + participante.getCpf());

        return participante;
    }

    public void preencherTodosEventos ()
    {
        SQLiteDatabase db = dbHelperEvento.getReadableDatabase();
        String []visao = {
                AppContract.Evento.COLUMN_NAME_REGISTRO,
                AppContract.Evento.COLUMN_NAME_NOME,
        };

        cursor = db.query(AppContract.Evento.TABLE_NAME, visao,null,null,null,null, null);

        int idxNum = cursor.getColumnIndexOrThrow(AppContract.Evento.COLUMN_NAME_REGISTRO);
        int idxNome = cursor.getColumnIndexOrThrow(AppContract.Evento.COLUMN_NAME_NOME);

        for (int i = 0; i < cursor.getCount(); i++)
        {
            cursor.moveToPosition(i);
            Evento evento = new Evento();
            evento.setRegistro(cursor.getInt(idxNum));
            evento.setNome(cursor.getString(idxNome));
            eventosTodos.add(evento);
        }

    }

    public void preencherEventosInscritosENaoInscritos(Integer registro)
    {
        List<Evento> eventosAux = new ArrayList<>();
        SQLiteDatabase db = dbHelperParticipanteEvento.getReadableDatabase();
        String []visao = {
                AppContract.ParticipanteEvento.COLUMN_NAME_EVENTO,
        };
        String select = AppContract.ParticipanteEvento.COLUMN_NAME_PARTICIPANTE+" = ?";
        String [] selectArgs = {String.valueOf(registro)};
        cursor = db.query(AppContract.ParticipanteEvento.TABLE_NAME, visao,select,selectArgs,null,null, null);
        for (int i = 0; i < cursor.getCount(); i++)
        {
            cursor.moveToPosition(i);
            int idxEvento = cursor.getColumnIndexOrThrow(AppContract.ParticipanteEvento.COLUMN_NAME_EVENTO);
            Integer idEvento = cursor.getInt(idxEvento);
            for (int j = 0; j < eventosTodos.size(); j++)
            {
                if (eventosTodos.get(j).getRegistro().equals(idEvento))
                {
                    eventosAux.add(eventosTodos.get(i));
                }
            }
        }

        eventosInscritos = eventosAux;
        participante.setEventos(eventosInscritos);

        List<Evento> eventos3 = new ArrayList<>();
        for (int i = 0; i < eventosTodos.size(); i++)
        {
            Boolean inserir = true;
            for (int j = 0; j < participante.getEventos().size(); j++)
            {
                if (eventosTodos.get(i).getNome().equals(participante.getEventos().get(j).getNome()))
                {
                    inserir = false;
                }
            }
            if (inserir)
            {
                eventos3.add(eventosTodos.get(i));
            }
        }

        if (participante.getEventos().size() == 0)
        {
            eventosDisponiveis = eventosTodos;
        }

        eventosDisponiveis = eventos3;

    }
}
