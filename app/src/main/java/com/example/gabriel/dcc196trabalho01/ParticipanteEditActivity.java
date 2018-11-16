package com.example.gabriel.dcc196trabalho01;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ParticipanteEditActivity extends AppCompatActivity {

    private Participante participante;
    private Button cnfEdicao;
    private EditText txtNome;
    private EditText txtEmail;
    private EditText txtCPF;
    private Cursor cursor;
    private ParticipanteDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participante_edit);

        cnfEdicao = findViewById(R.id.btnConfirmarParticipanteEdit);
        txtNome = findViewById(R.id.editTextNomeParticipanteEdit);
        txtEmail = findViewById(R.id.editTextEmailParticipanteEdit);
        txtCPF = findViewById(R.id.editTextCPFParticipanteEdit);

        dbHelper = new ParticipanteDbHelper(getApplicationContext());

        final Bundle extras = getIntent().getExtras();
        preencheInfo(extras.getInt("registro"));

        cnfEdicao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Participante p = new Participante();
                p.setNome(txtNome.getText().toString());
                p.setEmail(txtEmail.getText().toString());
                p.setCpf(txtCPF.getText().toString());
                ContentValues cv = new ContentValues();
                cv.put(AppContract.Participante.COLUMN_NAME_NOME, p.getNome());
                cv.put(AppContract.Participante.COLUMN_NAME_EMAIL, p.getEmail());
                cv.put(AppContract.Participante.COLUMN_NAME_CPF, p.getCpf());
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.update(AppContract.Participante.TABLE_NAME, cv, AppContract.Participante.COLUMN_NAME_REGISTRO +"="+extras.getInt("registro"), null);
                setResult(Activity.RESULT_OK);
                finish();
            }
        });
    }

    public void preencheInfo (Integer registro)
    {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
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

        this.participante = new Participante(cursor.getString(idxNome), cursor.getString(idxEmail) ,cursor.getString(idxCPF), null);

        txtNome.setText(participante.getNome());
        txtEmail.setText(participante.getEmail());
        txtCPF.setText(participante.getCpf());

    }
}
