# Building
Import this [project](https://github.com/MXP2095onetechguy/JEXTEdit) to [eclipse](https://eclipse.org) or other [IDEs](https://netbeans.apache.org)(if it supports eclipse project).

Use JDK 11 or above, but I recommend you to stay with JDK 11 as this program is compiled with JDK 11 and compiled for java 11

Install the dependencies with [Apache Maven](https://maven.apache.org)

# Running from eclipse
Just click the run button

# Running from Jar

Put the jar on a directory and then run it from the command line, because the editor needs to extract itself to get access to the resources that way when packed in jar.

# Upgraded and running from Jar

Clean the whole directory where you put the old jar, this will cause the new jar to extract itself again, this is optional, but recommended just in case the new jar uses a new resource not available in the old jar, then follow the 'Running from Jar' step.