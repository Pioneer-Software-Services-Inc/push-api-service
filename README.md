# PushAPI
This is a web service (json) API that provides various web services that communicate between the Web Application, via an Oracle SQL database, and the Push REST end-points. It performs various tasks which includes push service calls to Push which eventually results in messages being sent to Push enabled mobile devices.

# Technology Stack
The technology stack includes Apache Maven, Java 1.8, Stream API, Method references, Functions, Functional Interfaces, Lambda expressions and other more recent tools.

Performance was a key concern in terms of throughput and resource (cpu|memory) management. 
As a result the following were carefully considered: 

i) Lazy instantiation of objects paradigm of was used extensively. <br />
ii) Special care was made to ensure that looping and other expensive operations were limited to a maximum O(log n) performance.<br />
iii) Stream API was used to perform bulk operations on various Collections objects.  <br />
iv) Http request/response was done asynchronously along with multi-threading for processing multiple data inputs in parallel.<br />
v) Memory efficient cache strategies such as the use of java.lang.ref.SoftReference and WeakHashMap (Collection) classes were implemented.<br />

Apache Maven was used to streamline and manage jar file versions as well as automate the various JUnit tests that were integrated into the compile and packaging process. Security being an implicit and obvious design time concern was built in from day one. Various OWASP suggested code features were implemented. All communication was done over Transport Layer Security (TLS).

Various software architectural design patterns were used. These include Command, Data Transfer Objects, Abstract Factory, Service Locator, Singleton, Façade & Null Objects.

Exception tunneling (Lambda Expressions) was used to restore the conciseness of code when the lambda expression throws an exception.

Stream API was used preferably in place of looping control structures.

The try-catch with resource feature (Java 1.8) was used extensively to streamline exception handling when there is a i/o operation being performed.

The generalized target-type inference feature of the Java programmig language was used extensively to simplify passing various types of parameter objects as well as reduce the effect of generic types handling complexities.

Special attention was made to ensure the proper documentation of key functionalities, as well as the code being javadoc compliant.  

# Design Documentation
Please see below for more detailed documentation: 

   Design Specifications: http://bit.ly/2yNypLQ <br />
   Data Requirements Specifications: http://bit.ly/2liV0KF <br />