/**
 * Example taken from: https://medium.com/@mithoonkumar/design-an-in-memory-nosql-database-ood-428d48b68dfa
 */
package biz.nellemann.jmemperf.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.util.Date;
import java.util.HashMap;

public class Table {

    final Logger log = LoggerFactory.getLogger(Table.class);

    private String tableName;
    private HashMap<String, Row> rows;
    private Date createdAt;

    public Table(String tableName) {
        this.tableName = tableName;
        this.rows = new HashMap<>();
        this.createdAt = new Date();
    }

    public void insertEntry(String rowId, HashMap<String, ByteBuffer> columnsMap) {
        if (rows.containsKey(rowId)) {
            log.warn("insertEntry() - Insertion failed, duplicate primary key: {}", rowId);
        } else {
            Row row =  new Row(rowId, columnsMap, new Date(), new Date());
            rows.put(rowId, row);
            log.debug("insertEntry() - Row successfully added: {}", rowId);
        }
    }

    public void updateEntry(String rowId, HashMap<String, ByteBuffer>valuesMap) {
        Row row = rows.get(rowId);
        valuesMap.forEach( (k, v) -> {
            row.getColumnValuesMap().put(k, v);
        });
        log.debug("updateEntry() - Row successfully updated: {}", rowId);
        row.setUpdatedAt(new Date());
    }

    public void deleteEntry(String rowId) {
        log.debug("updateEntry() - Row successfully deleted: {}", rowId);
        rows.remove(rowId);
    }

    public HashMap<String, ByteBuffer> readEntry(String rowId) {
        return rows.get(rowId).getColumnValuesMap();
    }


    public HashMap<String, Row> getRows() {
        return rows;
    }

    public void setRows(HashMap<String, Row> rows) {
        this.rows = rows;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}