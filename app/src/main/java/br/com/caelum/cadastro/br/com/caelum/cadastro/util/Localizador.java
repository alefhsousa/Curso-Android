package br.com.caelum.cadastro.br.com.caelum.cadastro.util;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;

import br.com.caelum.cadastro.ListaAlunosActivity;

/**
 * Created by alefh on 7/31/15.
 */
public class Localizador {
    private Geocoder geocoder;

    public Localizador(Context context) {

        this.geocoder = new Geocoder(context);
    }



    public LatLng getCoordenada(String endereco){
        try {
            List<Address> listaDeEnderecos;
            listaDeEnderecos = geocoder.getFromLocationName(endereco,1);
            if( ! listaDeEnderecos.isEmpty()){
                Address resultado = listaDeEnderecos.get(0);
                return  new LatLng(resultado.getLatitude(),resultado.getLatitude());
            }else {
                return null;
            }
        }catch (IOException e){
            return null;
        }
    }
}
