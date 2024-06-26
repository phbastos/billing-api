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
    @Field(RequestDataProperties.ID)
    @Indexed(name = "idx_id")
    private String id;

    @Field(RequestDataProperties.TIPO_REQUEST)
    private TipoRequest tipoRequest;

    @Field(RequestDataProperties.STATUS_CODE)
    @Indexed(name = "idx_statusCode")
    private Long statusCode;

    @Field(RequestDataProperties.DATA_HORA_REQUEST)
    @Indexed(name = "idx_dataHoraRequest")
    private String dataHoraRequest;

    @Field(RequestDataProperties.HAS_ERROR)
    @Indexed(name = "idx_hasError")
    private Boolean hasError;

    @Field(RequestDataProperties.ERROR_MESSAGE)
    private String errorMessage;

    @Field(RequestDataProperties.TIPO_AMBIENTE)
    @Indexed(name = "idx_tipoAmbiente")
    private TipoAmbiente tipoAmbiente;

    @Field(RequestDataProperties.CNPJ_EMITENTE)
    @Indexed(name = "idx_cnpjEmitente")
    private String cnpjEmitente;

    @Field(RequestDataProperties.QUANTIDADE_DOCUMENTOS)
    private Long quantidadeDocumentos;

    @Field(RequestDataProperties.IP_ADDRESS)
    private String ipAddress;

    @Field(RequestDataProperties.DATA_HORA_RESPONSE)
    private String dataHoraResponse;

    @Field(RequestDataProperties.DATA_HORA_EXPIRE)
    private LocalDateTime dataHoraExpire; //TODO esse campo nao deveria existir

    @Field(RequestDataProperties.REQUEST_PAYLOAD)
    private String requestPayload;

    @Field(RequestDataProperties.RESPONSE_PAYLOAD)
    private String responsePayload;

    @Field(RequestDataProperties.UPDATED_AT)
    private String updatedAt;

    public static class RequestDataProperties {
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
        public static final String UPDATED_AT = "updatedAt";

    }

}
