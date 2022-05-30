package com.example.agendatelefone;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.content.Intent;
import android.os.Bundle;

import android.database.*;

import com.example.agendatelefone.aplicativo.MessageBox;
import com.example.agendatelefone.database.DataBase;
import com.example.agendatelefone.dominio.RepositorioContato;
import com.example.agendatelefone.dominio.entidades.Contato;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private ImageButton btnAdicionar;
    private EditText edtPesquisa;
    private ListView listaContatos;
    private ArrayAdapter<Contato>adpContatos;

    private DataBase dataBase;
    private SQLiteDatabase conn;
    private RepositorioContato repositorioContato;
    private FiltraDados filtraDados;

    public static final String PARAMETRO_CONTATO= "CONTATO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdicionar = (ImageButton) findViewById(R.id.btnAdicionar);
        edtPesquisa = (EditText) findViewById(R.id.edtPesquisa);
        listaContatos = (ListView) findViewById(R.id.listaContatos);

        btnAdicionar.setOnClickListener(this);
        listaContatos.setOnItemClickListener(this);

        try{

            dataBase = new DataBase(this);
            conn = dataBase.getWritableDatabase();
            repositorioContato = new RepositorioContato(conn);

            adpContatos = repositorioContato.buscaContatos(this);

            listaContatos.setAdapter(adpContatos);

            filtraDados = new FiltraDados(adpContatos);
            edtPesquisa.addTextChangedListener(filtraDados);

        } catch (SQLException ex)
        {
            MessageBox.show(this,"Erro","Erro ao criar o banco: " + ex.getMessage() );
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if (conn!=null){
            conn.close();
        }
    }

    @Override
    public void onClick(View v) {
        Intent it = new Intent(this, Cadastro_contatos.class);
        startActivityForResult(it, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        adpContatos = repositorioContato.buscaContatos(this);
        filtraDados.setArrayAdapter(adpContatos);
        listaContatos.setAdapter(adpContatos);
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Contato contato= adpContatos.getItem(position);

        Intent it= new Intent(this, Cadastro_contatos.class);

        it.putExtra(PARAMETRO_CONTATO, contato);

        startActivityForResult(it, 0);
    }

    private class FiltraDados implements TextWatcher
    {
        private ArrayAdapter<Contato>arrayAdapter;

        private FiltraDados(ArrayAdapter<Contato>arrayAdapter)
        {
            this.arrayAdapter= arrayAdapter;
        }

        public void setArrayAdapter(ArrayAdapter<Contato>arrayAdapter)
        {
            this.arrayAdapter= arrayAdapter;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int after) {
            arrayAdapter.getFilter().filter(s);

        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    }
}