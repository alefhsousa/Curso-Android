package br.com.caelum.cadastro.br.com.caelum.cadastro.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import br.com.caelum.cadastro.R;
import br.com.caelum.cadastro.br.com.caelum.cadastro.modelo.Aluno;

/**
 * Created by alefh on 7/28/15.
 */
public class ListaAlunosAdapter extends BaseAdapter {
    private final List<Aluno> alunos;
    private final Activity activity;

    public ListaAlunosAdapter( Activity activity, List<Aluno> alunos) {
        this.alunos = alunos;
        this.activity = activity;
    }


    @Override
    public int getCount() {
        return this.alunos.size();
    }

    @Override
    public Object getItem(int position) {
        return this.alunos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.alunos.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = this.activity.getLayoutInflater().inflate(R.layout.item,parent,false);
        Aluno aluno = alunos.get(position);

        TextView nome = (TextView) view.findViewById(R.id.item_nome);
        nome.setText(aluno.getNome());

        //refatorar
        if(position % 2 == 0){
            view.setBackgroundColor(this.activity.getResources().getColor(R.color.linha_par));
        }else{
            view.setBackgroundColor(this.activity.getResources().getColor(R.color.linha_impar));
        }

        Bitmap bm;

        //refatorar ..
        if(aluno.getCaminhoFoto() != null){
            bm = BitmapFactory.decodeFile(aluno.getCaminhoFoto());
        }else{
            bm = BitmapFactory.decodeResource(activity.getResources(), R.drawable.ic_no_image);
        }

        bm = Bitmap.createScaledBitmap(bm, 100,100,true);

        ImageView imageView = (ImageView) view.findViewById(R.id.item_foto);
        imageView.setImageBitmap(bm);

        return view;
    }
}
