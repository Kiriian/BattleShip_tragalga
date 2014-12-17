/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package battleship.examples;

import battleship.interfaces.BattleshipsPlayer;
import tournament.player.PlayerFactory;

/**
 *
 * @author Tobias Grundtvig
 */
public class RandomPlayerFactory implements PlayerFactory<BattleshipsPlayer>
{
    private static int nextID = 1;
    private final int id;

    public RandomPlayerFactory()
    {
        id = nextID++;
    }
    
    
    @Override
    public BattleshipsPlayer getNewInstance()
    {
        return new RandomPlayer();
    }

    @Override
    public String getID()
    {
        return "RND:"+id;
    }

    @Override
    public String getName()
    {
        return "Random player " + id;
    }
    
}
