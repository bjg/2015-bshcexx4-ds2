** Compiling and executing the CA1 solution client/server

1. idlj -fall -oldImplBase CA1NamingSoln.idl
2. javac *.java CA1NamingApp/*.java
3. tnameserv -ORBInitialPort 1050 &
4. java CA1NamingSolnServer -ORBInitialPort 1050 -ORBInitialHost localhost &
5. java CA1NamingSolnClient -ORBInitialPort 1050 -ORBInitialHost localhost
