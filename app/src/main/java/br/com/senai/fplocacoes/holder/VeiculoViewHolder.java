package br.com.senai.fplocacoes.holder;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import br.com.senai.fplocacoes.CadastroActivity;
import br.com.senai.fplocacoes.R;
import br.com.senai.fplocacoes.adapter.VeiculoRecycleAdapter;
import br.com.senai.fplocacoes.model.Veiculos;

/**
 * Created by 22853582884 on 25/04/2018.
 */

public class VeiculoViewHolder extends RecyclerView.ViewHolder{

    private final VeiculoRecycleAdapter adapter;
    private Long veiculoId;
    private TextView nome;
    private RadioButton onibus;
    private RadioButton van;
    private ImageView imagem;


    public VeiculoViewHolder(View itemView, VeiculoRecycleAdapter adapter) {
        super(itemView);
        this.adapter = adapter;

        nome = itemView.findViewById(R.id.textName);
        imagem = itemView.findViewById(R.id.imgTipos);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Activity context = (Activity) view.getContext();
                final Intent intent = new Intent(context, CadastroActivity.class);

                intent.putExtra("veiculoId", veiculoId);
                context.startActivityForResult(intent, 1);
            }
        });
    }

    public void preencher(Veiculos veiculo){
        veiculoId = veiculo.getId();
        nome.setText(veiculo.getNome());

        Bitmap imgConvertida = BitmapFactory.decodeFile(veiculo.getCaminhoDaImagem());
        imagem.setImageBitmap(imgConvertida);

    }

}
