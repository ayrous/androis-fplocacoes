package br.com.senai.fplocacoes.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import br.com.senai.fplocacoes.model.Veiculos;

/**
 * Created by 22853582884 on 25/04/2018.
 */

public class VeiculoDAO extends SQLiteOpenHelper{


    public VeiculoDAO(Context context) {
        super(context, "Veiculinhos", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String sql = "CREATE TABLE veiculos(" +
                "id INTEGER PRIMARY KEY," +
                "nome TEXT," +
                "descricao TEXT," +
                "tipo TEXT," +
                "caminhoDaFoto TEXT" +
                ");";
        sqLiteDatabase.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        String sql = "DROP TABLE IF EXISTS veiculos";
        sqLiteDatabase.execSQL(sql);

    }

    public void salvar(Veiculos veiculo){
        SQLiteDatabase dtb = getWritableDatabase();
        ContentValues dados = getContentValues(veiculo);
        dtb.insert("veiculos", null, dados);
    }

    @NonNull
    private ContentValues getContentValues(Veiculos veiculo) {
    ContentValues dados = new ContentValues();

    dados.put("nome", veiculo.getNome());
    dados.put("descricao", veiculo.getDescricao());
    dados.put("tipo", veiculo.getTipo());
    dados.put("caminhoDaFoto", veiculo.getCaminhoDaImagem());

    return dados;
    }

    public List<Veiculos> buscaVeiculos(){
        String sql = "SELECT * FROM veiculos";
        SQLiteDatabase dtb = getReadableDatabase();

        Cursor c = dtb.rawQuery(sql, null);
        List<Veiculos> veiculos = new ArrayList<Veiculos>();

        while(c.moveToNext()){
            Veiculos veiculo = new Veiculos();
            veiculo.setId(c.getLong(c.getColumnIndex("id")));
            veiculo.setNome(c.getString(c.getColumnIndex("nome")));
            veiculo.setDescricao(c.getString(c.getColumnIndex("descricao")));
            veiculo.setTipo(c.getString(c.getColumnIndex("tipo")));
            veiculo.setCaminhoDaImagem(c.getString(c.getColumnIndex("caminhoDaFoto")));

            veiculos.add(veiculo);
        }
        return veiculos;
    }

    public void remover(Veiculos veiculo){
        SQLiteDatabase dtb = getWritableDatabase();
        String[] parametros = {
                String.valueOf(veiculo.getId())
        };
        dtb.delete("veiculos", "id = ?", parametros);

    }

    public void editar(Veiculos veiculo){
        SQLiteDatabase dtb = getWritableDatabase();
        ContentValues dados = getContentValues(veiculo);
        String[] parametros = {
                veiculo.getId().toString()
        };
        dtb.update("veiculos", dados, "id = ?", parametros);
    }

    public Veiculos localizar(Long veiculoId) {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "SELECT * FROM veiculos WHERE id = ?";
        Cursor c = db.rawQuery(sql, new String[]{String.valueOf(veiculoId)});

        c.moveToFirst();
        Veiculos veiculoRetornado = new Veiculos();
        veiculoRetornado.setId(c.getLong(c.getColumnIndex("id")));
        veiculoRetornado.setNome(c.getString(c.getColumnIndex("nome")));
        veiculoRetornado.setDescricao(c.getString(c.getColumnIndex("descricao")));
        veiculoRetornado.setTipo(c.getString(c.getColumnIndex("tipo")));
        veiculoRetornado.setCaminhoDaImagem(c.getString(c.getColumnIndex("caminhoDaFoto")));

        db.close();
        return veiculoRetornado;
    }
}
