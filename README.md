Modifications to old lab:
- add lockButtons and unlockButtons


List of changes:
- Instance variables: myColor, iWantRematch, otherWantsRematch

- Server Connect: connect, setChannel, joinLobby

- update myColor, lockButtons

- onRecieve handle: joinLobby, yourColor, play, end, rematch

- on playAgain press: add rematch logic

- when pressing a button, send play message with row, col, and piece number

- if winning player send end message and enable playAgain if I lost




## Turning it into multiplayer

In your constructor we'll need to do a few things. We'll connect to the server, set our channel to this lab, then connect to a lobby. The lobby will be created if there isn't one already.

The first player to the lobby will "be in charge". What this means is they will decide what color they are, and when the other player joins, the first player will tell them what color they are.

currentPlayer will be used more to draw the game and we'll have a seperate new instance variable called my color to be in charge of most of the logic.

Since `this.newGame()` randomizes our color, after it we'll update our `myColor` variable to be equal to whatever currentPlayer is.

### What multiplayer actually is

Before we get into the how we make this multiplayer, we need to talk about what multiplayer really is. Multiplayer often is just each player having their own games with the server trying to keep each player in sync. In the case of Connect 4, the gameboards will technically be completely seperate but our goal will be to keep the boards the same for both players. The server will be merely just sending messages back and forth meaning there are no checks for cheating. We'll be locking and unlocking our buttons so we can't cheat but just keep in mind that there really is nothing stopping you from sending or ignoring any messages from the other player.

### Sending messages to the other player

Locate in your code where you place a piece when pressing a button. It should be located in a loop in actionPerformed. The exact location we're looking for is when you call `gameboard.set(int, int, int)`. Right after this, let's use the `send(String, String)` method where we'll use `"play"` as a way to communicate the message is us making a move. The message should be the row, column, and piece number seperated by spaces. It should look something like "5 0 5" to communicate that at row 5, column 0, piece of type 5 was placed. Later on, we'll show how the other player will use this to place a piece on their board.

### Listening to the other player
In the method `onRecieve`, this is where we'll be listening for messages from the server or from the other player. 

#### joinLobby
The server will notify us when a player joins a lobby by sending a message with the type `"joinLobby"`. Create an if statement to check if the type is equal to `"joinLobby"`. If we recieve this message, it means that a player joined *after* you meaning you are in charge. This means you need to tell the other player that they are the other color. Use the `send(String type, String message)` method with the type `"yourColor"` and message of the opposite color of `myColor`.

#### yourColor
Now we have to shift perspective and act like we're the second player joining. When we're the second player joining, we're going to get a message with the type `"yourColor"` as the other player is telling us to be the opposite.

Since we're the second player, we'll also be going second, so we need to lock our buttons so only the other player can make a move.

#### play
This will be the main way players will communicate. They'll send a play type message that will include the row, column, and piece type seperated by spaces. When we recieve this message, we need to place a piece at that location and then unlock our buttons as that means it's our turn.