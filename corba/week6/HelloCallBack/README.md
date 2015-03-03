** Compiling and executing a CORBA client/server (Callback example)

1. idlj -fallTIE -oldImplBase HelloCallBack.idl
2. javac *.java HelloCallBack/*.java
3. orbd -ORBInitialPort 1050 &
4. java HelloCBServer -ORBInitialPort 1050 -ORBInitialHost localhost &
5. java HelloCBClient -ORBInitialPort 1050 -ORBInitialHost localhost
