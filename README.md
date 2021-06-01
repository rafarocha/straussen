# straussen
comparison of the straussen algorithm, on matrix multiplication, with the naive, for large matrices

### how to run
- `java -jar <arquivo.jar> <algorithm> <sample>`
- `java -jar straussen.jar ` default straussen impl + file sample1 inside JAR
- `java -jar straussen.jar straussen ~/sample1` complete filename with absolute path

```shell
# java -jar straussen.jar naive /Volumes/work/msc/ufpb/projects/straussen/src/app/sample1
file: /Volumes/work/msc/ufpb/projects/straussen/src/app/sample1
k_max 10 : r 1 : interval 150 250

starting ... 


kmax 2^05 : time 8
kmax 2^06 : time 17
kmax 2^07 : time 65
kmax 2^08 : time 421
kmax 2^09 : time 4646
kmax 2^10 : time 30763
```

```shell
java -jar straussen.jar
inside JAR file: sample1
k_max 10 : r 1 : interval 150 250

starting ... 


kmax 2^05 : time 8
kmax 2^06 : time 18
kmax 2^07 : time 14
kmax 2^08 : time 42
kmax 2^09 : time 214
kmax 2^10 : time 2922
```
