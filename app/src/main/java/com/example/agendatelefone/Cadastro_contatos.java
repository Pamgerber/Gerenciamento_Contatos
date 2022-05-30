package com.example.agendatelefone;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.os.Bundle;

import com.example.agendatelefone.aplicativo.MessageBox;
import com.example.agendatelefone.aplicativo.ViewAjudante;
import com.example.agendatelefone.database.DataBase;
import com.example.agendatelefone.dominio.RepositorioContato;
import com.example.agendatelefone.dominio.entidades.Contato;
import com.example.agendatelefone.util.DateUtils;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class Cadastro_contatos extends AppCompatActivity {

    private EditText edtNome;
    private EditText edtTelefone;
    private EditText edtEmail;
    private EditText edtEndereco;
    private EditText edtDatasEspeciais;
    private EditText edtNotas;

    private Spinner spnTipoTelefone;
    private Spinner spnTipoEmail;
    private Spinner spnTipoEndereco;
    private Spinner spnTipoDataEspecial;

    private ArrayAdapter<String>adpTipoTelefone;
    private ArrayAdapter<String>adpTipoEmail;
    private ArrayAdapter<String>adpTipoEndereco;
    private ArrayAdapter<String>adpTipoDataEspecial;

    private DataBase dataBase;
    private SQLiteDatabase conn;
    private RepositorioContato repositorioContato;
    private Contato contato;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_cadastro_contatos);

        edtNome= (EditText) findViewById(R.id.edtNome);
        edtTelefone= (EditText) findViewById(R.id.edtTelefone);
        edtEmail= (EditText) findViewById(R.id.edtEmail);
        edtEndereco= (EditText) findViewById(R.id.edtEndereco);
        edtDatasEspeciais= (EditText) findViewById(R.id.edtDatasEspeciais);
        edtNotas= (EditText) findViewById(R.id.edtNotas);

        spnTipoTelefone= (Spinner) findViewById(R.id.spnTipoTelefone);
        spnTipoEmail= (Spinner) findViewById(R.id.spnTipoEmail);
        spnTipoEndereco= (Spinner) findViewById(R.id.spnTipoEndereco);
        spnTipoDataEspecial= (Spinner) findViewById(R.id.spnTipoDataEspecial);

        adpTipoTelefone = ViewAjudante.createArrayAdapter(this, spnTipoTelefone);
        adpTipoEmail = ViewAjudante.createArrayAdapter(this, spnTipoEmail);
        adpTipoEndereco = ViewAjudante.createArrayAdapter(this, spnTipoEndereco);
        adpTipoDataEspecial = ViewAjudante.createArrayAdapter(this, spnTipoDataEspecial);

        adpTipoTelefone.add("Casa");
        adpTipoTelefone.add("Celular");
        adpTipoTelefone.add("Trabalho");

        adpTipoEmail.add("Casa");
        adpTipoEmail.add("Trabalho");
        adpTipoEmail.add("Escola");
        adpTipoEmail.add("Outro");

        adpTipoEndereco.add("Casa");
        adpTipoEndereco.add("Trabalho");
        adpTipoEndereco.add("Escola");
        adpTipoEndereco.add("Outro");

        adpTipoDataEspecial.add("Aniversário");
        adpTipoDataEspecial.add("Outro");

        ExibeDataListener listener= new ExibeDataListener();

        edtDatasEspeciais.setOnClickListener( listener );
        edtDatasEspeciais.setOnFocusChangeListener( listener );
        edtDatasEspeciais.setKeyListener(null);

        Bundle bundle= getIntent().getExtras();

        if ((bundle != null) && (bundle.containsKey(MainActivity.PARAMETRO_CONTATO)))
        {
            contato= (Contato) bundle.getSerializable(MainActivity.PARAMETRO_CONTATO);
            preencheDados();
        }else
        contato= new Contato();

        try {
            dataBase = new DataBase(this);
            conn = dataBase.getWritableDatabase();

            repositorioContato= new RepositorioContato(conn);

        } catch (SQLException ex)
        {
            MessageBox.show(this,"Erro","Erro ao criar o banco: " + ex.getMessage() );
        }
    }

    private void validaCampos(){

        boolean res= false;

        String nome= edtNome.getText().toString();
        String telefone= edtNome.getText().toString();
        String email= edtNome.getText().toString();
        String endereco= edtNome.getText().toString();
        String notas= edtNotas.getText().toString();

        if(res=isCampoVazio(nome)){
            edtNome.requestFocus();
        }else
        if(res=!isTelefoneValido(telefone)) {
            edtTelefone.requestFocus();
        }else
        if(res=isCampoVazio(email)) {
            edtEmail.requestFocus();
        }else
        if(res=isCampoVazio(endereco)) {
            edtEndereco.requestFocus();
            }else
            if(res=isCampoVazio(notas)) {
                edtNotas.requestFocus();
            }

        if(res){

            AlertDialog.Builder dlg= new AlertDialog.Builder(this);
            dlg.setTitle(R.string.titulo_aviso);
            dlg.setMessage(R.string.mensagem_campos_invalidos_brancos);
            dlg.setNeutralButton(R.string.lnl_ok,null);
            dlg.show();
        }

    }

    private boolean isCampoVazio(String valor){
        boolean resultado= (TextUtils.isEmpty(valor) || valor.trim().isEmpty() );
        return resultado;
    }

    private boolean isTelefoneValido(String telefone){
        boolean resultado= (!isCampoVazio(telefone) && Patterns.PHONE.matcher(telefone).matches());
        return resultado;
    }

        @Override
        protected void onDestroy(){
            super.onDestroy();
            if (conn!=null){
                conn.close();
            }
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.menu_fragment_cadastro_contatos,menu);

        if(contato.getId() != 0)
            menu.getItem(1).setVisible(true);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId())
        {
            case R.id.acao1:
                salvar();

                break;

            case R.id.acao2:

                excluir();
                finish();

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void preencheDados()
    {
        edtNome.setText( contato.getNome() );
        edtTelefone.setText( contato.getTelefone() );
        spnTipoTelefone.setSelection( Integer.parseInt(contato.getTipoTelefone()) );
        edtEmail.setText( contato.getEmail() );
        spnTipoEmail.setSelection( Integer.parseInt(contato.getTipoEmail()) );
        edtEndereco.setText( contato.getEndereco() );
        spnTipoEndereco.setSelection( Integer.parseInt(contato.getTipoEndereco()) );

        DateFormat format= DateFormat.getDateInstance(DateFormat.SHORT);
        String dt= format.format( contato.getDatasEspeciais() );

        edtDatasEspeciais.setText(dt);
        spnTipoDataEspecial.setSelection( Integer.parseInt(contato.getTipoDataEspecial()) );

        edtNotas.setText( contato.getNotas() );
    }

    private void excluir(){

        try
        {
            repositorioContato.excluir(contato.getId());

        } catch (Exception ex)
        {
            MessageBox.show(this,"Erro","Erro ao excluir!" + ex.getMessage());
        }
    }

    private void salvar() {
        try {

            contato.setNome(edtNome.getText().toString());
            contato.setTelefone(edtTelefone.getText().toString());
            contato.setEmail(edtEmail.getText().toString());
            contato.setEndereco(edtEndereco.getText().toString());

            contato.setNotas(edtNotas.getText().toString());

            contato.setTipoTelefone(String.valueOf(spnTipoTelefone.getSelectedItemPosition()) );
            contato.setTipoEmail(String.valueOf(spnTipoEmail.getSelectedItemPosition()) );
            contato.setTipoEndereco(String.valueOf(spnTipoEndereco.getSelectedItemPosition()) );
            contato.setTipoDataEspecial(String.valueOf(spnTipoDataEspecial.getSelectedItemPosition()) );

            if(contato.getId()==0)
                repositorioContato.inserir(contato);
            else
                repositorioContato.alterar(contato);

            finish();

        } catch (Exception ex) {
            MessageBox.show(this,"Erro","Existe campos inválidos ou em branco!");
        }
    }

    private void exibeData()
        {
            Calendar calendar= Calendar.getInstance();

            int ano= calendar.get(Calendar.YEAR);
            int mes= calendar.get(Calendar.MONTH);
            int dia= calendar.get(Calendar.YEAR);

            DatePickerDialog dlg= new DatePickerDialog(this, new SelecionaDataListener(), ano, mes,dia);
            dlg.show();
        }

        private class ExibeDataListener implements View.OnClickListener, View.OnFocusChangeListener
        {
            @Override
            public void onClick(View v) {
                exibeData();
            }

            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                if (hasFocus)
                    exibeData();
            }
        }

        private class SelecionaDataListener implements DatePickerDialog.OnDateSetListener
        {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
            {
                String dt= DateUtils.dateToString(year, monthOfYear, dayOfMonth);
                Date data= DateUtils.getDate(year, monthOfYear, dayOfMonth);

                edtDatasEspeciais.setText(dt);

                contato.setDatasEspeciais(data);
            }
        }

}