import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

public class Blackjack extends JFrame {

    Container container;
    private JMenuBar instructor;
    private JMenu instructions;

    // Constructor to build the main menu. See <TitlePanel> class for specifics.
    public Blackjack() {
        super();
        setTitle("Blackjack Prototype");
        setBackground(new Color(65, 43, 21));
        setPreferredSize(new Dimension(1000, 800));
        setLayout(new GridLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        container = getContentPane();

        JPanel blackjackTitle = new TitlePanel();
        container.add(blackjackTitle);

        instructor = new JMenuBar();
        instructions = new JMenu("Beginning Launch");
        instructor.add(instructions);
        setJMenuBar(instructor);

        container.setLayout(new GridLayout());
        setVisible(true);
        pack();
    }

    // Method that runs to load a new pane for the player to play on
    public void StartGame(){
        setVisible(false);
        JPanel gamePanel = new NewGame();
        container.removeAll();
        container.add(gamePanel);
        System.out.println("Game has started");
        setVisible(true);
    }

    // Specifics for the look of the main menu. Indicates where to place buttons and text.
    class TitlePanel extends JPanel {

        private final JButton quitButton, playButton;

        public TitlePanel() {
            super();
            setBackground(new Color(91, 67, 14));
            setPreferredSize(new Dimension(1000, 800));
            setLayout(new GridLayout(3,1));

            ButtonActionHandler theActionListener = new ButtonActionHandler();

            playButton = new JButton("Play Button");
            playButton.setFont(new Font("Arial", Font.PLAIN, 60));
            playButton.setSize(200, 50);
            playButton.addActionListener(theActionListener);
            playButton.setBackground(new Color(195, 155, 119));

            quitButton = new JButton("Quit Button");
            quitButton.setFont(new Font("Arial", Font.PLAIN, 60));
            quitButton.setSize(200, 50);
            quitButton.addActionListener(theActionListener);
            quitButton.setBackground(new Color(190, 0 ,0));

            JLabel titleText = new JLabel("BlackJack");
            titleText.setForeground(Color.lightGray);
            titleText.setHorizontalAlignment(JLabel.CENTER);
            titleText.setFont(new Font("Times New Roman", 1, 200));

            add(titleText);
            add(playButton);
            add(quitButton);

            setVisible(true);
        }

        // Handles the listening and response to the user inputs for clicking buttons
        class ButtonActionHandler implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent evt) {
                Object source = evt.getSource();
                if (source == quitButton) {
                    System.exit(0);
                } else if (source == playButton) {
                    System.out.println("startingGame()");
                    StartGame();
                }
            }

        }
    }

    // The main gameplay.
    public class NewGame extends JPanel {

        TheListener theActionListener;
        JButton hitButton, passButton, nextButton, resetGame;
        JLabel playerOne, playerTwo, playerThree, playerFour;
        GameMechanics gameAi;

        // Constructor builds the essentials to a new game (where to put buttons, text, etc. for the interface)
        public NewGame() {
            super();
            theActionListener = new TheListener();
            gameAi = new GameMechanics();

            setBackground(new Color(65, 43, 21));
            setPreferredSize(new Dimension(1000, 800));
            setLayout(new GridLayout(2, 4));

            hitButton = new JButton("Hit");
            hitButton.setFont(new Font("Arial", Font.PLAIN, 60));
            hitButton.setSize(200, 50);
            hitButton.setBackground(new Color(195, 155, 119));
            hitButton.addActionListener(theActionListener);

            passButton = new JButton("Pass");
            passButton.setFont(new Font("Arial", Font.PLAIN, 60));
            passButton.setSize(200, 50);
            passButton.setBackground(new Color(195, 155, 119));
            passButton.addActionListener(theActionListener);

            nextButton = new JButton("Next");
            nextButton.setFont(new Font("Arial", Font.PLAIN, 60));
            nextButton.setSize(200, 50);
            nextButton.setBackground(new Color(195, 155, 119));
            nextButton.addActionListener(theActionListener);

            resetGame = new JButton("Quit");
            resetGame.setFont(new Font("Arial", Font.PLAIN, 60));
            resetGame.setSize(200, 50);
            resetGame.setBackground(new Color(190, 0, 0));
            resetGame.addActionListener(theActionListener);

            playerOne = new JLabel("Player One Score: 0");
            playerOne.setForeground(Color.lightGray);
            playerOne.setFont(new Font("Times New Roman", 1, 20));

            playerTwo = new JLabel("Player Two Score: 0");
            playerTwo.setForeground(Color.lightGray);
            playerTwo.setFont(new Font("Times New Roman", 1, 20));

            playerThree = new JLabel("Player Three Score: 0");
            playerThree.setForeground(Color.lightGray);
            playerThree.setFont(new Font("Times New Roman", 1, 20));

            playerFour = new JLabel("Player Four Score: 0");
            playerFour.setForeground(Color.lightGray);
            playerFour.setFont(new Font("Times New Roman", 1, 20));

            add(hitButton);
            add(passButton);

            add(playerOne);
            add(playerTwo);

            add(nextButton);
            add(resetGame);

            add(playerThree);
            add(playerFour);
        }

        // Handles the actions for the buttons, for example when the player asks for more cards.
        class TheListener implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent evt) {
                Object source = evt.getSource();
                if (source == hitButton) {
                    if (gameAi.whoseTurn == 0) {

                        gameAi.HitPlayer();
                    }
                } else if (source == passButton) {
                    if (gameAi.whoseTurn == 0) {
                        gameAi.PassPlayer();
                    }
                } else if (source == nextButton) {
                    gameAi.NextButton();
                } else if (source == resetGame) {
                    System.exit(0);
                }
            }

        }

        // Stores the player's scores using a map of a string mapping to an int, or the current total value of a player's hand
        // Also replicates a deck of cards using an array
        class GameMechanics {

            public Map<String, Integer> playerValues;
            public int[] cards;

            public int whoseTurn;
            public boolean[] isPlaying;

            public boolean hasMadeMove;

            // Creates a new game using default settings (52 cards, resets all hands)
            public GameMechanics() {
                playerValues = new TreeMap<>();
                cards = new int[13];
                isPlaying = new boolean[4];
                ResetGame();
            }

            // Resets the game
            public void ResetGame() {
                setVisible(false);
                for (int i = 0; i < isPlaying.length; i++) {
                    isPlaying[i] = true;
                }
                for (int i = 0; i < cards.length; i++) {
                    cards[i] = 4;
                }
                hasMadeMove = false;
                whoseTurn = 0;
                playerValues.clear();
                playerValues.put("Player 1", 0);
                playerValues.put("Player 2", 0);
                playerValues.put("Player 3", 0);
                playerValues.put("Player 4", 0);
                SetHelpText("Take your turn by clicking \"Hit\" button or the \"Pass\" button!");
                setVisible(true);
            }

            // Sets the tooltip help text above the game in order to guide the player.
            public void SetHelpText(String textHelp){
                instructor.removeAll();
                instructions = new JMenu(textHelp);
                instructor.add(instructions);
            }

            // The method for adding another 'card' to the player's hand.
            public void HitPlayer() {
                boolean searching = true;
                if (isPlaying[0]) {
                    if (!hasMadeMove) {
                        System.out.println("hitButton() was used!");
                        while (searching) {
                            int c = (int) (Math.random() * 11 + 1);
                            System.out.println("You got a " + c);
                            if (cards[c] > 0) {
                                cards[c] = cards[c] - 1;
                                if (c > 10) {
                                    c = 10;
                                }
                                SetNewValue("Player 1", playerValues.get("Player 1") + c);
                                playerOne.setText("Player One Score: " + GetPlayerValue(1));
                                SetHelpText("Hit the \"Next\" button in order for the computers to take their turns!");
                                hasMadeMove = true;
                                searching = false;
                            }
                        }
                    }
                }
                CheckBust(whoseTurn + 1);
            }

            public void SetNewValue(String key, int newValue) {
                playerValues.replace(key, newValue);
            }

            // Gives the computer the chance to get a card.
            public void HitComputer() {
                boolean searching = true;
                while (searching) {
                    int c = (int) (Math.random() * 11 + 1);
                    if (cards[c] > 0) {
                        cards[c] = cards[c] - 1;
                        if (c > 10) {
                            c = 10;
                        }
                        SetNewValue("Player " + (whoseTurn + 1), playerValues.get("Player " + (whoseTurn + 1)) + c);
                        searching = false;
                    }
                }
                CheckBust(whoseTurn + 1);
            }

            public int GetPlayerValue(int playerNumber) {
                return playerValues.get("Player " + playerNumber);
            }

            // Checks to see if a player has over a total value of 21 in the hand.
            public boolean CheckBust(int playerNumber) {
                if (GetPlayerValue(whoseTurn + 1) > 21) {
                    isPlaying[whoseTurn] = false;
                    switch(whoseTurn + 1){
                        case 1:
                            playerOne.setForeground(Color.red);
                            return playerValues.get("Player " + playerNumber) > 21;
                        case 2:
                            playerTwo.setForeground(Color.red);
                            return playerValues.get("Player " + playerNumber) > 21;
                        case 3:
                            playerThree.setForeground(Color.red);
                            return playerValues.get("Player " + playerNumber) > 21;
                        case 4:
                            playerFour.setForeground(Color.red);
                            return playerValues.get("Player " + playerNumber) > 21;
                    }
                }
                return playerValues.get("Player " + playerNumber) > 21;
            }

            // Once all players cannot make a move, the game checks to see who the winner is.
            public void CheckWinner() {
                int w = 0, pos = -1;
                if (playerValues.get("Player 1") > w && !CheckBust(1)) {
                    w = playerValues.get("Player 1");
                    pos = 1;
                }
                if (playerValues.get("Player 2") > w && !CheckBust(2)) {
                    w = playerValues.get("Player 2");
                    pos = 2;
                }
                if (playerValues.get("Player 3") > w && !CheckBust(3)) {
                    w = playerValues.get("Player 3");
                    pos = 3;
                }
                if (playerValues.get("Player 4") > w && !CheckBust(4)) {
                    w = playerValues.get("Player 4");
                    pos = 4;
                }
                switch(pos){
                    case 1:
                        playerOne.setForeground(Color.yellow);
                        return;
                    case 2:
                        playerTwo.setForeground(Color.yellow);
                        return;
                    case 3:
                        playerThree.setForeground(Color.yellow);
                        return;
                    case 4:
                        playerFour.setForeground(Color.yellow);
                        return;
                }
            }

            // Gives the computer a chance to control the other 3 players.
            public void ComputerTurn() {
                whoseTurn = 1;
                while (whoseTurn < 4) {
                    System.out.println("whoseTurn = " + whoseTurn);
                    if (isPlaying[whoseTurn]) {
                        if (!CheckBust((whoseTurn + 1))) {
                            int value = GetPlayerValue((whoseTurn + 1));
                            int a = (int) (Math.random() * 9);
                            if (value < 10) {
                                HitComputer();
                            } else if (value < 14) {
                                if (a > 1) {
                                    HitComputer();
                                } else {
                                    PassComputer();
                                }
                            } else if (value < 16) {
                                if (a > 4) {
                                    HitComputer();
                                } else {
                                    PassComputer();
                                }
                            } else if (value < 18) {
                                if (a > 7) {
                                    HitComputer();
                                } else {
                                    PassComputer();
                                }
                            } else if (value < 21) {
                                if (a == 2) {
                                    HitComputer();
                                } else {
                                    PassComputer();
                                }
                            } else {
                                PassComputer();
                            }
                        }
                    }
                    System.out.println("Computer #" + (whoseTurn + 1) + "'s turn has ended!");
                    whoseTurn++;
                }
                playerTwo.setText("Player Two Score: " + GetPlayerValue(2));
                playerThree.setText("Player Three Score: " + GetPlayerValue(3));
                playerFour.setText("Player Four Score: " + GetPlayerValue(4));
                whoseTurn = 0;
            }

            // The action of passing a player's turn until the round is over.
            public void PassPlayer() {
                if (isPlaying[0]) {
                    if (!hasMadeMove) {
                        playerOne.setForeground(Color.gray);
                        isPlaying[0] = false;
                        hasMadeMove = true;
                    }
                }
                whoseTurn++;
            }

            // The action of passing a computer's turn until the round is over.
            public void PassComputer() {
                System.out.println("Computer + " + (whoseTurn + 1) + " is passing!");
                isPlaying[whoseTurn] = false;
                switch(whoseTurn + 1){
                    case 2:
                        playerTwo.setForeground(Color.gray);
                        return;
                    case 3:
                        playerThree.setForeground(Color.gray);
                        return;
                    case 4:
                        playerFour.setForeground(Color.gray);
                        return;
                }
            }

            // Allows the computer to take action, and lets the player see the changes.
            public void NextButton() {

                if (hasMadeMove) {
                    System.out.println("nextButton() was used!");
                    int cnt = 0;
                    for(int i = 0; i < isPlaying.length; i++){
                        if(!isPlaying[i] || CheckBust(i + 1)){
                            cnt++;
                        }
                    }
                    System.out.println("CNT = " + cnt);
                    if (cnt > 2 || !isPlaying[0] && !isPlaying[1] && !isPlaying[2] && !isPlaying[3]) {
                        SetHelpText("The match is over! If someone's text is highlighted yellow that means they win!!!!");
                        CheckWinner();
                    } else if (!isPlaying[0] || CheckBust(1)) {
                        SetHelpText("You cannot make any more moves! Keep hitting the \"Next Button\" to finish the match!");
                        ComputerTurn();
                    } else {
                        SetHelpText("The other players have gone! \"Take your turn by clicking \"Hit Player\" or \"Pass Player\"!");
                        hasMadeMove = false;
                        ComputerTurn();
                    }
                } else {
                    SetHelpText("You need to make an action by clicking either \"Hit Player\" or \"Pass Player\", then by clicking \"Next Button\"");
                }
            }
        }
    }
}
