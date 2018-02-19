# Instruction to run

# Download

Download sources
```bash
git clone git@github.com:andrekosintsev/macOS-topexporter.git
```

then cd to your project, please keep in mind that this project works on mac os machines, I have not checked it on Linux machines (in my case `macOs High Sierra Version: 10.13.3`)

for linux to read command line, you reader should be able to read to the following result:

```bash
top - 09:47:57 up 7 days, 11:11,  0 users,  load average: 5.56, 6.81, 7.25
Tasks:   3 total,   1 running,   2 sleeping,   0 stopped,   0 zombie
%Cpu(s): 40.5 us,  5.5 sy,  0.8 ni, 52.2 id,  0.8 wa,  0.0 hi,  0.3 si,  0.0 st
KiB Mem:  53596596 total, 48820388 used,  4776208 free,  7924292 buffers
KiB Swap:        0 total,        0 used,        0 free. 15533448 cached Mem
......
```

Exporter now works only with such output result:

```bash
Processes: 111 total, 111 running, 111 sleeping, 111 threads                                                                                                                                                                          10:49:38
Load Avg: 2.10, 2.26, 2.31  CPU usage: 3.36% user, 3.36% sys, 3.26% idle  SharedLibs: 267M resident, 66M data, 47M linkedit. MemRegions: 100 total, 5235M resident, 141M private, 1895M shared.
PhysMem: 13G used (2449M wired), 2639M unused. VM: 2747G vsize, 1097M framework vsize, 7283171(0) swapins, 8295696(0) swapouts. Networks: packets: 12061349/6848M in, 10622315/1566M out. Disks: 204/61G read, 100/69G written.
........
```

If you want to run it on linux machine please change implementation for 

```java
io.youngkoss.prometheus.exporter.TopExecutorColl
```
Yep, I know that the best way was to check on which client you run this application and provide `adapter` pattern for example with different implementation and to create maven profile for this, but use this as you want, I have not implemented it for the different kind of clients.


# Install

Please use
```bash
mvn clean install
```
There is no tests added

then go to target folder 

```bash
cd target && java -jar app.jar
```
or I hope it should work also if you will use these commands:

```bash
cd target && mvn spring-boot:run
```

# Install

It will expose port `12228` by default, but you can specify your own in `application.properties` file before build.

then in browser you can check it with refresh like `localhost:12228/topstatistics`

you can point your prometheus to scrap metrics and provide them in grafana.

### Good luck
