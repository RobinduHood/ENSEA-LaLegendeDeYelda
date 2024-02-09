import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Dungeon {
    public int getHeight() {
        return height;
    }

    private final int height;

    public int getWidth() {
        return width;
    }

    private final int width;
    private final TileManager tileManager;
    private ArrayList <Things> renderList = new ArrayList<>();

    private char[][] map;
    private List<Trap> traps = new ArrayList<>(); // Liste des pièges

    public Dungeon(int height, int width) {
        this.height = height;
        this.width = width;
        this.tileManager = new TileManager(32,32);
        this.map = new char[width][height];
        for (int x=0;x<width;x++){
            for (int y=0;y<height;y++){
                if ((x==0)||(x==width-1)||(y==0)||(y==height-1)){
                    this.map[x][y]='W';
                }
                else {
                    this.map[x][y]=' ';
                }
            }
        }
        respawnListOfThings();
    }

    public Dungeon(int height, int width, TileManager tileManager) {
        this.height = height;
        this.width = width;
        this.tileManager = tileManager;
        this.map = new char[width][height];
        for (int x=0;x<width;x++){
            for (int y=0;y<height;y++){
                if ((x==0)||(x==width-1)||(y==0)||(y==height-1)){
                    this.map[x][y]='W';
                }
                else {
                    this.map[x][y]=' ';
                }
            }
        }
        respawnListOfThings();
    }
    public boolean checkCollision(Hero hero) {
        for (Things thing : renderList) {
            if (thing instanceof SolidThings && ((SolidThings) thing).getHitBox().intersect(hero.getHitBox())) {
                return true;
            }
        }
        return false;
    }

    public Dungeon(String fileName, TileManager tileManager) {
        this.tileManager = tileManager;
        int height=0;
        int width=0;
        try{
            FileReader fileReader = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fileReader);
            while(br.readLine()!=null){
                height++;
            }
            br.close();
            br = new BufferedReader(new FileReader(fileName));
            String s = br.readLine();
            width = s.length();
            this.map = new char[width][height];
            for (int y=0;y<height;y++){
                for (int x=0;x<width;x++){
                    this.map[x][y]=s.toCharArray()[x];
                }
                s=br.readLine();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            this.height = height;
            this.width = width;
        }
        respawnListOfThings();
    }

    private void respawnListOfThings(){
        renderList.clear();
        traps.clear(); // Nettoyer la liste des pièges avant de la remplir à nouveau
        for (int x=0;x<width;x++){
            for (int y=0;y<height;y++){
                switch (this.map[x][y]){
                    case ' ' :  renderList.add(new Things(x* tileManager.getWidth(),y* tileManager.getHeigth(), tileManager.getTile(0,1)));
                        break;
                    case 'W' :  renderList.add(new SolidThings(x* tileManager.getWidth(),y* tileManager.getHeigth(), tileManager.getTile(0,0)));
                        break;
                    case 'E' :  renderList.add(new SolidThings(x* tileManager.getWidth(),y* tileManager.getHeigth(), tileManager.getTile(0,1)));
                        break;
                    case 'P' :  // Ajouter un piège à la position actuelle
                        Trap trap = new Trap(x * tileManager.getWidth(), y * tileManager.getHeigth(), 32, 32); // Remplacer 32 par la largeur et la hauteur de votre piège
                        traps.add(trap);
                        break;
                }
            }
        }
    }

    public void displayDungeonInConsole(HitBox hero){
        for(int y=0;y<height;y++){
            for(int x=0;x<width;x++){
                if (x==(hero.getX()/ tileManager.getWidth()) && y==(hero.getY()/ tileManager.getHeigth())) {
                    System.out.print("H");
                }
                else {
                    System.out.print((map[x][y]));
                }
            }
            System.out.println();
        }

    }

    public ArrayList<Things> getRenderList() {
        return renderList;
    }

    // Méthode pour récupérer les pièges de la salle de donjon
    public Trap[] getTraps() {
        return traps.toArray(new Trap[0]);
    }
}
