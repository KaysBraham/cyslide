package src;
public class Level {
    private Tiles[][] tiles;
    private boolean solved;
    public Tile getTile(int x,int y){
        return tiles[x][y];
    }

}