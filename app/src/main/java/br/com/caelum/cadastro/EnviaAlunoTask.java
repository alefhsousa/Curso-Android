package br.com.caelum.cadastro;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.List;
import java.util.Objects;

import br.com.caelum.cadastro.br.com.caelum.cadastro.converter.AlunoConverter;
import br.com.caelum.cadastro.br.com.caelum.cadastro.dao.AlunoDAO;
import br.com.caelum.cadastro.br.com.caelum.cadastro.modelo.Aluno;
import br.com.caelum.cadastro.br.com.caelum.cadastro.support.WebClient;

/**
 * Created by alefh on 7/29/15.
 */
public class EnviaAlunoTask extends AsyncTask<Object, Object, String> {

    private final Context context;
    private  ProgressDialog progressDialog;

    public EnviaAlunoTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = ProgressDialog.show(context, "Por favor, aguarde ...", "Enviando dados para WEB",true,true);
    }

    @Override
    protected String doInBackground(Object... params) {

        AlunoDAO dao = new AlunoDAO(context);
        List<Aluno> alunos = dao.getListaAlunos();
        dao.close();

        String json =  new AlunoConverter().toJSON(alunos);
        WebClient client = new WebClient();
        String resposta = client.post(json);

        return resposta;
    }

    @Override
    protected void onPostExecute(String result) {
        progressDialog.dismiss();
        Toast.makeText(context,result,Toast.LENGTH_LONG).show();
    }
}
