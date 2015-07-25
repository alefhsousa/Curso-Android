package br.com.caelum.cadastro.br.com.caelum.cadastro.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.caelum.cadastro.br.com.caelum.cadastro.modelo.Aluno;

/**
 * Created by alefh on 7/23/15.
 */
public class AlunoDAO extends SQLiteOpenHelper {
    
    private static final int VERSAO = 1;
    private static final String TABELA = "Alunos";
    private static final String DATABASE = "CadastroCaelum";

    public AlunoDAO(Context context) {
        super(context, DATABASE, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.i("[INFO]", "INICIANDO DDL");

        String ddl = "CREATE TABLE " + TABELA +
                    " (id INTEGER PRIMARY KEY, "
                +   " nome TEXT NOT NULL, "
                +   " telefone TEXT, "
                +   " endereco TEXT, "
                +   " site TEXT, "
                +   " nota REAL);";

        db.execSQL(ddl);

        Log.i("[INFO]", "FINALIZANDO DDL");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = " DROP TABLE IF EXISTIS " + TABELA;
        db.execSQL(sql);
        onCreate(db);
    }

    public void insere(Aluno aluno){
        Log.i("[INFO]", "INICIANDO INSERCAO");
        ContentValues values = new ContentValues();

        values.put("nome",aluno.getNome());
        values.put("telefone",aluno.getTelefone());
        values.put("endereco",aluno.getEndereco());
        values.put("site", aluno.getSite());
        values.put("nota", aluno.getNota());

        this.getWritableDatabase().insert(TABELA, null, values);

        Log.i("[INFO]", "FINALIZANDO INSERCAO");

        this.close();
    }

    public List<Aluno> getListaAlunos(){

        SQLiteDatabase database = this.getReadableDatabase();
        Cursor c = this.getReadableDatabase().rawQuery("Select * from " + TABELA + ";", null);
        List<Aluno> alunos = new ArrayList<>();

        int idNome = c.getColumnIndex("nome");
        int identificador = c.getColumnIndex("id");
        int idTelefone = c.getColumnIndex("telefone");
        int idEndereco = c.getColumnIndex("endereco");
        int idSite = c.getColumnIndex("site");
        int idNota = c.getColumnIndex("nota");

        while (c.moveToNext()){
            Aluno aluno = new Aluno();

            aluno.setId(c.getLong(identificador));
            aluno.setNome(c.getString(idNome));
            aluno.setTelefone(c.getString(idTelefone));
            aluno.setEndereco(c.getString(idEndereco));
            aluno.setSite(c.getString(idSite));
            aluno.setNota(c.getDouble(idNota));

            alunos.add(aluno);
        }

        c.close();
        return alunos;
    }

    public void delete(Aluno aluno){

        String[] args = {aluno.getId().toString()};
        this.getWritableDatabase().delete(TABELA, "id=?",args);
    }

}
