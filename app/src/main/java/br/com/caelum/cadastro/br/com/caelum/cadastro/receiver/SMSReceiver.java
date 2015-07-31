package br.com.caelum.cadastro.br.com.caelum.cadastro.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

import java.util.Objects;

import br.com.caelum.cadastro.R;
import br.com.caelum.cadastro.br.com.caelum.cadastro.dao.AlunoDAO;

/**
 * Created by alefh on 7/28/15.
 */
public class SMSReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Object[] mensagens = (Object[]) bundle.get("pdus");

        byte[] mensagem = (byte[]) mensagens[0];
        SmsMessage sms = SmsMessage.createFromPdu(mensagem);
        AlunoDAO alunoDAO = new AlunoDAO(context);
        MediaPlayer mp = MediaPlayer.create(context, R.raw.msg);


        if(alunoDAO.isAluno(sms.getDisplayOriginatingAddress())) {
            mp.start();
            Toast.makeText(context, "Chegou um SMS!!" + sms.getDisplayOriginatingAddress(),Toast.LENGTH_LONG).show();
        }
    }
}
