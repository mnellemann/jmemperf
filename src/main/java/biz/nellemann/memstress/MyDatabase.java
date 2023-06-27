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

    private int idx = 0;
    private int idx2 = 0;
    private char[] baseCar;
    private byte[] byteBase;

    public MyDatabase(int tables, int rows, int size) {
        this.maxTables = tables;
        this.maxRowsPerTable = rows;
        this.maxDataPerRow = size;
        baseCar = new char[128];
        byteBase = new byte[BYTE_SIZE_1MB];
        for (int i = 0; i < 128; i++) {
            baseCar[i] = 'A';
        }
        for (int i = 0; i < byteBase.length; i++) {
            byteBase[i] = 0;
        }
    }


    public Database build(String dbName) {
        Instant instant1 = Instant.now();
        Database database = databaseManager.createDatabase(dbName);
        for (int t = 1; t <= maxTables; t++) {

            String tableName = String.format("table_%d", t);
            log.info("Creating table \"{}\"", tableName);

            Table table = database.createTable(tableName);

            for (int r = 1; r <= maxRowsPerTable; r++) {
                String rowIdx = String.format("%d_of_%d", r, maxRowsPerTable);
                HashMap<String, ByteBuffer> map = new HashMap<String, ByteBuffer>();
                for (int m = 1; m <= maxDataPerRow; m++) {
                    map.put(randomString(), randomBytes());
                }
                table.insertEntry(rowIdx, map);
            }

            tables.add(table);
        }

        Instant instant2 = Instant.now();
        log.info("Done building in-memory database \"{}\" in {}", dbName, Duration.between(instant1, instant2));
        return database;
    }

    String randomString() {
        baseCar[(idx++) % 128]++;
        String s = new String(baseCar);
        return s;
    }

    ByteBuffer randomBytes() {
        byteBase[(idx2++) % byteBase.length]++;
        byte[] bytes = new byte[byteBase.length];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = byteBase[i];
        }
        return ByteBuffer.wrap(bytes);
    }

    void destroy(final String dbName) {
        databaseManager.deleteDatabase(dbName);
    }


}
