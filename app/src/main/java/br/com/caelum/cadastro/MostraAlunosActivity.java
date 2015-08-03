package br.com.caelum.cadastro;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import br.com.caelum.cadastro.br.com.caelum.fragment.MapaFragment;

/**
 * Created by alefh on 7/31/15.
 */
public class MostraAlunosActivity  extends AppCompatActivity{

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_mostra_alunos);

        MapaFragment mapaFragment = new MapaFragment();
        Log.i("[MAPA]","inicio");
        FragmentTransaction tx = this.getSupportFragmentManager().beginTransaction();
        Log.i("[MAPA]","AntesReplace");
        tx.replace(R.id.mostra_alunos_mapa, mapaFragment);
        Log.i("[MAPA]", "posReplace");
        tx.commit();
    }
}
