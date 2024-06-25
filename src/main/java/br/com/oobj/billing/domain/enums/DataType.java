package br.com.oobj.billing.domain.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum DataType {

    REQUEST(0),
    DOCUMENTO(1);

    private final int codigo;

    public static DataType fromCodigo(int codigo) {
        for (DataType dataType : DataType.values()) {
            if (dataType.codigo == codigo) {
                return dataType;
            }
        }
        return null;
    }

}
