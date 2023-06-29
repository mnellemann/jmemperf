/**
 * Example taken from: https://medium.com/@mithoonkumar/design-an-in-memory-nosql-database-ood-428d48b68dfa
 */
package biz.nellemann.jmemperf.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.util.Date;
import java.util.HashMap;

public class Row {

    final Logger log = LoggerFactory.getLogger(Row.class);

    private String rowId;
    private HashMap<String, ByteBuffer> columnValuesMap;
    private Date createdAt;
    private Date UpdatedAt;

    public Row(String rowId, HashMap<String, ByteBuffer> columnsMap, Date createdAt, Date updatedAt) {
        this.rowId = rowId;
        this.columnValuesMap = columnsMap;
        this.createdAt = createdAt;
        UpdatedAt = updatedAt;
    }

    public HashMap<String, ByteBuffer> getColumnValuesMap() {
        return columnValuesMap;
    }

    public void setColumnValuesMap(HashMap<String, ByteBuffer> columnValuesMap) {
        this.columnValuesMap = columnValuesMap;
    }

    public String getRowId() {
        return rowId;
    }

    public void setRowId(String rowId) {
        this.rowId = rowId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return UpdatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        UpdatedAt = updatedAt;
    }
}