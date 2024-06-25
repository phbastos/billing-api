package br.com.oobj.billing.domain.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum TipoRequest {

    ENVIO_B_SYNC(0L),
    ENVIO_BATCH(1L),
    QUEUE(2L),
    CONTINGENCIA(3L),
    QUEUE_CONF(4L),
    CANCELAMENTO(5L);

    private Long codigo;

    public static TipoRequest fromCodigo(Long codigo) {
        for (TipoRequest tipoRequest : TipoRequest.values()) {
            if (tipoRequest.codigo == codigo) {
                return tipoRequest;
            }
        }
        return null;
    }
}
