/**
 * Example taken from: https://medium.com/@mithoonkumar/design-an-in-memory-nosql-database-ood-428d48b68dfa
 */
package biz.nellemann.jmemperf.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class DatabaseManager {
    final Logger log = LoggerFactory.getLogger(DatabaseManager.class);

    private HashMap<String, Database> databaseHashMap;

    public Database createDatabase(String databaseName) {
        if (databaseHashMap.containsKey(databaseName)) {
            log.warn("createDatabase() - A database already exists with this name: {}", databaseName);
        } else {
            databaseHashMap.put(databaseName, new Database(databaseName));
        }
        return databaseHashMap.get(databaseName);
    }

    public void deleteDatabase(String databaseName) {
        databaseHashMap.remove(databaseName);
    }


    public Database getDatabase(String databaseName) {
        if (databaseHashMap.containsKey(databaseName)) {
            return databaseHashMap.get(databaseName);
        } else {
            log.warn("getDatabase() - Database was not found: {}", databaseName);
        }
        return  null;
    }


    public DatabaseManager() {
        this.databaseHashMap = new HashMap<>();
    }

    public HashMap<String, Database> getDatabaseHashMap() {
        return databaseHashMap;
    }

    public void setDatabaseHashMap(HashMap<String, Database> databaseHashMap) {
        this.databaseHashMap = databaseHashMap;
    }

}