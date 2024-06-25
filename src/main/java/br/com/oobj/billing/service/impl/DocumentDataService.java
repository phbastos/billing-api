package br.com.oobj.billing.service.impl;

import br.com.oobj.billing.domain.DocumentData;
import br.com.oobj.billing.domain.enums.MessageHeaders;
import br.com.oobj.billing.exceptions.DataNotFoundException;
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

    public void processUpdateData(Message message) throws DataNotFoundException {
        Map<String, Object> headers = message.getMessageProperties().getHeaders();

        if (headers.get(MessageHeaders.CHAVE_UNICA.getHeader()) == null) {
            throw new IllegalArgumentException("Chave unica não informada. Não é possível prosseguir sem essa informação.");
        }

        Optional<DocumentData> documentDataFromId = repository.findById(headers.get(MessageHeaders.CHAVE_UNICA.getHeader()).toString()); //TODO buscar por chave unica e nao por id

        if (documentDataFromId.isEmpty()) {
            throw new DataNotFoundException("Nao existe documento com a chave unica informada.");
        }

        DocumentData documentData = documentDataFromId.get();

        updateDocumentData(documentData, headers);
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

    private void updateDocumentData(DocumentData documentData, Map<String, Object> headers) {
        setIfPresent((String) headers.get(MessageHeaders.CSTAT.getHeader()), documentData::setCStat);
        setIfPresent((String) headers.get(MessageHeaders.X_MOTIVO.getHeader()), documentData::setXMotivo);
        setIfPresent((String) headers.get(MessageHeaders.RETORNO_ENGINES.getHeader()), documentData::setRetornoEngines);
        setIfPresent((String) headers.get(MessageHeaders.DATA_HORA_RETORNO_ENGINES.getHeader()), documentData::setDataHoraRetornoEngines);
        setIfPresent((String) headers.get(MessageHeaders.DATA_HORA_CONSULTA_SAP.getHeader()), documentData::setDataHoraConsultaSAP);
        setIfPresent((String) headers.get(MessageHeaders.DATA_HORA_CONFIRMACAO_SAP.getHeader()), documentData::setDataHoraConfirmacaoSAP);
    } //TODO adicionar status_processamento e adicionar os cálculos de duracao consulta e duracao confirmacao
}
