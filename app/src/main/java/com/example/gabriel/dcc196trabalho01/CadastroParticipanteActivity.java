package com.example.gabriel.dcc196trabalho01;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CadastroParticipanteActivity extends AppCompatActivity {

    private Button btnConfimaCadastro;
    private EditText txtNomeParticipante;
    private EditText txtEmail;
    private EditText txtCPF;
    private ParticipanteDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_participante);

        dbHelper = new ParticipanteDbHelper(getApplicationContext());

        btnConfimaCadastro = findViewById(R.id.btn_confirmaCadPart);
        txtNomeParticipante = (EditText) findViewById(R.id.txt_nomeParticipante);
        txtEmail = (EditText) findViewById(R.id.txt_email);
        txtCPF = (EditText) findViewById(R.id.txt_cpf);

        btnConfimaCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Participante participante = new Participante(txtNomeParticipante.getText().toString(), txtEmail.getText().toString(), txtCPF.getText().toString(), null);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues valores = new ContentValues();
                valores.put(AppContract.Participante.COLUMN_NAME_NOME, participante.getNome());
                valores.put(AppContract.Participante.COLUMN_NAME_EMAIL, participante.getEmail());
                valores.put(AppContract.Participante.COLUMN_NAME_CPF, participante.getCpf());
                long id = db.insert(AppContract.Participante.TABLE_NAME,null, valores);
                Log.i("DBINFO", "registro criado com id: "+id);
                setResult(Activity.RESULT_OK);
                finish();
            }
        });
    }
}
