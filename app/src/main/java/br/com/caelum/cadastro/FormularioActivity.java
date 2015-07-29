package br.com.caelum.cadastro;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

import br.com.caelum.cadastro.br.com.caelum.cadastro.dao.AlunoDAO;
import br.com.caelum.cadastro.br.com.caelum.cadastro.modelo.Aluno;

public class FormularioActivity extends ActionBarActivity{

    public  static  final String ALUNO_SELECIONADO = "alunoSelecionado";
    public static final Integer RETORNO_FOTO = 123;
    private FormularioHelper helper;
    private String localArquivoFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("[INFO]", "Executando o metodo onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
        Button botaoSalvar = (Button) findViewById(R.id.form_botao);

        this.helper = new FormularioHelper(this);
        Intent intentAnterior =  this.getIntent();
        Aluno aluno = null;
        aluno = (Aluno) intentAnterior.getSerializableExtra(ALUNO_SELECIONADO);
        Button foto = helper.getFotoButton();

        if(aluno != null){
            this.helper.colocaNoFormulario(aluno);
            this.helper.carregaImagem(aluno.getCaminhoFoto());
        }


        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FormularioActivity.this, "Você clicou no botão", Toast.LENGTH_LONG).show();
                FormularioActivity.this.finish();
            }
        });

        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                localArquivoFoto = FormularioActivity.this.getExternalFilesDir(null) +
                        File.separator + System.currentTimeMillis()+".jpg";
                Intent irParaCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                irParaCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(
                                                                    new File(localArquivoFoto))
                                                                );
                FormularioActivity.this.startActivityForResult(irParaCamera,RETORNO_FOTO);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RETORNO_FOTO){
            if(resultCode == Activity.RESULT_OK){
                helper.carregaImagem(this.localArquivoFoto);
            }
        }else{
            this.localArquivoFoto = null;
        }
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


               if(aluno.getId() == null){
                   Log.i("[INFO]", " INSERE - id: " + aluno.getId());
                   Log.i("[INFO]", "CHAMEI INSERE");
                   dao.insere(aluno);
               }else{
                   Log.i("[INFO]", " altera - id: " + aluno.getId());
                   Log.i("[INFO]", "CHAMEI ALTERA");
                   dao.alterar(aluno);
               }
                dao.close();
               finish();
               return false;

           default:
               return super.onOptionsItemSelected(item);
       }

    }
}
