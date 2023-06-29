# Memory Performance Test


## Examples

Test with 8GB memory:
```shell
java -Xms10g -Xmx10g -XX:+AlwaysPreTouch -jar memstress-0.0.1-all.jar -t 8
```

Test with 100GB memory:
```shell
java -Xms128g -Xmx128g -XX:+AlwaysPreTouch -jar memstress-0.0.1-all.jar -t 100
```
