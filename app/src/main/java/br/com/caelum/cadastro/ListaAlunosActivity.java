package br.com.caelum.cadastro;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.com.caelum.cadastro.br.com.caelum.cadastro.dao.AlunoDAO;
import br.com.caelum.cadastro.br.com.caelum.cadastro.modelo.Aluno;

/**
 * Created by alefh on 7/21/15.
 */
public class ListaAlunosActivity extends Activity {
    private   ListView listaAlunos;

    public  static  final String ALUNO_SELECIONADO = "alunoSelecionado";

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

                editarAluno.putExtra(ALUNO_SELECIONADO,alunoSelecionado);

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

        final ArrayAdapter<Aluno> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, alunos);
        this.listaAlunos.setAdapter(adapter);
    }



    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final Aluno alunoSelecionado = (Aluno) listaAlunos.getAdapter().getItem(info.position);

        menu.add("Ligar");

        MenuItem deletar = menu.add("Deletar");

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
                                }).setNegativeButton("Não",null).show();

                return false;
            }
        });

    }
}
