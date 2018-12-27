package br.com.senai.fplocacoes.model;

/**
 * Created by 22853582884 on 27/04/2018.
 */

public class Imagem {

    private Long id;
    private String caminhoDaFoto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCaminhoDaFoto() {
        return caminhoDaFoto;
    }

    public void setCaminhoDaFoto(String caminhoDaFoto) {
        this.caminhoDaFoto = caminhoDaFoto;
    }
}
