** Compiling and executing a CORBA client/server (Callback example)

1. idlj -fall HelloWorldName.idl
1. idlj -fallTIE HelloWorldName.idl
2. javac *.java HelloNaming/*.java
3. tnameserv -ORBInitialPort 1050 &
4. java HelloNamingServer -ORBInitialPort 1050 -ORBInitialHost localhost &
5. java HelloNamingClient -ORBInitialPort 1050 -ORBInitialHost localhost
