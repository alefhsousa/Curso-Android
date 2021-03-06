package br.com.caelum.cadastro.br.com.caelum.cadastro.modelo;

import java.io.Serializable;

/**
 * Created by alefh on 7/23/15.
 */
public class Aluno  implements Serializable{
    private String nome;
    private String telefone;
    private String endereco;
    private String site;
    private Double nota;
    private Long id;
    private String caminhoFoto;



    @Override
    public String toString() {
        return this.getNome();
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getNome() { return nome; }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCaminhoFoto() {
        return caminhoFoto;
    }

    public void setCaminhoFoto(String caminhoFoto) {
        this.caminhoFoto = caminhoFoto;
    }
}
