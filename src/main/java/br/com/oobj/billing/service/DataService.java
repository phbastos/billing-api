package br.com.oobj.billing.service;

import br.com.oobj.billing.exceptions.DataNotFoundException;
import br.com.oobj.billing.exceptions.IdRequisicaoNaoInformadoException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.Message;

import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

public interface DataService {

    String STRING_VAZIA = "";

    void processNewData(Message message);
    void processUpdateData(Message message) throws IdRequisicaoNaoInformadoException, DataNotFoundException;

    default <T> void setIfPresent(Map<String, Object> map, String key, Consumer<T> setter) {
        Optional.ofNullable(map.get(key))
                .ifPresent(value -> setter.accept((T) value));
    }

    default <T> void setIfPresent(String content, Consumer<T> setter) {
        if (StringUtils.isNotBlank(content)) {
            setter.accept((T) content);
        }
    }

}
