package br.com.oobj.billing.service;

import br.com.oobj.billing.exceptions.DataNotFoundException;
import br.com.oobj.billing.exceptions.IdRequisicaoNaoInformadoException;
import org.springframework.amqp.core.Message;

public interface DataService {

    String STRING_VAZIA = "";

    void processNewData(Message message);
    void processUpdateData(Message message) throws IdRequisicaoNaoInformadoException, DataNotFoundException;

}
