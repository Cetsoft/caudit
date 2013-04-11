CAUDIT
======

Caudit is a simple library to log application performance, health and statistics in an 
organized manner. It has two basic audit types: stopwatches and quantities. Stopwatches
are the ones which you keep track of the time passed for a specific operation. Quantities 
are variables that you want to monitor. Let's make it more understanding with examples.
###Simple Stopwatch Example
In this example, we try to monitor how much time it takes to run doSomeWork() method. 
```java
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

##Specific Scenarios
In default configuration we print those audits to the console every 10 seconds. You can reset
your stopwatches or quantities for the new interval by shouldReset method.
```java
  stopwatch.shouldReset(true);
```
Moreover, one may want to monitor what is the value of something for every interval. It can
be accomplished by giving a provider to the quantity as follows.
```java
  doubleQuantity.setProvider(new AuditProvider() {
    public void updateAudit() {
		  doubleQuantity.set(Math.random());
		}
	});
```
