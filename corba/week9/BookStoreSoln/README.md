** Compiling and executing the book store solution

1. idlj -fallTIE -oldImplBase EbookStore.idl
2. javac *.java EbookStoreApp/*.java
3. tnameserv -ORBInitialPort 1050&
4. java EbookStoreServer -ORBInitialPort 1050 -ORBInitialHost localhost &
5. java EbookStoreClient -ORBInitialPort 1050 -ORBInitialHost localhost
