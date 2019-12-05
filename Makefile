
run: Main.class
	    java Main

compile: Main.class
	echo "go"

javadoc:
	    javadoc Main.java Console.java RandomNumber.java UserI.java

Main.class: Main.java RandomNumber.class
	    javac Main.java

Console.class: Console.java UserI.java
	    javac Console.java

UserI.class: UserI.java
	    javac UserI.java

RandomNumber.class: RandomNumber.java Console.class
	    javac RandomNumber.java

clean:
	rm *.class
	rm *.html
	rm *.css
	rm *.js
