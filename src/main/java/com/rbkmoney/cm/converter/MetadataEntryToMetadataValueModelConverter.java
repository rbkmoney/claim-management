package com.rbkmoney.cm.converter;

import com.rbkmoney.cm.model.MetadataModel;
import com.rbkmoney.damsel.msgpack.Value;
import lombok.SneakyThrows;
import org.apache.thrift.TSerializer;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MetadataEntryToMetadataValueModelConverter
        implements ClaimConverter<Map.Entry<String, Value>, MetadataModel> {

    ThreadLocal<TSerializer> thriftSerializerThreadLocal =
            ThreadLocal.withInitial(() -> new TSerializer(new TBinaryProtocol.Factory()));

    @Override
    @SneakyThrows
    public MetadataModel convert(Map.Entry<String, Value> metadataEntry) {
        MetadataModel metadataModel = new MetadataModel();
        metadataModel.setKey(metadataEntry.getKey());
        metadataModel.setValue(thriftSerializerThreadLocal.get().serialize(metadataEntry.getValue()));
        return metadataModel;
    }
}
