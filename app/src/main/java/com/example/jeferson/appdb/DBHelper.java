//******************************************************

//Instituto Federal de São Paulo - Campus Sertãozinho

//Disciplina......: M4DADM

//Programação de Computadores e Dispositivos Móveis

//Aluno...........: Jeferson Rodrigues

//******************************************************
package com.example.jeferson.appdb;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeferson on 17/12/2016.
 */

public class DBHelper {

    // OBJETIVO.......: Declarar  atributos da classe

    private static final String DATABASE_NAME = "sistemacadpf.db"; /*Nome do banco de dados*/
    private static final int DATABASE_VERSION = 1; /*Versão do banco de dados*/
    private static final String TABLE_NAME = "contatospf"; /*Nome da tabela do banco de dados*/

    private Context context;
    private SQLiteDatabase db;

    // OBJETIVO.......:  atributos para a criação do banco de dados

    private SQLiteStatement insertStnt;

    // OBJETIVO.......: Preparar a instrução SQL para ser reaproveitada em qualquer sessão
    private static final String INSERT = "insert into " + TABLE_NAME + " (nome, cpf, idade, telefone, email) VALUES (?,?,?,?,?)";

    // OBJETIVO.......: Criar o DBHelper
    public DBHelper(Context context) {
        this.context = context;
        OpenHelper openHelper = new OpenHelper(this.context);
        this.db = openHelper.getWritableDatabase();
        this.insertStnt = this.db.compileStatement(INSERT);
    }


    public long insert(String nome, String cpf, String idade, String telefone, String email) {
        this.insertStnt.bindString(1, nome);
        this.insertStnt.bindString(2, cpf);
        this.insertStnt.bindString(3, idade);
        this.insertStnt.bindString(4, telefone);
        this.insertStnt.bindString(5, email);

        return this.insertStnt.executeInsert();
    }


    // OBJETIVO.......: Retornar informações do BD em formato lista

    public List<Contato> recuperaDados() {

        List<Contato> list = new ArrayList<Contato>();

        try {
            Cursor cursor = this.db.query(TABLE_NAME, new String[]{"nome", "cpf", "idade", "telefone", "email"},
                    null, null, null, null, null, null);
            int contaRegistros = cursor.getCount();
            if (contaRegistros != 0) {
                cursor.moveToFirst();
                do {
                    Contato contato = new Contato(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
                    list.add(contato);
                } while (cursor.moveToNext());

                if (cursor != null && ! cursor.isClosed())
                    cursor.close();
                    return list;
            } else
                 return null;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            //return null;
        }
        return list;
    }

    // OBJETIVO.......: Criar o banco de dados caso não exista
    private static class OpenHelper extends SQLiteOpenHelper {

        OpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }


        public void onCreate(SQLiteDatabase db) {
            String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (idCad INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT, cpf TEXT, idade TEXT, telefone TEXT, email TEXT);";
            db.execSQL(sql);
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }
}