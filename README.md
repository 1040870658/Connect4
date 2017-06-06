# Assignment1.Connect4
## Game Flow
This app, a game with bomb theme, is strictly based on the rule of connect4. It contains two pages, starting page and game page.

Starting page
This page, with three controllable components, is very simple. Image 1 shows the basic feature of starting page.</br>
 ![Starting Page](https://github.com/1040870658/Connect4/raw/master/pics/start_activity.png)

   </br> Firstly, players are able to click the left bomb button to unlock the edit function and edit their own name, or default names will be used. Process showed as Image 2.

 ![Unlock Editing](https://github.com/1040870658/Connect4/raw/master/pics/unlock_edit_name.png)

   </br>Firstly, players are able to click the left bomb button to unlock the edit function and edit their own name, or default names will be used. Process showed as Image 2.
When the edit function is unlocked, the bomb button at the left side rotates up. Then, the name of players can be customized. 

Finally, it’s time to press the START button. When the button is pressed, it turns to gray indicating that the game is loading and, at the same time, the caption “Connect4”at the top will play a funny animation. After finishing the animation, game goes to the second page, game page, and end up the starting page. (After entering the game page, players are allowed to come back starting page at any time by pressing the back key of the smart phone hardware.)

Game page

Game page contains three sections: hint section at the top, control panel at the middle and game board at the bottom. Image 3 shows the detailed feature.

 ![initial_game](https://github.com/1040870658/Connect4/raw/master/pics/initial_game.png)

## Hint Section
This section hints the chess(bomb) color of each player and repeatedly plays rhythmic animation to hint who owns the current turn to put chess. For example, when the game turns to player 1, the text “Player 1” dances.

Control Panel
The control panel is the important section to do special interaction with players.

Firstly, there’s a text “total step” showing how many steps have been covered up to current time. When the number of total steps is up to 30, the text changes its color to red in order to warn the players it’s almost meet the limited steps. Naturally, when the steps reach 42 which is the limited number of steps, game is over.

Secondly, there are two button below the step text. One is “Restart”, the other is “Rollback”.

“Restart”: The function of this button is to restart the game and clear all chess put before. On other words, players will lose the current state of the game and play game in initial state by clicking this button. This button can be accessed at any time.

“Rollback”: This button helps players to retract their chess. By clicking this button, three events will occur to the game.
1. The most recent chess bubbles up and then disappear. 
2. Text of steps roll back which means the number will minus one. 
3. Hint of players will also roll back. 
This button can NOT be accessed while the chess is moving(bubbling) and the game will ignore the clicking when the steps come to 0 or 42.

Thirdly, there is a hidden text showing the result above the steps text. The text show “Game Draw” in dark blue color when the step number reaches 42 without 4 connected chesses and, fundamentally, it shows correctly, with corresponding text and color, who wins the game while 4 connected chesses appear. This text flows out from small size to normal size at the termination of the game.

Game Board
Game board section is the main section of this game. The board has four states: Normal Static State, Putting State, Retracting State and Finished State. Specially, the board will not react any touch events and retract request except it is under Static State.

Normal State: This state displays the which positions are empty, which are covered by player1 and which are covered by player2. The color is gray, blue and orange respectively. The board keep waiting for players to put chess under this state.

Putting State: When a player gives a valid putting, the game board will turn to this state. In this state, a chess cell at the top of corresponding touched column will be colored and it falls down to the first accessible position while other chess cells which are above the target position in the same column moves up a position. In addition, when a player tries to put a chess in a full column, the whole column will shake and vibrate to indicate this invalid input.

Retracting State: This state opposite against Putting State. When the “Rollback” button is pressed, game board turns to this state. The chess which is most recently put in will move up to the top of the corresponding column and then disappear while other cells above the target in the same column will move down a position.

Finished State: When the game meets the termination conditions (reaching steps limit and one of the players win the game), the board turns to this state. If no one wins the game, the board will display nothing new. If one wins the game, all the connected chesses which fulfills the rule of CONNECT4 will display an animation that corresponding chesses boom after burning and shaking. Players need to press the RESTART button to reset the game in this state. Image 4 shows the detail of Finished State.</br>

 ![Finished State](https://github.com/1040870658/Connect4/raw/master/pics/p1win.png)
![Finished State](https://github.com/1040870658/Connect4/raw/master/pics/multi-winnerpath.png)
![Finished State](https://github.com/1040870658/Connect4/raw/master/pics/game_draw.png)

Game Design:
Design includes two part: GUI Design and Architecture Design. UI Design is to determine how this game flow and display look like under the basic rule of connect 4 while Architecture Design is about how to build this project in a flexible and clear implementation.

GUI Design
Basically, this game is actually a simple game without any complex rules or system. Thus, from my own perspective, I decide to follow the principle of brief, leisurely style instead of colorfulness and dazzle. However, the game resources, solid circle, make the game very dull. I create a theme which is bomb to this game. When it meets the winning condition, the bombs can boom. So, GUI design starts.

Color: Since the target is to achieve brevity. All the chosen colors are light and moderate. And each player is associated with one represent color: player1 is associated with light blue while player2 is associated with orange (red which is recommended in requirement is too dazzling). The association means all the component related to players should be render with their related color. As “Game Draw” does not belong to any players, I use different color. Finally, buttons are also in a lighter blue to make it harmonious in this game.

Starting Layout: Also, layout should follow the principle of brevity, especially the starting page. Colorful images make it less brief, so it’s a good idea to create several buttons and texts only. So, I design two bomb-like lockers, which is also the representation icon of player, to enable/disable the name editing function, a START button for starting game in the center of Starting Page and a simple text “Connect4”at the top. (Of course, locker for editing is not such necessary but it helps improve the visual and interactive effect.) Finally, as leaving the blank background is too simple, I put the game page graph with high opacity level in the background which decorates the page and also links these two pages to an entire game.

Game Layout: The difficulty for designing game layout is to make full use of limited space to achieve all the functions. So, I divide the page into three parts as mentioned in “GAME FLOW”. The game board is the most frequent used component being interacted with players so it should be the largest part. Thus, I minimize the size of other components. Hence, the text for showing result is just a line of text hidden above the steps text.

Animation: I design a lot of animations to make game more of fun.

Starting Page: There are three animations in this page.
1.  Lockers for editing rotate while players lock/unlock the edit text. 
2.  Start button turn gray indicating game loading after being pressed.
3.  The caption “Connect 4” rotates in a happy rhythm before page changing.

Game Page: There are six animations in this page.
1.  For hint to the players, I try to use special way to do it under limited space so the text of players’ name is set to be rotatable. The text of current player continually dances to hint which owns the turn.
2.  The text for result scales out at the termination of the game.
3.  The chess bubbles down while being put, which interprets the route to reach its target position.
4.  The chess bubbles up while being retracted. This design mainly wants to achieve the opposite effect of putting chess.
5.  The bombs which fulfill the winning path firstly burn and then boom. This make the bomb theme more vivid.
6.  The column will shake while players try to put chess in a full column.

## Architecture Design

Fundamentally, architecture design is to achieve flexibility, modifiability and maintainability. I try every to separate model, view and logical operation. Furthermore, as the special development mode of Android development, I also try to delegate all the model and logical operation to a new class, called presenter, instead of having them controlled by Activity. Because activity have to maintain view operation so it must be chaos when activity maintain both logic and view. I design four essential classes: GamePresenter which is used to handle the logic (game judge, model refresh, etc), GameMotion which is implemented for complex animation of the view, Board which is one of the major models of the game and MainActivity. As a matter of fact, MainActivity is only on duty of displaying view. (But I still failed to totally separate the model from MainActivity. In other word, some operations in MainActivity still directly rely on the information of Board, the model of the game.) Logic computation running on a child thread and notify UI Thread to refresh view by a handler declare in UI Thread so that logic and view can be combined.

Under this design, this game can work efficiently and is able to be easily modified or expanded. For example, the initial 6\*7 board can easily change to 4\*4 board.

## Implementation

There are total two pages of this application as mentioned in Game Flow: Starting Page and Game Page.

Starting Page contains few logic operations. Therefore, Starting Page can be easily implemented by coding one class only, StartActivity and the major job is to write xml file to achieve the layout mentioned in Game Flow. The layout applies RelativeLayout to list buttons and edit texts by LinearLayout while cover an ImageView with low alpha value to set the background.

Game Page is the main part of this game. As mentioned in Architecture Design, there are four major classes: GamePresenter, GameMotion, Board and MainActivity. 

Board: This class is the major model class which mainly contains three two dimension arrays: board, record and imageButton. The board stores the data of how the game state which indicates what is covered in a specific cell of the game board. The record memories the before cell of each cell to help players retract their steps. The imageButton is the actual entity of gridlayout using in MainActivity. These buttons are only used to access by displaying animations. In addition, the essential logic about judgement firstly get the target column and row, then walk through the vertical, horizontal，diagonal and counter-diagonal orientation to find if there more than 4 chess connected while adding them to the winner-path. 

GamePresenter: This class is actually used to maintain the Board object. Each putting chess operation, in order to migrate time-consuming operation outside the UI thread, will activate a new thread to inform GamePresenter to invoke put chess method. The method will invoke other methods to modifies the state of board, make judgement of the steps and decide whether the game should be ended up after this step. When each of this operation is done, the pointer to the handler of father thread will send a message back and notify MainActivity to do corresponding view refreshing. The retracting operation is similar with putting chess except for the reference data, record array. The purpose of running child thread is to avoid ANR or, slightly, disfluency. 

GameMotion: This class handle the moving operation of this game. Putting chess is set as bubbling down animation, retracting chess is set as bubbling up, the opposite against putting chess, and winning is set as booming animation. The implementation of all of these three animations is to set object animator to the target while sending appropriate messages by the adding animation listener the animation objects. For example, after finishing bubbling down animation and meet the winning condition, the animation listener will send a message to ask for starting displaying booming animation.

Most of the animations are implemented by ObjectAnimator while a few animations are frame animations. Therefore, it’s important to synchronize the moving object as they are repeatedly invoked their setters of corresponding properties. Therefore, I set a Boolean lock for each round of player. When there’re object moving, the lock is closed. When the lock is closed, neither putting and retracting operation can be valid. After finishing moving, the lock will return to the players and wait for another operation. Without the lock, game will crash as the resources contradiction occurs by quickly and repeatedly putting chess. 

MainActivity: Actually, this class is only use to invoke other object to do logical maintenance and operate game animations. The behavior of this class is simple: set text for some component and set up game layout. At the top, I apply ObjectAnimator to the TextView for user name and run the animation repeatedly so that the text looks like dancing all the way. At the bottom, I use GridLayout to dynamically load chess board so that duplicated and redundant codes will not appear in a large-scale xml file. What’s more, it’s easy to change the size of game board in this way.

Other detail of implementation refers to the code of source files. 

## Additional Features
Animation:
1.  Lockers for editing rotate while players lock/unlock the edit text. 
2.  Start button turn gray indicating game loading after being pressed.
3.  The caption “Connect 4” rotates in a happy rhythm before page changing.
4.  The text of current player continually dances to hint which owns the turn.
5.  The text for result scales out at the termination of the game.
6.  The chess bubbles down while being put.
7.  The chess bubbles up while being retracted. 
8.  The bombs which fulfill the winning path firstly burn and then boom. 
9.  The column will shake while players try to put chess in a full column.

Function:
1.  Players can edit their name in the starting page and they can access this page at any time by clicking back key.
2.  Display the current covered steps and hint the coming of limited steps by color.
3.  The phone vibrates when players try to put chess in a full column.

## Limitation
Not very suitable for running under resolution lower than 800*400.


