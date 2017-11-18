# JavaEmailSender
Simple email sender using Java. This tool accepts parameters to define receivers and message content. This project can be imported into Eclipse IDE for further development. 

# Build
The file _build.xml_ has configuraiton we need for ant build.  So, to build we just execute ant, which creates the build files in bin directory.

# Run 
java -cp bin:lib/* com.frietec.sendmail.SendMail [parameters]

To see list of parameters

java -cp bin:lib/* com.frietec.sendmail.SendMail [--help|-h]


