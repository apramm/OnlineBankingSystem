# Personal Project - Online Banking System

#### *"All the banking operations on your fingertips"*
**WHAT'S THE SYSTEM ABOUT? & WHY DID THAT INTEREST ME?**

I've always interested in finance and how the online banking came into existence with just double variables and evolved 
to the complex reward points card with cashback provided to it's customer in a form of hook to their services.
This inspired me to create an Online Banking System, giving an option to the customer to add their account information
and later view all the information of different bank accounts as a list.Not only that the Online Banking System also 
enables them to perform banking operations on their fingertips through technology.

**User Story:**

1. As a user I would like to, *Add* my bank account's information like name, balance into the model.
2. As a user I would like to, view the Information of *Arbitrary* List of different types of Accounts created. 
3. As a user I would like to, have the reminder option to **save** the banking account information in app before 
quitting.
4. As a user I would like to, have an option to **load** the account state that was saved and use it again.
5. As a user I would like to, perform smooth transactions; credit or debit from the account (active balance should not
be below 0)
6. As a user I would like to, transfer between different type of accounts. 
7. As a user I would like to, have **multiple** types of Accounts in the bank. 
8. As a user I would like to, have the initial balance in savings and a minimum balance in other accounts.

Bonus Award! of reward points for every dollar spent. Automatically provided with cashback of 5 $ after
  reaching 1000 reward points.

(Sources : Teller App (in lecture-lab), Lab4-FoodServices Card, JsonSerializationDemo, 

  Basics From - https://www.youtube.com/watch?v=Kmgo00avvEw&t=3994s 

  Swing GUI - https://www.youtube.com/watch?v=KOI1WbkKUpQ 

  Button - https://docs.oracle.com/javase/tutorial/uiswing/components/button.html

  Alarm System(Event/EventLog) - https://github.students.cs.ubc.ca/CPSC210/AlarmSystem.git 
)

*** ***

**Phase 4: Task 2**

*Representation of what gets log:* 

Thu Mar 31 14:29:34 PDT 2022

Added apram to List

Thu Mar 31 14:29:34 PDT 2022

Added apram to List

Thu Mar 31 14:29:34 PDT 2022

Added apram to List

Thu Mar 31 14:29:40 PDT 2022

Debited Account with 2000.0

Thu Mar 31 14:29:46 PDT 2022

Credited BankAccount with 2000.0

Thu Mar 31 14:29:55 PDT 2022

Debited Account with 1000.0

Thu Mar 31 14:30:12 PDT 2022

Added any to List

Thu Mar 31 14:30:12 PDT 2022

Added any to List

Thu Mar 31 14:30:12 PDT 2022

Added any to List

We log three key events that happen in the Banking System which are Account Created > Debit > Credit. These tell us
when a user creates an account and perform any specified transaction on that. Which is done using the Event and EventLog
class in the model.

***

*REFLECTION*


Through this project I was able to understand how banks like Scotia, CIBC, RBC work and perform various functions
just on their users fingertips. In my UML Diagram: there are various classes like BankAccount and ListOfBank etc.

- When we open the ListofBank class we see that it uses an Array List of BankAccount hence they are 
  inter-related to each other with 0...* multiplicities.

- ListOfBank is an integral part in my UI classes such as BankingGUI and BankingSys through an aggregate relation. As 
  when BankingGUI we see that various functions are applied on the ListBank such as adding a BankAccount, credit and 
  debit these similar functions are performed in the console by BankingSys class.

- JsonReader and JsonWriter were two other classes which also have an aggregate relation with the UI classes 
used to perform the save and load functionality. Which is clearly evident by looking at the 2 UI classes.

- Main class is used to run the BankingSys console app. As it has new BankingSys() in it which calls the constructor and 
runs that class.

- Taking a look at the logging function of the system: we see that Event, EventLog and Iterable are the classes which
  perform the logging of account creation, credit, debit to increase the effectivity of the project. They apply directly
  on the Model classes to log the particular event that happens in the system and later print in console on quitting.

*REFACTORING*

If I had more time to do the project I will definitely 
- Focus more on the aesthetics of the project
- In my credit and debit functions, I would improve them by having some more known exceptions.
- I would also improve the exisitng Saving, Banking and Investment account type by allowing the user to choose 
any of them.
- Additionally, if there was a security page for login then that would've made the app much more safe. 
