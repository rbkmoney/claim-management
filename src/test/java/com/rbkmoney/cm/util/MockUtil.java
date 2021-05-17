package com.rbkmoney.cm.util;

import com.rbkmoney.geck.serializer.kit.mock.FieldHandler;
import com.rbkmoney.geck.serializer.kit.mock.MockMode;
import com.rbkmoney.geck.serializer.kit.mock.MockTBaseProcessor;
import com.rbkmoney.geck.serializer.kit.tbase.TBaseHandler;
import lombok.SneakyThrows;
import org.apache.thrift.TBase;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MockUtil {

    private static final Map.Entry<String[], FieldHandler> accountFields = Map.entry(
            new String[] {"correspondent_account"},
            structHandler -> {
                structHandler.beginStruct(3);
                structHandler.name("number");
                structHandler.value("test");
                structHandler.name("iban");
                structHandler.value("test");
                structHandler.name("account_holder");
                structHandler.value("test");
                structHandler.endStruct();
            }
    );

    private static final Map.Entry<String[], FieldHandler> timeFields = Map.entry(
            new String[] {"created_at", "updated_at", "signed_at", "valid_until"},
            structHandler -> structHandler.value(Instant.now().toString())
    );

    public static <T extends TBase> List<T> generateTBaseList(T thriftBase, int count) {
        return generateTBaseList(thriftBase, count, timeFields, accountFields);
    }

    public static <T extends TBase> List<T> generateTBaseList(Class<T> clazz, int count) {
        return generateTBaseList(clazz, count, timeFields, accountFields);
    }

    public static <T extends TBase> List<T> generateTBaseList(T thriftBase, int count,
                                                              Map.Entry<String[], FieldHandler>... fieldHandlers) {
        return Stream.generate(() ->
                generateTBase(thriftBase, fieldHandlers)).limit(count).collect(Collectors.toList());
    }

    public static <T extends TBase> List<T> generateTBaseList(Class<T> clazz, int count,
                                                              Map.Entry<String[], FieldHandler>... fieldHandlers) {
        return Stream.generate(() -> generateTBase(clazz, fieldHandlers)).limit(count).collect(Collectors.toList());
    }

    public static <T extends TBase> T generateTBase(Class<T> clazz) {
        return generateTBase(clazz, timeFields, accountFields);
    }

    @SneakyThrows
    public static <T extends TBase> T generateTBase(Class<T> clazz,
                                                    Map.Entry<String[], FieldHandler>... fieldHandlers) {
        return generateTBase(clazz.getDeclaredConstructor().newInstance(), fieldHandlers);
    }

    public static <T extends TBase> T generateTBase(T thriftBaseObject) {
        return generateTBase(thriftBaseObject, timeFields, accountFields);
    }

    @SneakyThrows
    public static <T extends TBase> T generateTBase(T thriftBaseObject,
                                                    Map.Entry<String[],
                                                            FieldHandler>... fieldHandlers) {
        return buildMockTBaseProcessor(fieldHandlers)
                .process(thriftBaseObject, new TBaseHandler<>((Class<T>) thriftBaseObject.getClass()));
    }

    public static MockTBaseProcessor buildMockTBaseProcessor(Map.Entry<String[], FieldHandler>... fieldHandlers) {
        MockTBaseProcessor mockTBaseProcessor = new MockTBaseProcessor(MockMode.RANDOM, 10, 5);
        Arrays.stream(fieldHandlers)
                .forEach(entry -> mockTBaseProcessor.addFieldHandler(entry.getValue(), entry.getKey()));
        return mockTBaseProcessor;
    }

}
