# Memory Performance Test

Small tool to test memory write and read performance.

```shell
Usage: jmemperf [-hV] [-d=NUM] [-i=NUM] [-r=NUM] [-t=NUM]
Memory performance measurement tool.
  -d, --data=NUM         Create this much data (MB) pr. row [default: 100]
  -h, --help             Show this help message and exit.
  -i, --iterations=NUM   Iterate this many times [default: 3]
  -r, --rows=NUM         Create this many rows pr. table [default: 10]
  -t, --tables=NUM       Create this many tables [default: 4]
  -V, --version          Print version information and exit.
```

## Examples

Test with 8GB memory:
```shell
java -Xms10g -Xmx10g -XX:+AlwaysPreTouch -jar memstress-0.0.1-all.jar -t 8
```

Test with 100GB memory:
```shell
java -Xms128g -Xmx128g -XX:+AlwaysPreTouch -jar memstress-0.0.1-all.jar -t 100
```

## Credits

- Jens Ebbesen for improvements.