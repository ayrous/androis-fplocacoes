package br.com.senai.fplocacoes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import br.com.senai.fplocacoes.model.Imagem;
import br.com.senai.fplocacoes.model.Veiculos;

/**
 * Created by 22853582884 on 25/04/2018.
 */

public class VeiculoHelper {
    public EditText nome;
    public EditText descricao;
    public RadioButton onibus;
    public RadioButton van;
    public Veiculos veiculo;
    private Imagem imagem;
    private ImageView imgCarregada;

    public VeiculoHelper(CadastroActivity cadastroActivity) {

        nome = cadastroActivity.findViewById(R.id.editNome);
        descricao = cadastroActivity.findViewById(R.id.editDescricao);
        onibus = cadastroActivity.findViewById(R.id.radioOnibus);
        van = cadastroActivity.findViewById(R.id.radioVan);

        veiculo = new Veiculos();
    }

    public Veiculos pegarVeiculo(){
        veiculo.setNome(nome.getText().toString());
        veiculo.setDescricao(descricao.getText().toString());

        if(onibus.isChecked()){
            veiculo.setTipo(onibus.getText().toString());
        } else if(van.isChecked()){
            veiculo.setTipo(van.getText().toString());
        }
        return veiculo;
    }

    public void preencherVeiculo(Veiculos veiculo){
        nome.setText(veiculo.getNome());
        descricao.setText(veiculo.getDescricao());

        if(onibus.isChecked()){
            veiculo.setTipo(veiculo.getTipo());
        } else if(van.isChecked()){
            veiculo.setTipo(veiculo.getTipo());
        }

        Bitmap imgConvertida = BitmapFactory.decodeFile(veiculo.getCaminhoDaImagem());
        imgCarregada.setImageBitmap(imgConvertida);

        this.veiculo = veiculo;

    }
}