package com.cvi.config.datasource;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Slf4j
public class ReplicationRoutingDataSource extends AbstractRoutingDataSource {

    private static final String DATASOURCE_KEY_MASTER = "master";
    private static final String DATASOURCE_KEY_SLAVE = "slave";

    private DataSourceNames<String> slaveNames;

    @Override
    public void setTargetDataSources(Map<Object, Object> targetDataSources) {
        super.setTargetDataSources(targetDataSources);

        List<String> replicas = targetDataSources.keySet()
            .stream()
            .map(Object::toString)
            .filter(string -> string.contains(DATASOURCE_KEY_SLAVE))
            .collect(toList());

        this.slaveNames = new DataSourceNames<>(replicas);
    }

    // 요청에서 사용할 DataSource Key 값 반환
    @Override
    protected Object determineCurrentLookupKey() {
        boolean isReadOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly();

        if (isReadOnly) {
            final String nextSlaveName = slaveNames.getNext();
            log.info("Connection Slave : {}", nextSlaveName);
            return nextSlaveName;
        }

        log.info("Connection Master");
        return DATASOURCE_KEY_MASTER;
    }

    public static class DataSourceNames<T> {

        private final List<T> values;
        private int index = 0;

        public DataSourceNames(List<T> values) {
            this.values = values;
        }

        public T getNext() {
            if (index + 1 >= values.size()) {
                index = -1;
            }
            return values.get(++index);
        }
    }
}
