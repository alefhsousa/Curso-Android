package br.com.caelum.cadastro;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
    private ImageView imagePerfil;



    private Button fotoButton;

    public  FormularioHelper(FormularioActivity activity){
        this.aluno = new Aluno();

        this.fotoButton = (Button) activity.findViewById(R.id.botaoCameraFormulario);
        this.imagePerfil = (ImageView) activity.findViewById(R.id.fotoPerfil);
        this.nome = (EditText) activity.findViewById(R.id.form_nome);
        this.telefone = (EditText) activity.findViewById(R.id.form_telefone);
        this.site = (EditText) activity.findViewById(R.id.form_site);
        this.nota = (RatingBar) activity.findViewById(R.id.form_nota);
        this.endereco = (EditText) activity.findViewById(R.id.form_endereco);

    }
    public void colocaNoFormulario(Aluno aluno){
        this.nome.setText(aluno.getNome().toString());
        this.telefone.setText(aluno.getTelefone().toString());
        this.site.setText(aluno.getSite().toString());
        this.nota.setRating(aluno.getNota().floatValue());
        this.endereco.setText(aluno.getEndereco().toString());

        this.aluno = aluno;

    }
    public Aluno pegaAlunoDoFormulario(){
        aluno.setNome(nome.getText().toString());
        aluno.setEndereco(endereco.getText().toString());
        aluno.setSite(site.getText().toString());
        aluno.setTelefone(telefone.getText().toString());
        aluno.setNota(Double.valueOf(nota.getProgress()));
        aluno.setCaminhoFoto((String) imagePerfil.getTag());
        return aluno;
    }

    public Button getFotoButton() {
        return fotoButton;
    }

    public void carregaImagem(String localArquivoFoto) {
        Bitmap imagemFoto = BitmapFactory.decodeFile(localArquivoFoto);
        Bitmap imagemFotoReduzida = Bitmap.createScaledBitmap(
                                    imagemFoto, imagemFoto.getWidth(),300,true);

        this.imagePerfil.setImageBitmap(imagemFotoReduzida);
        this.imagePerfil.setTag(localArquivoFoto);
        this.imagePerfil.setScaleType(ImageView.ScaleType.FIT_XY);


    }
}
