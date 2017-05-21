Assignment 2
#
The file hierarchy of this program is:
    Ozlympic
	src
	sqlite.jar
	database
	resources
	

To run the program the user must be inside the Ozlympic folder but outside all other folders. This structure is chosen as the database is automatically generated and searched for outside the src directory. to run the program the following commands should be run:
$ javac control/*.java exceptions/*.java games/*.java participants/*.java storage/*.java   // For compilation
$ java -cp sqlite-jdbc-3.16.1.jar;src control.Ozlympic // For execution

# Requirements
Java VM is needed to compile.

# Author
Student Name: Esteban Ramirez
Student Number: s3641207