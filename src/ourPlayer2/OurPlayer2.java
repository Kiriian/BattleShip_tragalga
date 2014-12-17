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
    private boolean lastHit = false;
    private boolean beforeLastHit = false;
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

    }

    @Override
    public void placeShips(Fleet fleet, Board board) {
        getCoordinates();
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
                coordinates.add(pos);
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
        Position hitShot;
        if (lastHit == true || beforeLastHit == true) {
            if (killShot1.size() > 0) {
                if (enemyFleet == enemyFleet2) {
                    hitShot = killShot1.get(0);
                    killShot1.remove(0);
                    System.out.println("her fjerne vi et element fra arrayet: " + killShot1.size());
                    System.out.println("skud " + hitShot.toString());
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
            Position shot = new Position(corX, corY);
            System.out.println("else skud" + shot.toString());
            return shot;
        }
        return null;
    }

    public ArrayList<Position> getCoordinates() {
        return coordinates;
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

//        if (corX - 1 < sizeX) {
//            killShot1.add(new Position(0, corY));
//        } else {
//            killShot1.add(new Position(corX - 1, corY));
//        }
//        if (sizeX < corX + 1) {
//            killShot1.add(new Position(9, corY));
//        } else {
//            killShot1.add(new Position(corX + 1, corY));
//        }
//        if (corY - 1 < sizeY) {
//            killShot1.add(new Position(corX, 0));
//        } else {
//            killShot1.add(new Position(corX, corY - 1));
//        }
//        if (corY + 1 > sizeY) {
//            killShot1.add(new Position(corX, 9));
//        } else {
//            killShot1.add(new Position(corX, corY + 1));
//        }
        return killShot1;

    }

    @Override
    public void hitFeedBack(boolean hit, Fleet enemyShips) {
        if (hit && !this.lastHit && !this.beforeLastHit) {
            enemyFleet = enemyShips.getNumberOfShips();
            enemyFleet2 = enemyFleet;
            this.beforeLastHit = this.lastHit;
            this.lastHit = hit;
            killShot1 = killWounded(corX, corY);
            //System.out.println("størrelse af array i nummer et: " + killShot1.size());
        }

        if (!hit && !this.lastHit && this.beforeLastHit) {
            enemyFleet2 = enemyShips.getNumberOfShips();
            this.beforeLastHit = this.lastHit;
            this.lastHit = hit;
            //System.out.println("størrelse af array i nummer fem: " + killShot1.size());
        }

        if (!hit && this.lastHit) {
            enemyFleet = enemyShips.getNumberOfShips();
            enemyFleet2 = enemyFleet;
            this.beforeLastHit = this.lastHit;
            this.lastHit = hit;
            //System.out.println("størrelse af array i nummer fire: " + killShot1.size());
        }

        if (hit && !this.lastHit && !this.beforeLastHit) {
            this.beforeLastHit = this.lastHit;
            this.lastHit = hit;
            //System.out.println("størrelse af array i nummer tre: " + killShot1.size());
        }

        if (hit && this.lastHit && !this.beforeLastHit) {
            this.beforeLastHit = this.lastHit;
            this.lastHit = hit;
            //System.out.println("størrelse af array i nummer to: " + killShot1.size());
        }
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
