package br.com.caelum.cadastro.br.com.caelum.cadastro.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by alefh on 7/28/15.
 */
public class SMSReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Chegou um SMS!!",Toast.LENGTH_LONG).show();
    }
}
