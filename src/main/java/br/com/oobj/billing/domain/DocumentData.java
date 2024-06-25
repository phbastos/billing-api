package br.com.oobj.billing.domain;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Builder
@Document(collection = "request_document")
public class DocumentData {

    @Id
    @Field(RequestDocumentProperties.ID)
    private String id;

    @Field(RequestDocumentProperties.ID_REQUEST)
    private String idRequest;

    @Field(RequestDocumentProperties.CHAVE_UNICA)
    private String chaveUnica;

    @Field(RequestDocumentProperties.RETORNO_ENGINES)
    private String retornoEngines;

    @Field(RequestDocumentProperties.C_STAT)
    private String cStat;

    @Field(RequestDocumentProperties.SERIE)
    private String serie;

    @Field(RequestDocumentProperties.NUMERO_NF)
    private String numeroNf;

    @Field(RequestDocumentProperties.RETORNO_CONFIRMADO)
    private String retornoConfirmado;

    @Field(RequestDocumentProperties.X_MOTIVO)
    private String xMotivo;

    @Field(RequestDocumentProperties.UPDATED_AT)
    private String updatedAt;

    @Field(RequestDocumentProperties.DATA_HORA_ENVIO_ENGINES)
    private String dataHoraEnvioEngines;

    @Field(RequestDocumentProperties.DATA_HORA_RETORNO_ENGINES)
    private String dataHoraRetornoEngines;

    @Field(RequestDocumentProperties.DATA_HORA_CONSULTA_SAP)
    private String dataHoraConsultaSAP;

    @Field(RequestDocumentProperties.DATA_HORA_CONFIRMACAO_SAP)
    private String dataHoraConfirmacaoSAP;

    @Field(RequestDocumentProperties.DURACAO_PROC_ENGINES)
    private String duracaoProcEngines;

    @Field(RequestDocumentProperties.DURACAO_SAP_CONSULT_CONFIRM)
    private String duracaoSAPConsultConfirm;

    @Field(RequestDocumentProperties.HAS_ERROR)
    private Boolean hasError;

    @Field(RequestDocumentProperties.ERROR_MESSAGE)
    private String errorMessage;

    public static class RequestDocumentProperties {
        public static final String ID = "id";
        public static final String ID_REQUEST = "idRequest";
        public static final String CHAVE_UNICA = "chaveUnica";
        public static final String RETORNO_ENGINES = "retornoEngines";
        public static final String C_STAT = "cStat";
        public static final String SERIE = "serie";
        public static final String NUMERO_NF = "numeroNf";
        public static final String RETORNO_CONFIRMADO = "retornoConfirmado";
        public static final String X_MOTIVO = "xMotivo";
        public static final String UPDATED_AT = "updatedAt";
        public static final String DATA_HORA_ENVIO_ENGINES = "dataHoraEnvioEngines";
        public static final String DATA_HORA_RETORNO_ENGINES = "dataHoraRetornoEngines";
        public static final String DATA_HORA_CONSULTA_SAP = "dataHoraConsultaSAP";
        public static final String DATA_HORA_CONFIRMACAO_SAP = "dataHoraConfirmacaoSAP";
        public static final String DURACAO_PROC_ENGINES = "duracaoProcEngines";
        public static final String DURACAO_SAP_CONSULT_CONFIRM = "duracaoSAPConsultConfirm";
        public static final String HAS_ERROR = "hasError";
        public static final String ERROR_MESSAGE = "errorMessage";
    }

}
