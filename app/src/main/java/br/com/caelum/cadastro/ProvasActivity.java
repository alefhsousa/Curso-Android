package br.com.caelum.cadastro;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import br.com.caelum.cadastro.br.com.caelum.cadastro.modelo.Prova;
import br.com.caelum.cadastro.br.com.caelum.fragment.DetalhesProvaFragment;
import br.com.caelum.cadastro.br.com.caelum.fragment.ListaProvasFragment;


public class ProvasActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provas);

       FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
       if(this.isTablet()){
           transaction.replace(R.id.provas_lista, new ListaProvasFragment());
           transaction.replace(R.id.provas_detalhes,new DetalhesProvaFragment());
       }else{
           transaction.replace(R.id.provas_view, new ListaProvasFragment());
       }

        transaction.commit();

    }

    private boolean isTablet() {
        return this.getResources().getBoolean(R.bool.isTablet);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_provas, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void selecionaProva(Prova prova) {
        FragmentManager fragmentManager = this.getSupportFragmentManager();

        if(isTablet()){
            DetalhesProvaFragment detalhesProva = (DetalhesProvaFragment) fragmentManager.findFragmentById(R.id.provas_detalhes);
            detalhesProva.populaCamposComDados(prova);
        }else{
            Bundle arguments = new Bundle();
            arguments.putSerializable("prova",prova);

            DetalhesProvaFragment detalhesProva = new DetalhesProvaFragment();
            detalhesProva.setArguments(arguments);

            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.provas_view, detalhesProva);
            transaction.addToBackStack(null); //faz o android colocar a instrução em uma nova pilha
            transaction.commit();
        }
    }
}
