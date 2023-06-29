/*
   Copyright 2023 mark.nellemann@gmail.com

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package biz.nellemann.jmemperf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;

import java.time.Duration;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "jmemperf", mixinStandardHelpOptions = true, versionProvider = VersionProvider.class, description = "Memory performance measurement tool.")
public class Application implements Callable<Integer> {

    final Logger log = LoggerFactory.getLogger(Application.class);


    @CommandLine.Option(names = { "-t", "--tables" }, paramLabel = "NUM", description = "Create this many tables [default: ${DEFAULT-VALUE}]")
    int maxTables = 4;

    @CommandLine.Option(names = { "-r", "--rows" }, paramLabel = "NUM", description = "Create this many rows pr. table [default: ${DEFAULT-VALUE}]")
    int maxRowsPerTable = 10;

    @CommandLine.Option(names = { "-d", "--data" }, paramLabel = "NUM", description = "Create this much data (MB) pr. row [default: ${DEFAULT-VALUE}]")
    int maxDataPerRow = 100;

    @CommandLine.Option(names = { "-i", "--iterations" }, paramLabel = "NUM", description = "Iterate this many times [default: ${DEFAULT-VALUE}]")
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
