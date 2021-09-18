-OVERVIEW: This is a basic CRUD example that incorporates elements that I have been learning in the past months.
In its most basic form this project will just incorporate JSF and JDBC. 
My intend with this part is to have the code clear and easy to understand, now about JDBC I know it's not necessary to incorporate
a connection pool but it seems to be one of the most efficient ways to set a connection, besides that the methods that connect to the DB 
use stored procedures, again it's faster than using regular selects and programming is a bit clearer this way in my opinion.

Regarding JSF, I tried to be as clear as I could be but the project got a bit messy because I decided to incorporate Primefaces artifacts.
Something I need to clarify is that I avoided AJAX since I haven't learned about it at this moment.

The last thing is that this project was made in Eclipse IDE and manages its libraries using Maven dependencies.


-INSTRUCTIONS: Import the schema stored in 'student_tracker.sql' in your DBMS of choice but be wary this schema was made in MySQL Workbench
so you might need to make adjustments if yours differ, and if this is the case you have to replace the database connector in the POM file as well. 
Verify that the data on the connection pool class so it matches yours 
(Localhost port, user name, password, as well as the connection string, you know the drill), then run the App and verify if it's working.

-KNOWN ISSUES: For some reason the sorting function of the DataTable doesn't work on the links that let you update or delete.
The search button has to be clicked twice to generate changes, this behavior is not intended.

-FUTURE UPDATES: Fixing the bugs is a must and as soon as I get the necessary knowledge I will do it.
One of the things that I learned while trying to debug this App is that Primefaces incorporated AJAX as one of its essential elements,
so there's a possibility that the architecture of the App changes drastically if that's what It takes to debug it.
If said case is true I will make another repository that doesn't incorporate Primefaces to keep it readable for beginners.
