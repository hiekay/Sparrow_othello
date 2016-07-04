# Sparrow_othello
Othello (Reversi) is a strategy board game for two players, played on a 8×8 board. There are 64
game pieces, which are white on one side and black on the other. Players take turns placing white/black pieces on the board with their assigned color facing up. During a play, pieces of the opponent's color that are in a straight line and bounded by the piece just placed and another piece of the current player's color are flipped over to the current player's color. Figure 1 shows an example of the turning pieces
Game Rules:
1. Player using black pieces will move first, and then it will be the move of the player using
white pieces, and so on.
2. When placing a new piece, all the opponent player’s piece(s), which is/are bounded by
that newly placed piece and other piece(s) on the board with the same color in all 8 directions (up, down, left, right, top-left, top-right, bottom-left, and bottom-right), will be flipped into the same color as the newly placed piece. The following Figure 3 shows some examples of the flipping.
3. Player can only place a piece on the board if that move can flip piece(s) of the opponent player.
4. If the player does not have any move in his/her turn (no move can flip opponent player’s pieces), his/her turn will be skipped, and it becomes the opposite player’s turn (Figure 4).
5. If all the 64 positions on the board have pieces placed, or both players do not have any more move (Figure 5), the game ends.

Basic Features:
1. The Othello application contains 2 pages. The first page should contain candidate’s name
and university number for identification purpose. This page should also provide a button “Start Game” for starting the game. The second page is the game main frame which will be loaded when the “Start Game” in the first page is pressed.
2. At the a. b. c. d. e.
second page, candidates should design the layout for
A 8x8 chess board (as the example in Figure 2)
8x8 (totally 64) positions on the board for placing the chess pieces Views showing the current number of white pieces and black pieces A view showing the current turn (white/black)
A button to start a “new game” anytime
3. The game starts with the black-chess player move first, and then it is the turn of white- chess player, and so on. The move is induced when the player touch the corresponding position on the game board. For each move, the game engine should check whether this move is valid or not (Point 3 in Section “Game Rules”). The game engine can ignore any invalid move.
4. For each valid move, the corresponding chess pieces on the board, the black/white pieces counts, and the current turn should be updated accordingly
5. When the game ended (Point 5 in Section “Game Rules”), the game should show the information: “who wins/loses” or “game draw”.
6. Please NOTE that NO artificial intelligent (AI) is required in this assignment. Students have to only implement the “human vs human” mode.
Additional Features :
A button for a player to switch on/off in showing the hint of the next possible moves (as the example showing in Figure 6).
