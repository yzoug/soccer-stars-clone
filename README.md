##Computer Science project: SoccerStars

To play, clone this repository or download the zip and unzip it on your computer. Then compile the files and launch the EcranAccueil class (*java EcranAccueil*). If you have a custom puck done for you (if you're a SCAN student mainly) enter your first name. For example, try *yassine* and *horia*.

**Team members :** Horia Burca, Guilhem Dupuy, Vincent Garcia, Yassine Zouggari

**Supervized by :** Yassine Zouggari (yzoug on GitHub)

![Image of the original game](http://i.imgur.com/Tchcfxw.jpg)

###Aim of the project: 

This project consist in creating a game similar to SoccerStars: a famous Android and iOS application. To get a grip of it, you can either download the application on a compatible mobile device or head to the website of the editor: [try the original SoccerStars](http://www.miniclip.com/games/soccer-stars-mobile/fr/). 

As suggested by the title, the *goal* of the players is the score against the opponent, like in a soccer game. To do so, you have 5 pucks at your disposal, on your side of the field: they consitute your team. In addition, there also is an 11th object, the soccer ball. The first team to push the ball inside the opponent's goal scores, and the first team that scores 2 times wins.

The pucks and the goal bounce at the limits of the field and can't hence leave the boundaries. Alternately, each player will launch one of his pucks using his mouse, which will determine the direction in which it will be thrown. Depending on the distance between the first click and to position of the cursor when released, the puck will be assigned a proportional velocity. If the puck hits another puck or the ball, both displacements will be modified (their magnitude and direction). When the puck hits the field limit, it will change trajectory as would a light beam in Optics when reflected.

The players hence use the bounces to their advantage, either to try and score, or the move the pucks of the opponent to disturb his attempts, or to defend their own goal.

### Main issues which had to be dealt with :

While coding our program, we mainly faced the following problems:

1. First of all, our field had to be delimited, on the graphical point of vue (which means that these limits should appear clearly to the users), but also limited with respect to the pucks and the ball: they never leave the field and always stay inside it.

2. Then, as the aim of our project is to program an arcade game, we had to make clear for each player what is going on in the game (who scored, who’s playing on which side, etc …)

3. The displacement of all our elements (which means of our 10 pucks and the ball) had to be taken care of

4. Probably the hardest part of our project, we had to figure out a way to implement rebounds in order to be as realistic as possible. This means the collisions had to impact the displacements in a realistic way, but without trying to simulate them exactly as their happen in real life. We also had to differentiate how they happen when the ball is hit, versus when two pucks enter in contact with one another.

5. We tried to code the program following the general POO guidelines (private attributes, factorization of the code etc) and a big effort was made to make the code as legible as possible and as compact as possible.

### How did we face these issues ?

* The first two problems were solved simply using IHM; indeed, we used a background image in order to indicate to the players were the field was, and we mesured it (pixel-wise) to find the actual limits of the field. Then, using the usual Graphics tools (*drawString(String a)*), we displayed the score. What took us the longest time is the precise positioning of each element, and the creation of all the graphics of the game: pucks at the right size, field modified to suit our needs for example. We had to use graphical softwares (GIMP mostly, or when possible, Paint) to achieve this.

* In order to make sure that all our objects (ball + pucks) stay inside our field at all times, we implemented rebounds on the extremities of our field (knowing their exact position). This was done inside the move() method, inside our abstract class *PaletAndBall* (see the part dealing with the hierarchy of our different objects).

* In order to implement the displacement of our objects (3), we tried to stay as simple as possible and only used 2 values, that were sufficient in order to simulate a standard mathematical vector, and hence allowing us to do basic trigonometry and mechanics for our displacements. Those two attributes of *PaletAndBall* are the direction and the speed, defined as doubles. The change of those variables is done with the mouse by the user, when he clicks and drags his mouse: we used *mousePressed* and *mouseReleased*, with a *MouseListener*.

* The most challenging part of our project was the rebounds (4). In order to solve this issue, we tried to adapt physical models to our program. We tried not to spend too much time on this, as it relied more on (complicated) Mechanics than on Computer Science but it was still a major part of our project. We used a *collision(PaletAndBall p)* method implemented inside our abstract *PaletAndBall* class. This method is called by our *actionPerformed* method (that itself is call by a Swing Timer) on each object (puck or ball) that is moving. It checks, after each displacement, if the new position of the puck enters in contact with another object, and if that's true, it first moves the puck back up until it no longer is **above** the other object, then changes the directions and speeds of both objects accordingly.

* We also had to face a major and unexpected issue; indeed, our pucks were moving too quick for our timer calling the *collision* method every 50ms. Due to this, our objects were often overlapping each other despite the precautions we took (explained just above), which was sometimes resulting in non-existing rebounds (pucks were sometimes going through other objects without bouncing against them). Some small tweaks on our *collision* method, and a lower limit for the maximum speed of our objects (from 200 to 100) were sufficient for compeletly solving this, but took us quite some time debugging.
 
### Bibliography:

Two resources were needed during our work on this project: the [javadoc](https://docs.oracle.com/javase/7/docs/api/) mainly, and the Asteroids TP we did in class: the code we wrote for that TP helped us with some similar code that had to be written for our game (buffers, images etc).
We also relied on our Mechanics textbook to come up with a simple collisions model, mainly concerning the changes in direction.

### Organization of our program:

Her is the complete list of the different objects we used :

* An abstract class named *PaletAndBall*. As its name suggests, it is divided in 2 child classes :
 * public *Ball*
 * public *Palet*

We found the creation of such a class usefull in order to optimize the space occupied by our program; indeed, “Ball” and “Palet” objects share lots of common methods and attributes. In order to be able to know if our *PaletAndBall* object is a ball or a puck, we implemented the abstract isBall method, which returns a boolean. This is because throughout the game, we manipulate an *objects* variable that is an array containing ten pucks and a ball, hence we actually never know if we have a puck or a ball without using this method.

* A public class named *Jeu*, child class of a JFrame, whcih implements an *ActionListener* (used for our Timer) and a *MouseListener* (used by the players when they set the speed and direction of one of their pucks).

* A public *EcranAccueil* class, child class of a JFrame which implements an *ActionListener*. This listener is used in order to detect when the players click on the ball, which launches a new game. You can also choose the name of your team in the two JTextField. If the name correspond to one of the puck we have in the folder *images/*, you will have a special kind of puck.

* A public class named *EcranFinal* child class of a JFrame is launched when someone wins, i.e. when someone score two times. This class implements an *ActionListener*, you can go back tothe menu or replay the game with the same team names.

* A public class named *SoundClip*, which deals with the audio part of our program (music played during the game and while our *EcranAccueil* is displayed)

### Enhancement ideas :

In our opinion, the main problem of our program is the sometimes non-realistic rebounds. Some extra work could be done in order to find better physical models, which would enhance the gameplay.

Furthermore, we could also add posts to each side of our goals, which would make the rebounds on the side of the goals more interesting. This could be quite easily done by creating a non-moving *PaletAndBall* object. We didn’t realize this on our program as the use of a drawing software like Paint, Photoshop or GIMP is very time-consuming and not very interesting in terms of Computer Science.

Some animation could be added when one of the players scores, once again in order to enhance the gameplay quality.

Finally, the big enhancement idea (that Yassine will still work on even after the end of the project) is playing online. However, at INSA, the Internet access we have is behind a firewall that blocks all ports (except a few reserved ones). That's why for example it's impossible to use Steam or IRC clients from our homes. This made the writing of the server-side client-side of the code a lot harder, since I simply couldn't test if it was working or not. If I manage to find a solution it'll be possible to play our game over the web (I even have a server that can do the connection).

### Planning

The actual way our project happened, the whole day-to-day changes are all available on GitHub: simply look at the [commits](https://github.com/yzoug/soccer-stars-clone/commits/master).

However, we also had an initial planning that we hence didn't follow at the letter, but it's amusing to see how priorities shift when we actually start working on the project versus simply planning it. If you want to compare what happened and what we planned, here it is:

11/12/2015 :
* Study the mechanics aspects of the game.
* Create the objects Puck and Ball.
* Beginning the IHM part of the project.

18/12/2015 :
* Finish the IHM of the game (field + puck + ball).
* Try to throw a puck on the field.
* Try to integrate the mechanics aspects to the programme.
        
06/101/2015 :
* Finish the throwing of the puck on the field.
* Continue to integrate the mechanics aspects.
* From now on, we were able to play a game (with lots of bugs thow).

13/01/2015 :
* The finishing of each aspect of the programme were made.
* We added customizable pucks (with pictures of some students of the class and even some of our teachers available)

