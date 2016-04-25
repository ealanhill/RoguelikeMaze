# RoguelikeMaze
A Roguelike Maze generation tool.

##Introduction
This tool randomly generates a Roguelike Maze with rooms, corridors and closed walls. The rooms a randomly generated and only placed within the world if they do not overlap another room or are next to a corridor.

- Wall => represented by a '#'
- Floor => represented by a ' '
- Closed Door => represented by a 'X'
- The Boundary of the World => represented by a '.'

To run the generator you may double click on the executable jar (RoguelikeMaze.jar), please ensure 'config.properties' is in the same directory of the jar if you want to use custom properties for the directory.

Or, you can run the generator using the following from the command line:

```
java -jar RoguelikeMaze.jar
```

##Properties
A properties file, 'config.properties' is included, where a user can set the following properties:

- ```maxRoomSize``` => the maximum number of characters in the height and width of a room
- ```roomTries``` => how many times the generator should attempt to insert a room into the world
- ```worldWidth``` => how wide the world should be
- ```worldHeight``` => how high the world should be

##Build
Clone the repository onto your local machine or download the code from the release. There are a couple of options to build the generator:

####Eclipse
1. Open Eclipse
2. Go to File > New > Project...
3. Select 'Java Project from Existing Ant Buildfile'
4. Click 'Browse'
5. Browse to the 'build.xml' file
6. Click 'Finish'
7. Open 'build.xml'
8. Edit the ```basedir``` property of the Ant project definition to point to where you cloned the source code 
9. Open the Ant perspective by selecting Window > Show View > Other... > Ant
10. Double click on the 'jar' target

####Commandline
NOTE: you must have Apache Ant installed.

1. Open a terminal
2. Navigate to the directory you saved the source
3. Run the following command: ```ant jar```
