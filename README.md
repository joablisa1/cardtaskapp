		Instruction 
...................................................
1.Install Java 17 JDK  or latest version and configure it correctly.
2.Install maven 3.9.3 or latest version and configure it correctly.

	Requirements for the project
	.....................
1.Install MYSQL latest version.
2.IDE of your choice for development.
3.SpringBoot 3.1.2 or latest version.

		Testing
	....................
Any of this will work just fine
1.PostMan 
2.Swagger UI(already included into the project) 
for swagger users copy and paste this to your browser: http://localhost:8080/swagger-ui/index.html

Admin
username:admin
password:1234

users
username:user
password:1234



								NOTES
							.............
Please ensure that  mysql password and username  you change them appropriately with what you are using on your machine.
I have not used email but I have  provided the username to use in this case for login.
I have  swagger UI latest already configured and include in the project, just import the project into your IDE and execute/run it.
I also have default user in the project, there is a package init  which contain a SystemInt class that will initialize the users into the database and I have also added two dummy card to startup with you can create them .
You will have to authenticate the user using the password, I have provided above and authorize them on swagger UI or postman for access
without this you will get an Error: "response status is 403".And in case you are timed out just refresh and login again.
For search Api you will have to just execute it on Swagger without providing any content in the page field at the moment the record is below 10 as the record increases it will change automatically,
I created it in way that If you don't provide "keyword" It will List All the Cards, but you can search card of your choice. 





