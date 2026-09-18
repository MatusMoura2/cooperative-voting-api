package com.cooperativevoting.application.ports.out;

public interface CpfValidationPort {
    boolean canVote(String cpf);
}
