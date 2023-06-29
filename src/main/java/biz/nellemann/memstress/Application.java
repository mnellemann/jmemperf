package biz.nellemann.memstress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;

import java.time.Duration;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "memstress", mixinStandardHelpOptions = true, versionProvider = VersionProvider.class, description = "Memory performance measurement tool.")
public class Application implements Callable<Integer> {

    final Logger log = LoggerFactory.getLogger(Application.class);


    @CommandLine.Option(names = { "-t", "--tables" }, paramLabel = "NUM", description = "Create this many tables [default: ${DEFAULT-VALUE}]")
    int maxTables = 4;

    @CommandLine.Option(names = { "-r", "--rows" }, paramLabel = "NUM", description = "Create this many rows pr. table [default: ${DEFAULT-VALUE}]")
    int maxRowsPerTable = 10;

    @CommandLine.Option(names = { "-d", "--data" }, paramLabel = "NUM", description = "Create this much data (MB) pr. row [default: ${DEFAULT-VALUE}]")
    int maxDataPerRow = 100;

    @CommandLine.Option(names = { "-i", "--iterations" }, paramLabel = "NUM", description = "Iterate test his many times [default: ${DEFAULT-VALUE}]")
    int iterations = 3;

    @Override
    public Integer call() throws Exception {

        long writeTimeMillis = 0;
        long readTimeMillis = 0;

        for(int i = 1; i <= iterations; i++) {
            log.info("Starting test {} of {}", i, iterations);
            MemDatabase database = new MemDatabase(maxTables, maxRowsPerTable, maxDataPerRow);
            writeTimeMillis += database.write("testDb");
            readTimeMillis += database.read("testDb");
            database.destroy("testDb");
        }

        log.info("Average writing time: {}", Duration.ofMillis(writeTimeMillis / iterations));
        log.info("Average reading time: {}", Duration.ofMillis(readTimeMillis / iterations));

        return 0;
    }


    public static void main(String... args) {
        int exitCode = new CommandLine(new Application()).execute(args);
        System.exit(exitCode);
    }


}
