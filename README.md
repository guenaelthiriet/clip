# clip
A short URL generator

"Clip" features
Shorten URL :
The user can provide a full, absolute URL to the program, and get back the corresponding "short URL". 
Two identical URLs always result in the same short URL being generated. 
All short URLs generated begin with http://cl.ip/ and are followed by a series of no more than 10 alphanumerical 

Expand URL Given a short URL previously generated with the program:
The user can get back the original non-shortened URL. 
If the short URL provided has not been previously generated by the program, an error should be given. 

Getting started:
The project uses Gradle.
To package the executable run "gradle fatJar". The executable will go into build/libs
You can then run clip from the command line using "java -jar clip-all-1.0-SNAPSHOT.jar"
