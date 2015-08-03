package br.com.caelum.cadastro.br.com.caelum.fragment;



import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import br.com.caelum.cadastro.br.com.caelum.cadastro.dao.AlunoDAO;
import br.com.caelum.cadastro.br.com.caelum.cadastro.modelo.Aluno;
import br.com.caelum.cadastro.br.com.caelum.cadastro.util.Localizador;

public class MapaFragment extends SupportMapFragment {

    @Override
    public void onResume() {
        super.onResume();
        Localizador localizador = new Localizador(this.getActivity());
        LatLng local = localizador.getCoordenada("Rua Vergueiro 3185 Vila Mariana");

        this.centralizaNo(local);

        AlunoDAO dao = new AlunoDAO(this.getActivity());
        List<Aluno> alunos = dao.getListaAlunos();
        dao.close();

        for(Aluno aluno : alunos){
            LatLng coordenada = localizador.getCoordenada(aluno.getEndereco());

            if (coordenada != null){
                MarkerOptions marcador = new MarkerOptions()
                                            .position(coordenada)
                                            .title(aluno.getNome())
                                            .snippet(aluno.getEndereco());
                this.getMap().addMarker(marcador);
            }
        }

    }

    private void centralizaNo(LatLng local) {
        GoogleMap mapa = this.getMap();

        mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(local,17));
    }
}
