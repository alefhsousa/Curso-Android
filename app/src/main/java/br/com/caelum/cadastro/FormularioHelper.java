package br.com.caelum.cadastro;

import android.widget.EditText;
import android.widget.RatingBar;

import br.com.caelum.cadastro.br.com.caelum.cadastro.modelo.Aluno;

/**
 * Created by alefh on 7/23/15.
 */
public class FormularioHelper {
    private Aluno aluno;
    private EditText nome;
    private EditText telefone;
    private EditText site;
    private RatingBar nota;
    private EditText endereco;


    public  FormularioHelper(FormularioActivity activity){
        this.aluno = new Aluno();

        this.nome = (EditText) activity.findViewById(R.id.form_nome);
        this.telefone = (EditText) activity.findViewById(R.id.form_telefone);
        this.site = (EditText) activity.findViewById(R.id.form_site);
        this.nota = (RatingBar) activity.findViewById(R.id.form_nota);
        this.endereco = (EditText) activity.findViewById(R.id.form_endereco);

    }
    public void populaInfoAluno(Aluno aluno){
        this.nome.setText(aluno.getNome().toString());
        this.telefone.setText(aluno.getNome().toString());
        this.site.setText(aluno.getSite().toString());
        this.nota.setRating(aluno.getNota().floatValue());
        this.endereco.setText(aluno.getEndereco().toString());
    }
    public Aluno pegaAlunoDoFormulario(){
        aluno.setNome(nome.getText().toString());
        aluno.setEndereco(endereco.getText().toString());
        aluno.setSite(site.getText().toString());
        aluno.setTelefone(telefone.getText().toString());
        aluno.setNota(Double.valueOf(nota.getProgress()));

        return aluno;
    }
}
