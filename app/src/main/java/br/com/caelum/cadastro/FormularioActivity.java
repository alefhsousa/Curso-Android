package br.com.caelum.cadastro;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import br.com.caelum.cadastro.br.com.caelum.cadastro.dao.AlunoDAO;
import br.com.caelum.cadastro.br.com.caelum.cadastro.modelo.Aluno;

public class FormularioActivity extends ActionBarActivity{

    private FormularioHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
        Log.i("[INFO]", "Executando o metodo onCreate");

        Button botaoSalvar = (Button) findViewById(R.id.form_botao);

        this.helper = new FormularioHelper(this);

        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FormularioActivity.this, "Você clicou no botão", Toast.LENGTH_LONG).show();
                FormularioActivity.this.finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("[INFO]", "Executando o metodo onResume");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("[INFO]", "Executando o metodo onStart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("[INFO]", "Executando o metodo onDestroy");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("[INFO]", "Executando o metodo onStop");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("[INFO]", "Executando o metodo onStop");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i("[INFO]", "Executando o metodo onMenu");
        getMenuInflater().inflate(R.menu.menu_formulario, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

       switch(item.getItemId()){
           case R.id.menu_formulario_ok:
               Aluno aluno = helper.pegaAlunoDoFormulario();
               AlunoDAO dao = new AlunoDAO(this);

               dao.insere(aluno);

               finish();
               return false;

           default:
               return super.onOptionsItemSelected(item);
       }

    }
}
