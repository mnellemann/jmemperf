# Memory Performance Test

## Examples

```shell
java -Xms128g -Xmx128g  -XX:+UseLargePages -XX:+AlwaysPreTouch \
  -XX:-UseParallelGC -XX:MaxGCPauseMillis=500 -Xgcthreads3  \
  -jar memstress-0.0.1-all.jar -t 128
```
