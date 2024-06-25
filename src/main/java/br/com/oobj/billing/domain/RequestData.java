package br.com.oobj.billing.domain;

import br.com.oobj.billing.domain.enums.TipoAmbiente;
import br.com.oobj.billing.domain.enums.TipoRequest;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Data
@Builder
@Document(collection = "request_billing")
public class RequestData {

    @Id
    @Field(RequestBillingProperties.ID)
    @Indexed(name = "idx_id")
    private String id;

    @Field(RequestBillingProperties.TIPO_REQUEST)
    private TipoRequest tipoRequest;

    @Field(RequestBillingProperties.STATUS_CODE)
    @Indexed(name = "idx_statusCode")
    private String statusCode;

    @Field(RequestBillingProperties.DATA_HORA_REQUEST)
    @Indexed(name = "idx_dataHoraRequest")
    private String dataHoraRequest;

    @Field(RequestBillingProperties.HAS_ERROR)
    @Indexed(name = "idx_hasError")
    private Boolean hasError;

    @Field(RequestBillingProperties.ERROR_MESSAGE)
    private String errorMessage;

    @Field(RequestBillingProperties.TIPO_AMBIENTE)
    @Indexed(name = "idx_tipoAmbiente")
    private TipoAmbiente tipoAmbiente;

    @Field(RequestBillingProperties.CNPJ_EMITENTE)
    @Indexed(name = "idx_cnpjEmitente")
    private String cnpjEmitente;

    @Field(RequestBillingProperties.QUANTIDADE_DOCUMENTOS)
    private Long quantidadeDocumentos;

    @Field(RequestBillingProperties.IP_ADDRESS)
    private String ipAddress;

    @Field(RequestData.RequestBillingProperties.DATA_HORA_RESPONSE)
    private String dataHoraResponse;

    @Field(RequestBillingProperties.DATA_HORA_EXPIRE)
    private LocalDateTime dataHoraExpire; //TODO esse campo nao deveria existir

    @Field(RequestBillingProperties.REQUEST_PAYLOAD)
    private String requestPayload;

    @Field(RequestBillingProperties.RESPONSE_PAYLOAD)
    private String responsePayload;

    public static class RequestBillingProperties {
        public static final String ID = "id";
        public static final String TIPO_REQUEST = "tipoRequest";
        public static final String STATUS_CODE = "statusCode";
        public static final String DATA_HORA_REQUEST = "dataHoraRequest";
        public static final String HAS_ERROR = "hasError";
        public static final String TIPO_AMBIENTE = "tipoAmbiente";
        public static final String CNPJ_EMITENTE = "cnpjEmitente";
        public static final String QUANTIDADE_DOCUMENTOS = "quantidadeDocumentos";
        public static final String IP_ADDRESS = "ipAddress";
        public static final String ERROR_MESSAGE = "errorMessage";
        public static final String DATA_HORA_RESPONSE = "dataHoraResponse";
        public static final String DATA_HORA_EXPIRE = "dataHoraExpire";
        public static final String REQUEST_PAYLOAD = "requestPayload";
        public static final String RESPONSE_PAYLOAD = "responsePayload";

    }

}
