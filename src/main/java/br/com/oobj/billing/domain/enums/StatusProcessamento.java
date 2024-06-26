package br.com.oobj.billing.domain.enums;

import lombok.AllArgsConstructor;

import java.util.Objects;

@AllArgsConstructor
public enum StatusProcessamento {

    ENVIADO(0L),
    PROCESSANDO(1L),
    AUTORIZADO(2L),
    REJEITADO(3L);

    private final  Long codigo;

    public static StatusProcessamento fromCodigo(Long codigo) {
        for (StatusProcessamento statusProcessamento : StatusProcessamento.values()) {
            if (Objects.equals(statusProcessamento.codigo, codigo)) {
                return statusProcessamento;
            }
        }
        return null;
    }

}
