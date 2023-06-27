package biz.nellemann.memstress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;

import java.util.Scanner;
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


    @Override
    public Integer call() throws Exception {

        MyDatabase database = new MyDatabase(maxTables, maxRowsPerTable, maxDataPerRow);
        database.build("testDb");

        System.out.println("TODO: How to search / read from data stored in rows?");

        Scanner scanner = new Scanner(System.in);
        System.out.println("Press ENTER to stop");
        String s= scanner.nextLine();
        scanner.close();

        database.destroy("testDb");

        return 0;
    }


    public static void main(String... args) {
        int exitCode = new CommandLine(new Application()).execute(args);
        System.exit(exitCode);
    }


}
