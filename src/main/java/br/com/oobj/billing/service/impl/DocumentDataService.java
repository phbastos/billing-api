package br.com.oobj.billing.service.impl;

import br.com.oobj.billing.domain.DocumentData;
import br.com.oobj.billing.domain.enums.MessageHeaders;
import br.com.oobj.billing.repository.DocumentoDataRepository;
import br.com.oobj.billing.service.DataService;
import lombok.AllArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@Service
public class DocumentDataService implements DataService {

    private final DocumentoDataRepository repository;

    public void processNewData(Message message) {
        Map<String, Object> headers = message.getMessageProperties().getHeaders();
        DocumentData documentData = createDocumentData(headers);
        repository.save(documentData);
    }

    public void processUpdateData(Message message) {
        Map<String, Object> headers = message.getMessageProperties().getHeaders();

        if (headers.get(MessageHeaders.CHAVE_UNICA.getHeader()) == null) {
            throw new IllegalArgumentException("Chave unica não informada. Não é possível prosseguir sem essa informação.");
        }

        repository.findBy() //TODO criar a query para encontrar o documento por chave unica
    }

    private DocumentData createDocumentData(Map<String, Object> headers) {
        return DocumentData.builder()
                        .idRequest(Optional.ofNullable(headers.get(MessageHeaders.DATA_HORA_REQUEST.getHeader()))
                                .map(Object::toString)
                                .orElse(STRING_VAZIA))
                .idRequest(Optional.ofNullable(headers.get(MessageHeaders.ID_REQUISICAO.getHeader()))
                        .map(Object::toString)
                        .orElse(STRING_VAZIA))
                .chaveUnica(Optional.ofNullable(headers.get(MessageHeaders.CHAVE_UNICA.getHeader()))
                        .map(Object::toString)
                        .orElse(STRING_VAZIA))
                .hasError(Optional.ofNullable(headers.get(MessageHeaders.HAS_ERROR.getHeader()))
                        .map(value -> {
                            return Boolean.getBoolean(value.toString());
                        })
                        .orElse(Boolean.FALSE))
                .errorMessage(Optional.ofNullable(headers.get(MessageHeaders.ERROR_MESSAGE.getHeader()))
                        .map(Object::toString)
                        .orElse(STRING_VAZIA))
                .dataHoraEnvioEngines(Optional.ofNullable(headers.get(MessageHeaders.DATA_HORA_ENVIO_ENGINES.getHeader()))
                        .map(Object::toString)
                        .orElse(STRING_VAZIA))
                .build();
    }

}
