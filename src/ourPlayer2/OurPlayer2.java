/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ourPlayer2;

import battleship.interfaces.Board;
import battleship.interfaces.Fleet;
import battleship.interfaces.Position;
import battleship.interfaces.Ship;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Jeanette
 */
public class OurPlayer2 implements battleship.interfaces.BattleshipsPlayer {

    private final static Random rnd = new Random();
    private int sizeX;
    private int sizeY;
    private int nextX;
    private int nextY;
    private int corX;
    private int corY;
    ArrayList<Position> coordinates = new ArrayList<>();
    private boolean lastHit;
    private boolean beforeLastHit;
    private int enemyFleet;
    private int enemyFleet2;
    ArrayList<Position> killShot1 = new ArrayList<>();

    @Override
    public void startMatch(int rounds) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void startRound(int round) {
        corX = 0;
        corY = 0;

        lastHit = false;
        beforeLastHit = false;
    }

    @Override
    public void placeShips(Fleet fleet, Board board) {
        nextX = 0;
        nextY = 0;
        sizeX = board.sizeX();
        sizeY = board.sizeY();
        for (int i = 0; i < fleet.getNumberOfShips(); ++i) {
            Ship s = fleet.getShip(i);
            boolean vertical = rnd.nextBoolean();
            Position pos;
            if (vertical) {
                int x = rnd.nextInt(sizeX);
                int y = rnd.nextInt(sizeY - (s.size() - 1));
                pos = new Position(x, y);
                coordinates.add(pos);
                board.placeShip(pos, s, vertical);

            } else {
                int x = rnd.nextInt(sizeX - (s.size() - 1));
                int y = rnd.nextInt(sizeY);
                pos = new Position(x, y);
                board.placeShip(pos, s, vertical);
            }
        }

    }

    @Override
    public void incoming(Position pos) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Position getFireCoordinates(Fleet enemyShips) {
        Position shot = new Position(corX, corY);

        if (lastHit == true || beforeLastHit == true) {
            System.out.println("bliver den tom og så kommer vi der ned? " + killShot1.size());
            if (killShot1.size() > 0) {
                if (enemyFleet == enemyFleet2) {
                    Position hitShot = killShot1.get(0);
                    killShot1.remove(0);
                    System.out.println(hitShot.toString());
                    return hitShot;
                }
            }
        } else {
            corY = corY + 2;
            if (corY >= sizeY) {
                if (corY == 10) {
                    corY = 1;
                } else {
                    corY = 0;
                }
                if (corX >= sizeX - 1) {
                    corX = 0;
                }
                corX = corX + 1;
            }
            shot = new Position(corX, corY);
            System.out.println("else skud" + shot.toString());
            return shot;
        }
        corY = corY + 2;
        if (corY >= sizeY) {
            if (corY == 10) {
                corY = 1;
            } else {
                corY = 0;
            }
            if (corX >= sizeX - 1) {
                corX = 0;
            }
            corX = corX + 1;
        }
        shot = new Position(corX, corY);
        return shot;
    }

    public ArrayList<Position> killWounded(int corX, int corY) {
        killShot1.clear();
        int tempX1 = corX;
        int tempX2 = corX;
        int tempY1 = corY;
        int tempY2 = corY;

        tempX1 = tempX1 - 1;
        tempX2 = tempX2 + 2;
        tempY1 = tempY1 - 1;
        tempY2 = tempY2 + 1;

        if (tempX1 >= sizeX) {
            killShot1.add(new Position(tempX1, corY));
        } else {
            killShot1.add(new Position(corX, corY));
        }

        if (tempX2 <= sizeX) {
            killShot1.add(new Position(tempX2, corY));
        } else {
            killShot1.add(new Position(corX, corY));
        }

        if (tempY1 >= sizeY) {
            killShot1.add(new Position(corX, tempY1));
        } else {
            killShot1.add(new Position(corX, corY));
        }

        if (tempY2 <= sizeY) {
            killShot1.add(new Position(corX, tempY2));
        } else {
            killShot1.add(new Position(corX, corY));
        }

        return killShot1;

    }

    @Override
    public void hitFeedBack(boolean hit, Fleet enemyShips) {
        if (hit && !this.lastHit && !this.beforeLastHit) {
            if (killShot1.isEmpty()) {
                killShot1 = killWounded(corX, corY);
            }
            enemyFleet = enemyShips.getNumberOfShips();
            enemyFleet2 = enemyFleet;
            this.beforeLastHit = this.lastHit;
            this.lastHit = hit;
        }
        if (!hit && this.lastHit && !this.beforeLastHit) {
            enemyFleet = enemyShips.getNumberOfShips();
            this.beforeLastHit = this.lastHit;
            this.lastHit = hit;
        }

        if (!hit && !this.lastHit && this.beforeLastHit) {
            enemyFleet2 = enemyShips.getNumberOfShips();
            this.beforeLastHit = this.lastHit;
            this.lastHit = hit;
        }

        if (hit && !this.lastHit && !this.beforeLastHit) {
            enemyFleet = enemyShips.getNumberOfShips();
            this.beforeLastHit = this.lastHit;
            this.lastHit = hit;
        }

        if (hit && this.lastHit && !this.beforeLastHit) {
            enemyFleet = enemyShips.getNumberOfShips();
            this.beforeLastHit = this.lastHit;
            this.lastHit = hit;
        }

        if (hit && this.lastHit && this.beforeLastHit) {
            enemyFleet = enemyShips.getNumberOfShips();
            this.beforeLastHit = this.lastHit;
            this.lastHit = hit;
        }
        this.beforeLastHit = hit;
        this.lastHit = hit;

    }

    @Override
    public void endRound(int round, int points, int enemyPoints) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void endMatch(int won, int lost, int draw) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
