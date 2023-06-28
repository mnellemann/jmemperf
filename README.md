# Memory Performance Test


## Examples

```shell
java -Xms128g -Xmx128g  -XX:+UseLargePages -XX:+AlwaysPreTouch \
  -XX:-UseParallelGC -XX:MaxGCPauseMillis=500 -Xgcthreads3  \
  -jar memstress-0.0.1-all.jar -t 96
```


```shell
java -Xms144g -Xmx144g  -XX:+UseLargePages -XX:+AlwaysPreTouch \
  -XX:-UseParallelGC -XX:MaxGCPauseMillis=500 -Xgcthreads3  \
  -jar memstress-0.0.1-all.jar -t 128
```

```shell
java -Xms160g -Xmx160g  -XX:+UseLargePages -XX:+AlwaysPreTouch \
  -XX:-UseParallelGC -XX:MaxGCPauseMillis=500 -Xgcthreads3  \
  -jar memstress-0.0.1-all.jar -t 144
```

```shell
java -Xms192g -Xmx192g  -XX:+UseLargePages -XX:+AlwaysPreTouch \
  -XX:-UseParallelGC -XX:MaxGCPauseMillis=500 -Xgcthreads3  \
  -jar memstress-0.0.1-all.jar -t 160
```

```shell
java -Xms240g -Xmx240g  -XX:+UseLargePages -XX:+AlwaysPreTouch \
  -XX:-UseParallelGC -XX:MaxGCPauseMillis=500 -Xgcthreads3  \
  -jar memstress-0.0.1-all.jar -t 192
```

```shell
java -Xms256g -Xmx256g  -XX:+UseLargePages -XX:+AlwaysPreTouch \
  -XX:-UseParallelGC -XX:MaxGCPauseMillis=500 -Xgcthreads3  \
  -jar memstress-0.0.1-all.jar -t 240
```
