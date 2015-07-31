package br.com.caelum.cadastro.br.com.caelum.cadastro.support;

import android.content.Entity;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by alefh on 7/29/15.
 */
public class WebClient {

    private static final String urlServidor = "http://www.caelum.com.br/mobile";

    public String post(String json){
        DefaultHttpClient client = new DefaultHttpClient();
        HttpResponse response = null;
        HttpPost post = new HttpPost(urlServidor);
        try {
            post.setEntity(new StringEntity(json));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        post.setHeader("Content-type","application/json");
        post.setHeader("Accept", "application/json");

        try {
             response = client.execute(post);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            return EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
