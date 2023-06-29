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
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;

public class MemDatabase {

    final Logger log = LoggerFactory.getLogger(MemDatabase.class);

    private final int BYTE_SIZE_1MB = 1_000_000;
    private final int BYTE_SIZE_1GB = 1_000_000_000;

    private final DatabaseManager databaseManager = new DatabaseManager();

    // Use when searching or using later?
    private final ArrayList<Table> tables = new ArrayList<Table>();

    private final int maxTables;
    private final int maxRowsPerTable;
    private final int maxDataPerRow;

    private int idx = 0;
    private int idx2 = 0;
    private char[] baseCar;
    private byte[] byteBase;

    public MemDatabase(int tables, int rows, int size) {
        this.maxTables = tables;
        this.maxRowsPerTable = rows;
        this.maxDataPerRow = size;
        baseCar = new char[128];
        byteBase = new byte[BYTE_SIZE_1MB];
        for (int i = 0; i < 128; i++) {
            baseCar[i] = 'A';
        }
        Arrays.fill(byteBase, (byte) 0);
    }


    public long write(String dbName) {
        Instant instant1 = Instant.now();
        Database database = databaseManager.createDatabase(dbName);

        AtomicLong bytesWritten = new AtomicLong();
        for (int t = 1; t <= maxTables; t++) {

            String tableName = String.format("table_%d", t);
            log.debug("Creating table \"{}\"", tableName);

            Table table = database.createTable(tableName);

            for (int r = 1; r <= maxRowsPerTable; r++) {
                String rowIdx = String.format("%d_of_%d", r, maxRowsPerTable);
                HashMap<String, ByteBuffer> map = new HashMap<String, ByteBuffer>();
                for (int m = 1; m <= maxDataPerRow; m++) {
                    map.put(randomString(), randomBytes());
                    bytesWritten.addAndGet(byteBase.length);
                }
                table.insertEntry(rowIdx, map);
            }

            tables.add(table);
        }

        Instant instant2 = Instant.now();
        Duration duration = Duration.between(instant1, instant2);
        log.info("Done writing {} bytes -> \"{}\" in {}", bytesWritten, dbName, duration);

        return duration.toMillis();
    }


    public long read(String dbName) {
        Instant instant1 = Instant.now();
        Database database = databaseManager.getDatabase(dbName);

        AtomicLong bytesRead = new AtomicLong();
        for(Table table : tables) {
            table.getRows().forEach((idx, row) -> {
                HashMap<String, ByteBuffer> values = row.getColumnValuesMap();
                values.forEach((str, byteBuffer) -> {
                    byteBuffer.rewind();
                    while (byteBuffer.hasRemaining()) {
                        byte[] tmp = new byte[BYTE_SIZE_1MB];
                        byteBuffer.get(tmp);
                        bytesRead.addAndGet(tmp.length);
                    }
                });
            });
        }
        Instant instant2 = Instant.now();
        Duration duration = Duration.between(instant1, instant2);
        log.info("Done reading {} bytes <- \"{}\" in {}", bytesRead.get(), dbName, duration);

        return duration.toMillis();
    }



    String randomString() {
        baseCar[(idx++) % 128]++;
        return new String(baseCar);
    }

    ByteBuffer randomBytes() {
        byteBase[(idx2++) % byteBase.length]++;
        byte[] bytes = new byte[byteBase.length];
        System.arraycopy(byteBase, 0, bytes, 0, bytes.length);
        return ByteBuffer.wrap(bytes);
    }

    void destroy(final String dbName) {
        databaseManager.deleteDatabase(dbName);
    }


}
