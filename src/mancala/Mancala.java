package mancala;

import sun.rmi.runtime.Log;

public class Mancala
{
    public int[] pits;
    public int activePlayer;
    public Mancala()
    {
        pits = new int[]{4, 4, 4, 4, 4, 4, 0, 4, 4, 4, 4, 4, 4, 0};
        activePlayer = 0;
    }

    public void stow(int pitIndex)
    {
        if(isIllegalMove(pitIndex))
            return;

        int seedsInHand = pickupSeeds(pitIndex);

        while(seedsInHand != 0)
        {
            pitIndex = nextPit(pitIndex);
            seedsInHand = dropSeedInPit(pitIndex, seedsInHand);
        }

        if(pitIndex != getPlayerGoalIndex(activePlayer))
            endTurn();
    }

    private void endTurn()
    {
        activePlayer++;
        activePlayer %= 2;
    }

    private int nextPit(int pitIndex)
    {
        pitIndex++;
        if(isPitOpponentsGoal(pitIndex))
            pitIndex++;

        pitIndex %= pits.length;
        return pitIndex;
    }

    private int dropSeedInPit(int pitIndex, int seedsInHand)
    {
        pits[pitIndex] += 1;
        seedsInHand--;
        return seedsInHand;
    }

    private boolean isPitOpponentsGoal(int pitIndex)
    {
        if(activePlayer == 0 && pitIndex == getPlayerGoalIndex(1))
            return true;
        if(activePlayer == 1 && pitIndex == getPlayerGoalIndex(0))
            return true;
        return false;
    }

    private int pickupSeeds(int pitIndex)
    {
        int seeds = pits[pitIndex];
        pits[pitIndex] = 0;
        return seeds;
    }

    private boolean isIllegalMove(int pitIndex)
    {
        if(activePlayer == 0 && pitIndex > 5)
            return true;

        if(activePlayer == 1 && ((pitIndex < 7) || pitIndex == 13))
            return true;

        return isPitEmpty(pitIndex);
    }

    private boolean isPitEmpty(int pitIndex)
    {
        return pits[pitIndex] == 0;
    }

    public boolean isGameOver()
    {
        return (isPlayerWinner(0) || isPlayerWinner(1));
    }

    private int getPlayerFirstPitIndex(int playerIndex)
    {
        if(playerIndex == 0)
            return 0;
        else if(playerIndex == 1)
            return 7;
        return 0;
    }

    private int getPlayerGoalIndex(int playerIndex)
    {
        if(playerIndex == 0)
            return (pits.length/2)-1;
        else if(playerIndex == 1)
            return (pits.length-1);
        return 0;
    }

    public int winner()
    {
        if(isPlayerWinner(0))
            return 0;
        if(isPlayerWinner(1))
            return 1;
        return -1;
    }

    private boolean isPlayerWinner(int playerID)
    {
        int seedsLeft = 0;

        int firstPitIndex = getPlayerFirstPitIndex(playerID);
        int goalIndex = getPlayerGoalIndex(playerID);

        for (int i = firstPitIndex; i < goalIndex; i++)
            seedsLeft += pits[i];

        return (seedsLeft == 0);
    }
}
