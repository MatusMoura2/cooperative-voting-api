package com.cooperativevoting.domain.model;

public class Associado {
    private String id;
    private String cpf;

    public Associado(String id, String cpf) {
        this.id = id;
        this.cpf = cpf;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
}
