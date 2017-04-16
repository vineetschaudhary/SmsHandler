# Synopsis
This application code allow customers to perform simple operations on their account using their mobile phones. The technical sending and receiving of the SMS itself will be done by another team. Your task
is to handle the incoming and outgoing strings and perform all necessary operations. Some request examples are given above.

| SMS Command        | Example           | Response Notes  |
| ------------- |-------------| -----|
| BALANCE      |1500 |Returns your balance in EUR |
| SEND-100-FFRITZ       |OK     |Send 100 EUR to user with username FFRITZ |
| SEND-100-FFRITZ | ERR – INSUFFICIENT FUNDS|User hasn’t got enough money to make the transfer |
| SEND-100-FFRITZ  | ERR – NO USER  | System can’t find the user with username FFRITZ|
| TOTAL-SENT-FFRITZ  | 560  |  Get the total amount sent so far to user FFRITZ |
| TOTAL-SENT-FFRITZ-MSMITH   |  560,250 | Get the total amounts sent to users FFRITZ and MSMITH as a comma separated list.  |
| TOTAL-SENT-FFRITZ-MSMITH  |  ERR – NO USER | Either the system cannot find the user FFRITZ or the system cannot find MSMITH  |
|  XYZ  | ERR – UNKNOWN COMMAND  | The command was not recognized.   |

# Code
Currently, it can be testedusing junit which are already in the code.

Technology stack used: 
1. Spring Boot.
2. Java 8
3. Maven

Entry point class is SMSHandlerImpl which implements SMSHandler Interface. This handler will then validate and filter the request using CommandUtil.getCommand method.
It will then return the actual command name to be sent to CommandBuilder to et the command instance.
For example:  SEND-100-FFRITZ command will be fitered to the SEND command using CommandUtil.getcommand method and will return SendCommand instance
to process this command.

All Command classes needs to implement SMSCommand interface and add corrsponding pattern to the CommandUtil class.

Each command class will return the ResponseStatus based on the logic execution.

# Installation and Running Application 
You should have java 8 installed on your system.

Code can be imported to any IDE and you can use Maven to build and package the project. You can also use command prompt and use maven to build and package project.

Currently, we do not have the implementation classes for UserManager and TransferManager so it may not be tested other then JUnit testcases hwich are already included in the code.

We can run the test cases using MVN test command and it needs to be run on the folder where pom.xml exist.

# Tests

Unit tests are under test folder which all the above command.


