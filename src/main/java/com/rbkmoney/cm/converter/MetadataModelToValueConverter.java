package com.rbkmoney.cm.converter;

import com.rbkmoney.cm.model.MetadataModel;
import com.rbkmoney.damsel.msgpack.Value;
import lombok.SneakyThrows;
import org.apache.thrift.TDeserializer;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.springframework.stereotype.Component;

@Component
public class MetadataModelToValueConverter implements ClaimConverter<MetadataModel, Value> {

    ThreadLocal<TDeserializer> tDeserializerThreadLocal = ThreadLocal.withInitial(() -> new TDeserializer(new TBinaryProtocol.Factory()));

    @Override
    @SneakyThrows
    public Value convert(MetadataModel metadataModel) {
        Value value = new Value();
        tDeserializerThreadLocal.get().deserialize(value, metadataModel.getValue());
        return value;
    }
}
