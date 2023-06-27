/**
 * Example taken from: https://medium.com/@mithoonkumar/design-an-in-memory-nosql-database-ood-428d48b68dfa
 */
package biz.nellemann.memstress.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;

public class Database {

    final Logger log = LoggerFactory.getLogger(Database.class);

    private String name;
    private HashMap<String, Table> tableHashMap;
    private Date createdAt;

    public Database(String name) {
        this.name = name;
        this.tableHashMap = new HashMap<>();
        this.createdAt = new Date();
    }

    public Table createTable(String tableName) {
        if (tableHashMap.containsKey(tableName)) {
            log.warn("createTable() - A table already exists with the given name: {}", tableName);
        } else {
            Table table = new Table(tableName);
            tableHashMap.put(tableName, table);
            log.debug("createTable() - Table successfully created: {}", tableName);
        }
        return tableHashMap.get(tableName);
    }

    public void dropTable(String tableName) {
        tableHashMap.remove(tableName);
        log.debug("dropTable() - Table successfully dropped: {}", tableName);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<String, Table> getTableHashMap() {
        return tableHashMap;
    }

    public void setTableHashMap(HashMap<String, Table> tableHashMap) {
        this.tableHashMap = tableHashMap;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}