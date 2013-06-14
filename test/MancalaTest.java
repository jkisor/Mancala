import mancala.Mancala;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MancalaTest
{
    @Test
    public void startingBoardIsSetupCorrectlyByDefault()
    {
        Mancala board = new Mancala();
        int[] expected = new int[]{4, 4, 4, 4, 4, 4, 0, 4, 4, 4, 4, 4, 4, 0};
        assertTrue(Arrays.equals(board.pits, expected));
        assertFalse(board.isGameOver());
    }

    @Test
    public void stowedPitIsEmpty()
    {
        Mancala board = new Mancala();
        board.pits = new int[]{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        board.stow(0);
        assertEquals(0, board.pits[0]);
    }

    @Test
    public void stowingSeedsAddsOneSeedToEachFollowingPit()
    {
        Mancala board = new Mancala();
        board.pits = new int[]{3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        board.stow(0);
        int[] expected = new int[]{0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

        assertTrue(Arrays.equals(board.pits, expected));
    }

    @Test
    public void Player1StowingGoesAroundBoardSkippingPlayer2Goal()
    {
        Mancala board = new Mancala();
        board.pits = new int[]{14, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        board.activePlayer = 0;
        board.stow(0);
        int[] expected = new int[]{1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0};

        assertTrue(Arrays.equals(board.pits, expected));
    }

    @Test
    public void Player2StowingGoesAroundBoardSkippingPlayer1Goal()
    {
        Mancala board = new Mancala();
        board.pits = new int[]    {0, 0, 0, 0, 0, 0, 0, 14, 0, 0, 0, 0, 0, 0};
        board.activePlayer = 1;
        board.stow(7);
        int[] expected = new int[]{1, 1, 1, 1, 1, 1, 0, 1, 2, 1, 1, 1, 1, 1};

        assertTrue(Arrays.equals(board.pits, expected));
    }

    @Test
    public void Player2CannotStowPlayer1Side()
    {
        Mancala board = new Mancala();
        board.pits = new int[]    {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        board.activePlayer = 1;
        board.stow(0);
        int[] expected = new int[]{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

        assertTrue(Arrays.equals(board.pits, expected));
    }

    @Test
    public void Player1CannotStowPlayer2Side()
    {
        Mancala board = new Mancala();
        board.pits = new int[]    {0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0};
        board.activePlayer = 0;
        board.stow(7);
        int[] expected = new int[]{0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0};

        assertTrue(Arrays.equals(board.pits, expected));
    }

    @Test
    public void Player1GoalCannotBeStowed()
    {
        Mancala board = new Mancala();
        board.pits = new int[]    {0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0};
        board.activePlayer = 0;
        board.stow(6);
        int[] expected = new int[]{0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0};

        assertTrue(Arrays.equals(board.pits, expected));
    }

    @Test
    public void Player2GoalCannotBeStowed()
    {
        Mancala board = new Mancala();
        board.pits = new int[]    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1};
        board.activePlayer = 1;
        board.stow(13);
        int[] expected = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1};

        assertTrue(Arrays.equals(board.pits, expected));
    }

    @Test
    public void testAlternatingPlayerTurns()
    {
        Mancala board = new Mancala();
        board.pits = new int[]    {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        board.activePlayer = 0;
        board.stow(0);

        assertTrue(board.activePlayer == 1);
    }

    @Test
    public void stillPlayersTurnAfterStowingEmptyPit()
    {
        Mancala board = new Mancala();
        board.pits = new int[]    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        board.activePlayer = 0;
        board.stow(0);

        assertTrue(board.activePlayer == 0);
    }

    @Test
    public void player1ExtraTurnIfLastStowedSeedLandsInGoal()
    {
        Mancala board = new Mancala();
        board.pits = new int[]    {0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0};
        board.activePlayer = 0;
        board.stow(5);

        assertTrue(board.activePlayer == 0);
    }

    @Test
    public void player2ExtraTurnIfLastStowedSeedLandsInGoal()
    {
        Mancala board = new Mancala();
        board.pits = new int[]    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0};
        board.activePlayer = 1;
        board.stow(12);

        assertTrue(board.activePlayer == 1);

    }

    @Test
    public void gameIsOverWhenPlayer1PitsAreEmpty()
    {
        Mancala board = new Mancala();
        board.pits = new int[]    {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 0};
        board.activePlayer = 0;
        assertTrue(board.isGameOver());
        assertEquals(0, board.winner());
    }

    @Test
    public void gameIsOverWhenPlayer2PitsAreEmpty()
    {
        Mancala board = new Mancala();
        board.pits = new int[]    { 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0};
        board.activePlayer = 1;
        assertTrue(board.isGameOver());
        assertEquals(1, board.winner());
    }

}
