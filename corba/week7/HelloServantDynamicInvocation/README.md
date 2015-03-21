** Compiling and executing a CORBA client/server (DII example)

1. idlj -fallTIE -oldImplBase HelloWorldDynamicInvocation.idl
2. javac *.java HelloDynamicInvocation/*.java
3. tnameserv -ORBInitialPort 1050 &
4. java HelloDynamicServer -ORBInitialPort 1050 -ORBInitialHost localhost &
5. java HelloDynamicClient -ORBInitialPort 1050 -ORBInitialHost localhost