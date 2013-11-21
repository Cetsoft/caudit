CAUDIT
======

Caudit is a simple library to log application performance, health and statistics in an 
organized manner. It has two basic audit types: stopwatches and quantities. Stopwatches
are the ones which you keep track of the time passed for a specific operation. Quantities 
are variables that you want to monitor. Let's make it more understanding with examples.
###Simple Stopwatch Example
In this example, we try to monitor how much time it takes to run doSomeWork() method. 
```java
  //Mapping audit to integer is for performance(string comparison vs integer comparison)
  private final static int BASIC_STOPWATCH_ID = Audits.mapAudit("example.basicStopwatch");
  
  public void tryOut(){
    final Stopwatch stopwatch = Audits.getBasicStopwatch(BASIC_STOPWATCH_ID);
    stopwatch.start();
    doSomeWork();
    stopwatch.stop();
  }
```
If we use default configuration we will see an output on the console as follows.
> example.basicStopwatch : ElapsedTime[5679]

###Pom Dependency
```xml
<dependency>
  <groupId>com.cetsoft</groupId>
  <artifactId>caudit</artifactId>
  <version>0.0.7</version><!--Can be updated for later versions-->
</dependency>
```
###Simple Quantity Example
In this example, we try to monitor how many items we retrieve from database. 
```java
  private final static int NO_OF_RETRIEVED_ITEMS_ID = Audits.mapAudit("example.noOfRetrievedItems");
  
  public void tryOut(){
    final LongQuantity quantity = Audits.getLongQuantity(NO_OF_RETRIEVED_ITEMS_ID);
    int size = retrieveItems().size();
    quantity.increment(size);
  }
```
If we use default configuration we will see an output on the console as follows.
> example.noOfRetrievedItems : Quantity[2631]

###Counting Stopwatch Example
In this example, we try to monitor how much time it takes to do specific number of operations. 
```java
  private final static int COUNTING_STOPWATCH_ID = Audits.mapAudit("example.countingStopwatch");
  
  public void tryOut(){
    final CountingStopwatch stopwatch = Audits.getCountingStopwatch(COUNTING_STOPWATCH_ID);
    stopwatch.start(0);
    int size = doSomeWork();
    stopwatch.stop(size);
  }
```
If we use default configuration we will see an output on the console as follows.
> example.countingStopwatch : Count[23] ElapsedTime[5679]

AuditProvider is called for each time interval. Thus, we can see updated output on the console.
###Complex Audit Example
In this example, we try to monitor specific events that have many attributes.
```java
  private final static int TOTAL_CHANGES_ID = Audits.mapAudit("example.totalChanges");
  
  public void tryOut(){
    final ComplexAudit audit = Audits.getComplexAudit(TOTAL_CHANGES_ID);
    audit.put("width",3);
    audit.put("length",5);
    audit.put("number",11);
  }
```
If we use default configuration we will see an output on the console as follows.
> example.totalChanges : width[3] length[5] number[11]

###Default Audits
If you want to know how much memory you consume or how much threads you run, you can use default
functions of caudit as follows.
```java
  public void tryOut(){
     Audits.monitorUsedMemoryInMB();
     Audits.monitorNumberOfThreads();
  }
```
If we use default configuration we will see an output on the console as follows.
> USED_MEMORY_IN_MB : Quantity[3]

>NO_OF_THREADS : Quantity[5]

##Specific Scenarios
* In default configuration we print those audits to the console every 10 seconds. You can reset
your stopwatches or quantities for the new interval by shouldReset method.

```java
  stopwatch.shouldReset(true);
```
* Moreover, one may want to monitor what is the value of something for every interval. It can
be accomplished by giving a provider to the quantity as follows.

```java
  doubleQuantity.setProvider(new AuditProvider() {
    public void updateAudit(){
      doubleQuantity.set(Math.random());
    }
  });
```
* Sometimes, you may want to measure time for one event and remove it after measurement, this can
done by AuditRemovalListener. We can set shouldReset to false in order to avoid from resetting it.

```java
  private final static int EVENT_ID = Audits.mapAudit("example.event");
  public void startEvent(){
    Stopwatch stopwatch = Audits.getBasicStopwatch(EVENT_ID);
    stopwatch.setShouldReset(false);
    stopwatch.setObservable(false);// By setting observable to false will prevent it from being observed.
    stopwatch.setRemovalListener(new AuditRemovalListener() {
  		public void onRemoval(AuditEvent auditEvent) {
				System.out.println(auditEvent);
			}
		});
    stopwatch.start();
  }
  public void stopEvent(){
    Stopwatch stopwatch = Audits.getBasicStopwatch(EVENT_ID);
    stopwatch.stop();
    Audits.unmapAudit(stopwatch);
  }
```
###Configuration
Caudit configuration is simple, you just give period of caudit and observers for audit events.
Here is an example configuration. 
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<caudit>
  <period>1000</period>
    <observers>
      <observer class="com.cetsoft.caudit.observer.ConsoleObserver" />
      <!-- MyObserver is just an example -->
      <observer class="com.cetsoft.caudit.observer.MyObserver" />
	</observers>
</caudit>
```
Caudit tries to find caudit.xml in classpath or you can give
its path by -Dcaudit.configuration.filePath=yourpath, otherwise, it loads the default configuration.
###Licence
Caudit uses GNU Licence.
