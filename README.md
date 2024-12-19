# BankingProject
### by Benzea Mircea-Andrei


## Introduction:

This project is implements the backend logic for a banking application  
with various operations (e.g. addAccount, payOnline, sendMoney)

# Packaging:
## 1. The _**bank**_ package
>This is the most importante package for the application logic.  
> It includes **SIX** other _**packages**_:  
>>#### 1. _*accounts*_ containing:
>>> - Account (abstract class) extended by:
>>>  - Specific accounts (e.g. EconomyAccount)
>>> - account_factory containing:  
>>>  - AccountFactory (Interface) implemented by:
>>>    - SpecificFactories (e.g EconomyAccountFactory)
>>>
>>#### 2. _*cards*_ containing:
>>> - Card (abstract class) extended by:
  >>>  - OneTimeCard
  >>>  - StandardCard
>>> - card_factory containing:
>>>  - CardFactory (Interface) implemented by:
>>>    - SpecificFactories (e.g. OneTimeCardFactory)
>>> - CardInput 
>>#### 3. _*commands*_ containing:
>>> - Command (Interface) implemented by:
>>>   - All specific commands (e.g. AddInterest)
>>> - Transactionable (Interface) implemented by:
>>>  - Some commands, those who generate transactions (e.g. AddAccount)
>>> - command_factory containing:
>>>  - CommandFactory (Interface) implemented by:
>>>    - SpecificFactories (e.g. AddAccountFactory)
>>#### 4. _*commerciants*_ containing:
>>> - Commerciant
>>#### 5. _*transactions*_ containing:
>>> - Transaction (abstract class) extended by: 
>>>  - Specific transactions (e.g. AddAccountTransaction, CheckCardStatusTransaction etc.)
>>> - transaction_factory containing:
>>>  - TransactionFactory (Interface) implemented by:
>>>    - SpecificFactories (e.g. AddAccountTransactionFactory)
>>#### 6. _*users*_ containing:
>>> - User (abstract class) extended by:
>>>  - BasicUser
>>> - users_factory containing:
>>>  - UserFactory (Interface) implemented by:
>>>    - SpecificFactories (e.g. BasicUserFactory)  
> - #### And the following **two** classes:
>> 1. Bank - represents the bank with operations like:
>> -  createCard(cardInput card) -- creates a card  
>> - createAccount(accountInput account) -- creates an account  
>> - etc.  
>> 2. BankSettup -- represents the invoker that calls/creates commands
>>                   that are received by input. It has methods like:
>> - executeCommands() - executes the commands
>> - createBank() - creates a bank


## 2. The _**utils**_ package
> This package includes 
#### account_factory
      >  AccontFactory (Interface) implemented by:  
      >  * EconomyAccountFactory
      >  * StandardAccountFactory
#### cards
      > * BackRowCard
          >     * The Cursed One
      >     * Disciple
      > * FrontRowCard
          >     * Tank
      >     * The Ripper
      >     * Miraj

2. ### There is also the _**game**_ package that has the following classes:

* ###  Player
  > Represents a player in the game and the methods it can perform such as  
  drawCard(), placeCard() etc.
* ### GamesSetup
  > This class is used to set up and start the games given at input.  
  In this class we create an instance of the Game class for each of the  
  played games. We start the game and retrieve a game output for each  
  of the games that is then written in the output ArrayNode.
* ### Game
  > This class is used to perform possible actions within a game and  
  also some statistic related actions regarding the current series of games  
  such as getTotalGamesPlayed(), getPlayerOneWins(), getPlayerTwoWins().

* ### Game Table
  > This class is used to represent a game table, it s constants  
  > (e.g. nr of columns, nr of lines), and some table specific methods  
  > (e.g. addCard, doesPlayerHaveTanks).

3. ### The _utils_ package containing the following class:

* ### JsonNode
  >   This class is a helper class used to write input + output of  
  a command in JSON format.

4. ### The _main_ package contains the following classes:

* ### Main
  > This class is used to write the output of each test in JSON  
  > format
* ### Test
  > Class used for testing

## Code Flow:
Starting from the main package, in the main class we create an instace of the  
GamesSetup class in which we create instances of the Game class for each of  
the games received in the input.

In the Game class all actions are taken care of with the use of a public method  
that handles the command name of all the actions within a game/series of games.  
After that, depending on the command we will go through a private method  
located in the Game class that later on calls methods located in other classes  
such as: Cards (and by polymorphism the inheriting classes), Player, GameTable.

The output is generated with the help of the utility class JsonNode in the  
Game class and merged in the GamesSetup class.

## Project Feedback

This was an interesting project that helped me learn a lot about the basics  
of **OOP**. I really would recommend to anyone that wants to have a solid  
knowledge about primary OOP concepts such as Polymorphism and Inheritance  
take on the assignment.
#### Thank you for your attention!