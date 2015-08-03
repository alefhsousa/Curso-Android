package br.com.caelum.cadastro;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.com.caelum.cadastro.br.com.caelum.cadastro.adapter.ListaAlunosAdapter;
import br.com.caelum.cadastro.br.com.caelum.cadastro.converter.AlunoConverter;
import br.com.caelum.cadastro.br.com.caelum.cadastro.dao.AlunoDAO;
import br.com.caelum.cadastro.br.com.caelum.cadastro.modelo.Aluno;
import br.com.caelum.cadastro.br.com.caelum.cadastro.support.WebClient;

/**
 * Created by alefh on 7/21/15.
 */
public class ListaAlunosActivity extends ActionBarActivity {
    private   ListView listaAlunos;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_aluno);

        this.listaAlunos = (ListView) findViewById(R.id.lista_alunos);
        Button botaoAdiciona = (Button) findViewById(R.id.lista_alunos_floating_button);

        this.registerForContextMenu(this.listaAlunos); //registrando p/ menu de contexto

        this.listaAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                Intent editarAluno = new Intent(ListaAlunosActivity.this,FormularioActivity.class);
                Aluno alunoSelecionado =  (Aluno) adapter.getItemAtPosition(position);

                editarAluno.putExtra(FormularioActivity.ALUNO_SELECIONADO,alunoSelecionado);

                ListaAlunosActivity.this.startActivity(editarAluno);
            }
        });


       /* this.listaAlunos.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        Aluno aluno = (Aluno) parent.getItemAtPosition(position);
                        Toast.makeText(ListaAlunosActivity.this, "Clique longo: " + aluno, Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }
        );*/

                botaoAdiciona.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(ListaAlunosActivity.this, "Floating button clicado", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
                        startActivity(intent);
                    }
                });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.menu_enviar_notas:
                new EnviaAlunoTask(this).execute();
                return true;

            case R.id.menu_receber_provas:
                Intent provas = new Intent(this,ProvasActivity.class);
                startActivity(provas);
                return true;

            case R.id.menu_mapa:
                Intent maps = new Intent(this, MostraAlunosActivity.class);
                startActivity(maps);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.menu_lista_alunos, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.i("[INFO]","## INICIO DO ONRESUME");
        this.carregarListaDeAlunos();

        Log.i("[INFO]", "## FIM DO ONRESUME");
    }

    private void carregarListaDeAlunos() {
        List<Aluno> alunos;

        AlunoDAO dao = new AlunoDAO(this);
        alunos = dao.getListaAlunos();
        dao.close();

        final ListaAlunosAdapter adapter = new ListaAlunosAdapter(this, alunos);
        this.listaAlunos.setAdapter(adapter);
    }



    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final Aluno alunoSelecionado = (Aluno) listaAlunos.getAdapter().getItem(info.position);

        MenuItem ligar = menu.add("Ligar");
        MenuItem sms = menu.add("Enviar SMS");
        MenuItem mapa = menu.add("Achar no Mapa");
        MenuItem site = menu.add("Navegar no site");
        MenuItem deletar = menu.add("Deletar");

        Intent intentLigar = new Intent(Intent.ACTION_DIAL);
        intentLigar.setData(Uri.parse("tel:" + alunoSelecionado.getTelefone()));
        ligar.setIntent(intentLigar);

        Intent intentSMS =  new Intent(Intent.ACTION_VIEW);
        intentSMS.setData(Uri.parse("sms:" + alunoSelecionado.getTelefone()));
        intentSMS.putExtra("sms_body", "Olá, tudo bem?");
        sms.setIntent(intentSMS);

        Intent intentMapa = new Intent(Intent.ACTION_VIEW);
        intentMapa.setData(Uri.parse("geo:0,0?z=14&q="+alunoSelecionado.getEndereco()));
        mapa.setIntent(intentMapa);

        Intent intentSite = new Intent(Intent.ACTION_VIEW);
        String sites = alunoSelecionado.getSite();
        //sites = "google.com";
        Log.i("[SITES]", sites);
        if( !sites.startsWith("http://")){
            sites = "http://" + sites;
            Log.i("[SITES]", sites);
        }

        intentSite.setData(Uri.parse(sites));
        site.setIntent(intentSite);


        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Log.i("[INFO]", "## cliquei onMenuItemClick " + alunoSelecionado.getNome());
                new AlertDialog.Builder(ListaAlunosActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Deletar")
                        .setMessage("Realmente deseja deletar?")
                        .setPositiveButton("Quero",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        AlunoDAO dao = new AlunoDAO(ListaAlunosActivity.this);
                                        dao.delete(alunoSelecionado);
                                        dao.close();
                                        ListaAlunosActivity.this.carregarListaDeAlunos();
                                    }
                                }).setNegativeButton("Não", null).show();

                return false;
            }
        });

    }
}
