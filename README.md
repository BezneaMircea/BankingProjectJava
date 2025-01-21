## <span style="color: darkcyan;">Author</span>

- **Name:** [Mircea-Andrei Beznea](https://www.instagram.com/mircea.wpp/)
- **GitHub Profile:** [@BezneaMircea](https://github.com/BezneaMircea)
- **Email:** [bezneamirceaandrei21@gmail.com]()
- **Date:** [December, 19, 2024]()

# <span style="color: darkcyan;">BankingProject</span>



## <span style="color: darkgreen;">Introduction:</span>

This project is implements the backend logic for a banking application  
with various operations (e.g. addAccount, payOnline, sendMoney) using the  
Command, Factory and Builder design patterns.

# <span style="color: darkgreen;">Packaging:</span>
## 1. The _**bank**_ package
>This is the most importante package for the application logic.  
> It includes **SIX** other _**packages**_:  
>>#### 1. _*accounts*_ containing:
>>> - _**Account**_ (abstract class) extended by:
>>>  - _**Specific accounts**_ (e.g. EconomyAccount)
>>> - _**account_factory**_ containing:  
>>>  - _**AccountFactory**_ (Interface) implemented by:
>>>    - _**SpecificFactories**_ (e.g EconomyAccountFactory)
>>>
>>#### 2. _*cards*_ containing:
>>> - _**Card**_ (abstract class) extended by:
  >>>  - _**OneTimeCard**_
  >>>  - _**StandardCard**_
>>> - _**card_factory**_ containing:
>>>  - _**CardFactory**_ (Interface) implemented by:
>>>    - _**SpecificFactories**_ (e.g. OneTimeCardFactory)
>>> - _**CardInput**_ 
>>#### 3. _*commands*_ containing:
>>> - _**Command**_ (Interface) implemented by:
>>>   - All specific commands (e.g. AddInterest)
>>> - _**Transactionable**_ (Interface) implemented by:
>>>  - Some commands, those who generate transactions (e.g. AddAccount)
>>> - _**command_factory**_ containing:
>>>  - _**CommandFactory**_ (Interface) implemented by:
>>>    - _**SpecificFactories**_ (e.g. AddAccountFactory)
>>#### 4. _*commerciants*_ containing:
>>> - _**Commerciant**_ abstract class
>>>  - _**Specific Commerciants**_ (e.g. TechCommerciant)
>>> - _**commerciant_factory**_ package
>>>  - factory package for commerciants
>>> - _**commerciant_strategies**_
>>>  - _**specific cashback strategies**_ (e.g. NrOfTransactions)
>>#### 5. _*transactions*_ containing:
>>> - _**Transaction**_ (abstract class) extended by: 
>>>  - _**Specific transactions**_ (e.g. AddAccountTransaction, CheckCardStatusTransaction etc.)
>>> - _**transaction_factory containing**_:
>>>  - _**TransactionFactory**_ (Interface) implemented by:
>>>    - _**SpecificFactories**_ (e.g. AddAccountTransactionFactory)
>>#### 6. _*users*_ containing:
>>> - _**User**_ (abstract class) extended by:
>>>  - _**BasicUser**_
>>> - _**users_factory**_ containing:
>>>  - _**UserFactory**_ (Interface) implemented by:
>>>    - _**SpecificFactories**_ (e.g. BasicUserFactory)  
>>> - _**users_stategy**__ containing:
>>>  - specific stategies for users (e.g. Gold)
>>>  - a factory for creating a strategy.
> - #### And the following **two** classes:
>> 1. _**Bank**_ - represents the bank with operations like:
>> -  createCard(cardInput card) -- creates a card  
>> - createAccount(accountInput account) -- creates an account  
>> - etc.  
>> 2. _**BankSettup**_ -- represents the invoker that calls/creates commands
>>                   that are received by input. It has methods like:
>> - executeCommands() - executes the commands
>> - createBank() - creates a bank


## 2. The _*utils*_ package
> This package includes two classes:
> - _**Utils**_ that has some utils static methods and constants.  
>  - generateIban()
>  - generateCardNr()
>  - MAPPER (an object mapper)
>  - MAIN_CURRENCY (the main currency)
> - _**CustomFloydWarshallPaths**_ that implements the Floyd Warshall algorithm.  
> The length of the shortest path will be the product of the edges, not the sum
>  - getRate(final V source, final V dest) used to find the distance  
>  (rate from source to dest)

## 3. The _*main*_ package
> This package includes two classes:
> - Main - starting point for the application
> - Test - class used for testing


## <span style="color: darkgreen;">Code Flow:</span>
Starting from the main package, in the main class, we create an instance of the  
BankSetup class and call the BankSetup.executeCommands() method  
that creates a Bank, and for each commandInput creates the specific command using   
FactoryPattern that is then to be called execute() onto. (CommandPattern, where   
Bank is the receiver and BankSetup is the invoker). Some command also need to  
generate some transactions. Those commands will also implement the Transactionable  
interface that contains the "addTransaction" method.

Users will have their own plan types represented as Strategies (Strategy Pattern)  
Commerciants will have their own cashback plans represented as Strategies oance again  


We will have commands for every command that we want to perform:  
- AddAccount
- DeleteAccount
- CreateCard
- DeleteCard
- Report etc.




## <span style="color: darkgreen;">How to add new functionality:</span>
Users, Accounts, Cards, Commands, Transactions are created using the  
FactoryPattern. If extra functionalities (e.g. other accounts, cards, commands etc.)  
need to be added simply create a specificFactory of the wanted object and the  
object class. This approach makes the code more readable and most importantly  
it separates the creation of an object to the object itself  
(factories are created using (CommandInput, TransactionInput etc.)).  
For very simple objects (like Strategies that have no attributes)  
it is bettern to have only one factory file for not overloading the  
code with too unuseful files


_**Cards, Accounts, Transactions**_ are created in _**Bank**_   
_**Commands and Users**_ are created in _**BankSetup**_


## <span style="color: darkgreen;">Used Patterns:</span>
- Factory
- Builder
- Visitor
- Strategy
- Command

## <span style="color: darkgreen;">Project Feedback:</span>

This was an interesting project that helped me learn a lot about design patterns.  
I really would recommend to anyone that wants to have a solid knowledge about  
design pattern concepts take on the assignment.

#### Thank you for your attention!

![Homework done](https://media.tenor.com/9qooEZ2uscQAAAAM/sleepy-tom-and-jerry.gif)