** Compiling and executing the sample solution client/server

1. idlj -fall -oldImplBase SampleSoln.idl
2. javac *.java SampleApp/*.java
3. tnameserv -ORBInitialPort 1050 &
4. java SampleSolnServer -ORBInitialPort 1050 -ORBInitialHost localhost &
5. java SampleSolnClient -ORBInitialPort 1050 -ORBInitialHost localhost
