** Compiling and executing a CORBA client/server (TIE-sytle with ANY type)

1. idlj -fallTIE -oldImplBase HelloWorldTie.idl
2. javac *.java HelloTie/*.java
3. start orbd -ORBInitialPort 1050
4. start java HelloTieServer -ORBInitialPort 1050 -ORBInitialHost localhost 
5. java HelloTieClient -ORBInitialPort 1050 -ORBInitialHost localhost
