package br.com.oobj.billing.service.impl;

import br.com.oobj.billing.domain.RequestData;
import br.com.oobj.billing.domain.enums.MessageHeaders;
import br.com.oobj.billing.domain.enums.TipoRequest;
import br.com.oobj.billing.exceptions.DataNotFoundException;
import br.com.oobj.billing.exceptions.IdRequisicaoNaoInformadoException;
import br.com.oobj.billing.repository.RequestDataRepository;
import br.com.oobj.billing.service.DataService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

@Slf4j
@Service
@AllArgsConstructor
public class RequestDataService implements DataService {

    private final RequestDataRepository repository;

    public RequestData save(RequestData requestData) {
        return repository.save(requestData);
    }

    public void processNewData(Message message) {
        Map<String, Object> headers = message.getMessageProperties().getHeaders();
        String body = new String(message.getBody());
        RequestData requestData = createRequestData(headers, body);
        save(requestData);
    }

    public void processUpdateData(Message message) throws IdRequisicaoNaoInformadoException, DataNotFoundException {
        Map<String, Object> headers = message.getMessageProperties().getHeaders();
        String idRequest = (String) headers.get(MessageHeaders.ID_REQUISICAO.getHeader());
        if (StringUtils.isEmpty(idRequest.trim())) {
            throw new IdRequisicaoNaoInformadoException("ID da requisição não informado. Não é possível prosseguir sem essa informação.");
        }

        Optional<RequestData> requestDataFromDatabase = repository.findById(idRequest);

        if (!requestDataFromDatabase.isPresent()) {
            throw new DataNotFoundException("Requisição não encontrada.");
        }

        RequestData requestData = requestDataFromDatabase.get();

        updateRequestData(requestData, headers, new String(message.getBody(), StandardCharsets.UTF_8));

    }

    private RequestData createRequestData(Map<String, Object> headers, String body) {
        return RequestData.builder()
                .tipoRequest(TipoRequest.fromCodigo((Long) headers.get(MessageHeaders.TIPO_REQUISICAO.getHeader())))
                .dataHoraRequest(Optional.ofNullable(headers.get(MessageHeaders.DATA_HORA_REQUEST.getHeader()))
                        .map(Object::toString).orElse(STRING_VAZIA))
                .ipAddress(Optional.ofNullable(headers.get(MessageHeaders.IP_ADDRESS.getHeader()))
                        .map(Object::toString).orElse(STRING_VAZIA))
                .dataHoraRequest(Optional.ofNullable(headers.get(MessageHeaders.DATA_HORA_REQUEST.getHeader()))
                        .map(Object::toString).orElse(STRING_VAZIA))
                .requestPayload(body)
                .cnpjEmitente(Optional.ofNullable(headers.get(MessageHeaders.CNPJ_EMITENTE.getHeader()))
                        .map(Object::toString).orElse(STRING_VAZIA))
                .build();
    }

    private void updateRequestData(RequestData requestData, Map<String, Object> headers, String body) {
        setIfPresent(headers, MessageHeaders.STATUS_CODE.getHeader(), requestData::setStatusCode);
        setIfPresent(headers, MessageHeaders.HAS_ERROR.getHeader(), requestData::setHasError);
        setIfPresent(headers, MessageHeaders.ERROR_MESSAGE.getHeader(), requestData::setErrorMessage);
        setIfPresent(headers, MessageHeaders.QUANTIDADE_DOCUMENTOS.getHeader(), requestData::setQuantidadeDocumentos);
        setIfPresent(headers, MessageHeaders.DATA_HORA_RESPONSE.getHeader(), requestData::setDataHoraResponse);
        setIfPresent(headers, MessageHeaders.CNPJ_EMITENTE.getHeader(), requestData::setCnpjEmitente);
        setIfPresent(body, requestData::setResponsePayload);
    }
}
