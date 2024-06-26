package br.com.oobj.billing.service;

import br.com.oobj.billing.domain.enums.DataType;
import br.com.oobj.billing.exceptions.DataNotFoundException;
import br.com.oobj.billing.exceptions.DataTypeNaoInformadoException;
import br.com.oobj.billing.exceptions.IdRequisicaoNaoInformadoException;
import br.com.oobj.billing.service.impl.DocumentDataService;
import br.com.oobj.billing.service.impl.RequestDataService;
import lombok.AllArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Service;

import static br.com.oobj.billing.domain.enums.MessageHeaders.DATA_TYPE;

@AllArgsConstructor
@Service
public class BillingService {

    private final RequestDataService requestDataService;
    private final DocumentDataService documentDataService;

    public void newRecepcaoMessage(Message message) throws DataTypeNaoInformadoException {
        Object dataTypeHeader = message.getMessageProperties().getHeaders().get(DATA_TYPE.getHeader());
        if (dataTypeHeader == null) {
            throw new DataTypeNaoInformadoException("Tipo de dado não informado. Nao consigo prosseguir sem essa informacao");
        }

        DataType dataType = DataType.fromCodigo(((Long) dataTypeHeader).intValue());

        switch (dataType) {
            case REQUEST -> requestDataService.processNewData(message);
            case DOCUMENTO -> documentDataService.processNewData(message);
            case null, default -> throw new IllegalArgumentException("O data_type informado no header da mensagem nao foi " +
                    "reconhecido. Data type: " + dataType);
        }
    }

    public void newUpdateMessage(Message message) throws DataTypeNaoInformadoException, IdRequisicaoNaoInformadoException, DataNotFoundException {
        Object dataTypeHeader = message.getMessageProperties().getHeaders().get(DATA_TYPE.getHeader());
        if (dataTypeHeader == null) {
            throw new DataTypeNaoInformadoException("Tipo de dado não informado. Nao consigo prosseguir sem essa informacao");
        }

        DataType dataType = DataType.fromCodigo(((Long) dataTypeHeader).intValue());

        switch (dataType) {
            case REQUEST -> requestDataService.processUpdateData(message);
            case DOCUMENTO -> documentDataService.processUpdateData(message);
            default -> throw new IllegalArgumentException("O data_type informado no header da mensagem nao foi " +
                    "reconhecido. Data type: " + dataType);
        }
        System.out.println("Update message received");
    }

}
