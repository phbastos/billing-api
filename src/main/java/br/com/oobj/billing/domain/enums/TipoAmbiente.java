package br.com.oobj.billing.domain.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum TipoAmbiente {

    PRODUCAO(1),
    HOMOLOGACAO(2);

    private int codigo;

    public static TipoAmbiente fromCodigo(int codigo) {
        for (TipoAmbiente tipoAmbiente : TipoAmbiente.values()) {
            if (tipoAmbiente.codigo == codigo) {
                return tipoAmbiente;
            }
        }
        return null;
    }

}
