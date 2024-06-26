package br.com.oobj.billing.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessageHeaders {

    //Request
    ID_REQUISICAO("id_requisicao"),
    DATA_TYPE("data_type"),
    TIPO_REQUISICAO("tipo_requisicao"),
    STATUS_CODE("status_code"),
    DATA_HORA_REQUEST("data_hora_request"),
    DATA_HORA_RESPONSE("data_hora_response"),
    QUANTIDADE_DOCUMENTOS("quantidade_documentos"),
    IP_ADDRESS("ip_address"),
    REQUEST_PAYLOAD("request_payload"),
    RESPONSE_PAYLOAD("response_payload"),
    CNPJ_EMITENTE("cnpj_emitente"),

    //Ambos,
    HAS_ERROR("has_error"),
    ERROR_MESSAGE("error_message"),

    //Document,
    CHAVE_UNICA("chave_unica"),
    RETORNO_ENGINES("retorno_engines"),
    CSTAT("cstat"),
    SERIE("serie"),
    NUMERO_NF("numero_nf"),
    RETORNO_CONFIRMADO("retorno_confirmado"),
    X_MOTIVO("x_motivo"),
    UPDATED_AT("updated_at"),
    DATA_HORA_ENVIO_ENGINES("data_hora_envio_engines"),
    DATA_HORA_RETORNO_ENGINES("data_hora_retorno_engines"),
    DATA_HORA_CONSULTA_SAP("data_hora_consulta_sap"),
    DATA_HORA_CONFIRMACAO_SAP("data_hora_confirmacao_sap"),
    STATUS_PROCESSAMENTO("status_processamento");


    private final String header;

}
