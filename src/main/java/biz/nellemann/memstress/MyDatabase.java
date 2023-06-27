package biz.nellemann.memstress;

import biz.nellemann.memstress.db.Database;
import biz.nellemann.memstress.db.DatabaseManager;
import biz.nellemann.memstress.db.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class MyDatabase {

    final Logger log = LoggerFactory.getLogger(MyDatabase.class);

    private final int BYTE_SIZE_1MB = 1_000_000;
    private final int BYTE_SIZE_1GB = 1_000_000_000;

    private final DatabaseManager databaseManager = new DatabaseManager();
    private final Random random = new Random();

    // Use when searching or using later?
    private final ArrayList<Table> tables = new ArrayList<Table>();

    private final int maxTables;
    private final int maxRowsPerTable;
    private final int maxDataPerRow;

    public MyDatabase(int tables, int rows, int size) {
        this.maxTables = tables;
        this.maxRowsPerTable = rows;
        this.maxDataPerRow = size;
    }


    public Database build(String dbName) {
        Instant instant1 = Instant.now();
        Database database = databaseManager.createDatabase(dbName);
        for(int t = 1; t <= maxTables; t++) {

            String tableName = String.format("table_%d", t);
            log.info("Creating table \"{}\"", tableName);

            Table table = database.createTable(tableName);

            for(int r = 1; r <= maxRowsPerTable; r++) {
                String rowIdx = String.format("%d_of_%d", r, maxRowsPerTable);
                HashMap<String, ByteBuffer> map = new HashMap<String,ByteBuffer>();
                for(int m = 1; m <= maxDataPerRow; m++) {
                    map.put(randomString(128), ByteBuffer.wrap(randomBytes(BYTE_SIZE_1MB)) );
                }
                table.insertEntry(rowIdx, map);
            }

            tables.add(table);
        }

        Instant instant2 = Instant.now();
        log.info("Done building in-memory database \"{}\" in {}", dbName, Duration.between(instant1, instant2));
        return database;
    }


    String randomString(final int length) {
        StringBuilder sb = new StringBuilder(length);
        for(int l = 0; l < length; l++) {
            sb.append( (char) random.nextInt(65_535));
        }
        return sb.toString();
    }


    byte[] randomBytes(final int length) {
        byte[] randomArray = new byte[length];
        random.nextBytes(randomArray);
        return randomArray;
    }


    void destroy(final String dbName) {
        databaseManager.deleteDatabase(dbName);
    }


}
