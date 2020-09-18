package unsw.dungeon;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;

/**
 * Loads a dungeon from a .json file.
 *
 * By extending this class, a subclass can hook into entity creation. This is
 * useful for creating UI elements with corresponding entities.
 *
 * @author Robert Clifton-Everest
 *
 */
public abstract class DungeonLoader {

    private JSONObject json;

    public DungeonLoader(String filename) throws FileNotFoundException {
        json = new JSONObject(new JSONTokener(new FileReader("dungeons/" + filename)));
    }

    public DungeonLoader(JSONObject json){
        this.json = json;
    }

    /**
     * Parses the JSON to create a dungeon.
     * @return
     */
    public Dungeon load() {
        int width = json.getInt("width");
        int height = json.getInt("height");

        Dungeon dungeon = new Dungeon(width, height);

        JSONObject jsonGoalCondition = this.json.getJSONObject("goal-condition");
        createAllQuests(dungeon, jsonGoalCondition);

        // Creating entities
        JSONArray jsonEntities = json.getJSONArray("entities");
        for (int i = 0; i < jsonEntities.length(); i++) {
            loadEntity(dungeon, jsonEntities.getJSONObject(i));
        }

        return dungeon;
    }

    private void loadEntity(Dungeon dungeon, JSONObject json) {
        String type = json.getString("type");
        int x = json.getInt("x");
        int y = json.getInt("y");

        Entity entity = null;
        switch (type) {
            case "player":
                AllQuests quest1 = AllQuests.getInstance();
                Player player = new Player(dungeon, quest1.getQuests(), x, y);
                dungeon.setPlayer(player);
                onLoad(player);
                entity = player;
                break;
            case "player_coop":
                AllQuests quest2 = AllQuests.getInstance();
                Player playerCoop = new Player(dungeon, quest2.getQuests(), x, y);
                dungeon.setPlayerCoop(playerCoop);
                onLoad(playerCoop);
                entity = playerCoop;
                break;
            case "wall":
                Wall wall = new Wall(x, y);
                onLoad(wall);
                entity = wall;
                break;
            case "treasure":
                Treasure treasure = new Treasure();
                PickUp treasurePU = new PickUp(x, y, (Item) treasure, "Treasure");
                onLoad(treasurePU);
                entity = treasurePU;
                break;
            case "gnome":
                Gnome gnome = new Gnome(x, y, dungeon);
                onLoad(gnome);
                entity = gnome;
                break;
            case "hound":
                Hound hound = new Hound(x, y, dungeon);
                onLoad(hound);
                entity = hound;
                break;
            case "camo_gnome":
                CamoGnome camoGnome = new CamoGnome(x, y, dungeon);
                onLoad(camoGnome);
                entity = camoGnome;
                break;
            case "sword":
                Sword sword = new Sword();
                PickUp swordPU = new PickUp(x, y, (Item) sword, "Sword");
                onLoad(swordPU);
                entity = swordPU;
                break;
            case "bow":
                Bow bow = new Bow(dungeon);
                PickUp bowPU = new PickUp(x, y, (Item) bow, "Bow");
                onLoad(bowPU);
                entity = bowPU;
                break;
            case "invincibility":
                Potion potion = new Potion(); 
                PickUp potionPU = new PickUp(x, y, (Item) potion, "Potion");
                onLoad(potionPU);
                entity = potionPU;
                break;
            case "key":
                int keyid = json.getInt("id");            
                Key key = new Key(keyid);
                PickUp keyPU = new PickUp(x, y, (Item) key, "Key");
                onLoad(keyPU);
                entity = keyPU;
                break;
            case "portal":
                int portid = json.getInt("id");            
                Portal portal = new Portal(x, y, portid);
                onLoad(portal);
                entity = portal;
                break;
            case "boulder":
                Boulder boulder = new Boulder(x, y);
                onLoad(boulder);
                entity = boulder;
                break;
            case "switch":
                Switch plate = new Switch(x, y);
                onLoad(plate);
                entity = plate;
                break;
            case "door":
                int doorid = json.getInt("id");
                Door door = new Door(x, y, doorid);
                onLoad(door);
                entity = door;
                break;
            case "exit":
                Exit exit = new Exit(x, y);
                onLoad(exit);
                entity = exit;
                break;
        }
        dungeon.addEntity(entity);
    }

    private void createAllQuests(Dungeon dungeon, JSONObject jsonGoalCondition) {
        // Finding type of quest
        String jsonGoal = jsonGoalCondition.getString("goal");

        // Creating array of quests
        ArrayList<Mission> missions = new ArrayList<Mission>();

        // Simple quest of simply arriving to exit
        Mission questExit = new ExitQuest(dungeon);
        missions.add(questExit);

        // Creating AND, OR goals
        JSONArray jsonQuests = null;
        switch (jsonGoal) {
            case "OR":
                jsonQuests = jsonGoalCondition.getJSONArray("subgoals");
                OrQuest OrQuest = new OrQuest(new ArrayList<Mission>());
                for (int i = 0; i < jsonQuests.length(); i++) {
                    JSONObject jsonSpecificQuest = (JSONObject) jsonQuests.get(i);
                    Mission additionalQuest = createQuest(jsonSpecificQuest.getString("goal"), dungeon);
                    OrQuest.addQuest(additionalQuest);
                }
                missions.add(OrQuest);
                break;
            case "AND":
                jsonQuests = jsonGoalCondition.getJSONArray("subgoals");
                AndQuest AndQuest = new AndQuest(new ArrayList<Mission>());
                for (int i = 0; i < jsonQuests.length(); i++) {
                    JSONObject jsonSpecificQuest = (JSONObject) jsonQuests.get(i);
                    Mission additionalQuest = createQuest(jsonSpecificQuest.getString("goal"), dungeon);
                    AndQuest.addQuest(additionalQuest);
                }
                missions.add(AndQuest);
                break;
            default:
                String quest = jsonGoalCondition.getString("goal");
                missions.add(createQuest(quest, dungeon));
        }

        AllQuests.createInstance(missions);
    }

    private Mission createQuest(String goal, Dungeon dungeon) {
        Mission mission = null;
        if (goal.equals("exit")) {
            mission = new ExitQuest(dungeon);
        } else if (goal.equals("treasure")) {
            mission = new TreasureQuest(dungeon);
        } else if (goal.equals("boulders")) {
            mission = new BouldersQuest(dungeon);
        } else if (goal.equals("enemies")) {
            mission = new EnemyQuest(dungeon);
        }

        return mission;
    }

    public abstract void onLoad(Player player);

    public abstract void onLoad(Wall wall);

    public abstract void onLoad(Gnome gnome);

    public abstract void onLoad(Hound hound);

    public abstract void onLoad(CamoGnome camoGnome);

    public abstract void onLoad(PickUp pickup);

    public abstract void onLoad(Exit exit);

    public abstract void onLoad(Door door);

    public abstract void onLoad(Portal portal);

    public abstract void onLoad(Boulder boulder);

    public abstract void onLoad(Switch plate);
}
