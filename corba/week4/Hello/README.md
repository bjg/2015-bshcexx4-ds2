** Compiling and executing a CORBA client/server

1. idlj -fall -oldImplBase Hello.idl
2. javac *.java HelloApp/*.java
3. start orbd -ORBInitialPort 1050
4. start java HelloServer -ORBInitialPort 1050 -ORBInitialHost localhost 
5. java HelloClient -ORBInitialPort 1050 -ORBInitialHost localhost
