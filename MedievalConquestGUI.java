package main;

public class MedievalConquestGUI {

    private GameEngine engine;
    private final int SCREEN_WIDTH = 1200;
    private final int SCREEN_HEIGHT = 800;

    public MedievalConquestGUI(){
        engine = new GameEngine(SCREEN_WIDTH, SCREEN_HEIGHT);
    }

    public static void main(String[] args){
        new MedievalConquestGUI();
    }
}