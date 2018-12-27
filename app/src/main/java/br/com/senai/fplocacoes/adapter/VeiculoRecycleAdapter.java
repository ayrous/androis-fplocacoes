package br.com.senai.fplocacoes.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.com.senai.fplocacoes.R;
import br.com.senai.fplocacoes.holder.VeiculoViewHolder;
import br.com.senai.fplocacoes.model.Veiculos;

/**
 * Created by 22853582884 on 25/04/2018.
 */

public class VeiculoRecycleAdapter extends RecyclerView.Adapter {

    private final Context context;
    private final List<Veiculos> veiculos;

    public VeiculoRecycleAdapter(Context context, List<Veiculos> veiculos) {
        this.context = context;
        this.veiculos = veiculos;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.lista_veiculos, parent, false);
        VeiculoViewHolder holder = new VeiculoViewHolder(view, this);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        VeiculoViewHolder meuHolder = (VeiculoViewHolder) holder;
        Veiculos veiculo = veiculos.get(position);
        meuHolder.preencher(veiculo);
    }

    @Override
    public int getItemCount() {
        return veiculos.size();
    }
}
