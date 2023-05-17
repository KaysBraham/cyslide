package src;
public class Level {
    private Tile[][] tiles;
    private boolean solved;
    public Tile getTile(int x,int y){
        return tiles[x][y];
    }

    public static void shuffleLevel() {
        T = this.tiles
        List<Tile> tempList = new ArrayList<>();
        int n  = T.lenght;
        for (int i=0; i<= n-1; i++) {
            if (T[i].getValeur() != -1 ) {
                tempList.add(T[i].getValeur);
            }
        }

        Collections.shuffle(tempList);
        int p = 0 ;
        for (int i=0; i<= n-1; i++) {
            if (T[i].getValeur() != -1 ) {
                T[i] = tempList[p];
                p += 1;
            }
        }

}