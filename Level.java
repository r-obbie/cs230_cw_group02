/*
 * Level.java
 * A subclass of Agent that can send and receive encrypted messages
 * Author: Robbie Jones
 * Date created: 13/11/25
 * Date last modified: 13/11/25
 * Version: 0.1
 * Version history:
 * 0.1 - Initial version
 * 0.2 - Added update method
 */
public class Level {
    // Attributes
    private Tile tiles[][];
    private int height;
    private int width;
    private int timeRemaining;
    private int levelNumber;
    private Player player;
    private NPC npcs[];
    private Item items[];

    // Constructors
    public Level(Tile tiles[][], int time, int levelNumber, Player player, NPC npcs[], Item items[]) {
        this.tiles = tiles;
        this.height = tiles.length;
        this.width = tiles[0].length;
        this.timeRemaining = time;
        this.levelNumber = levelNumber;
        this.player = player;
        this.npcs = npcs;
        this.items = items;
    }

    // Methods
    /**
     *  Checks if the given position is valid within the level boundaries
     * @param x The x coordinate
     * @param y The y coordinate
     * @return true if the position is valid, false otherwise
     */
    public boolean isValidPosition(int x, int y) {
        return (x >= 0 && x < width && y >= 0 && y < height);
    }

    /**
     * Removes a character from the level
     * @param character The character to remove
     */
    public void removeCharacter(Character character) {
        if (character instanceof Player) {
            player = null;
        } else if (character instanceof NPC) {
            for (int i = 0; i < npcs.length; i++) {
                if (npcs[i] == character) {
                    npcs[i] = null;
                }
            }
        }
    }

    /**
     * Removes an item from the level
     * @param item The item to remove
     */
    public void removeItem(Item item) {
        for (int i = 0; i < items.length; i++) {
            if (items[i] == item) {
                items[i] = null;
            }
        }
    }

    /**
     * Updates the level every tick
     * @return The current game state
     */
    public GameState update() {
        // to be implemented
    }

    /**    
     * Checks if all loot has been collected
     * @return true if all loot has been collected, false otherwise
     */
    public boolean allLootCollected() {
        for (Item item : items) {
            if (item.isCollectable() && item != null) {
                return false;
            }
        }
        return true;
    }

    /**
     * Gets the next tile in the specified direction according to tile colour matching rules
     * @param curr The current tile
     * @param dir The direction to move
     * @return The next tile in the specified direction, or null if no valid tile is found
     */
    public Tile getNextTileInDirection(Tile curr, Direction dir) {
        int x = curr.getX();
        int y = curr.getY();
        boolean foundTile = false;

        while (!foundTile) {
            switch (dir) {
                case Direction.UP:
                    y -= 1;
                    break;
                case Direction.DOWN:
                    y += 1;
                    break;
                case Direction.LEFT:
                    x -= 1;
                    break;
                case Direction.RIGHT:
                    x += 1;
                    break;
            }

            Tile nextTile = getTileAt(x, y);
            if (nextTile == null || nextTile.isOccupied()) {
                return null; 
            }
            if (nextTile.sharesColourWith(curr)) {
                foundTile = true;
            }
            else {
                curr = nextTile;
            }
        }
        return curr;
    }

    /**
     * Gets the tile at the specified coordinates
     * @param x The x coordinate
     * @param y The y coordinate
     * @return The tile at the specified coordinates, or null if out of bounds
     */
    public Tile getTileAt(int x, int y) {
        if (isValidPosition(x, y)) {
            return tiles[y][x];
        }
        return null;
    }

    /**
     * Gets the time remaining in the level
     * @return The time remaining
     */
    public int getTimeRemaining() {
        return timeRemaining;
    }

    /**
     * Gets the list of all collectable items in the level
     * @return A list of collectable items
     */
    public List<Item> getCollectableItems() {
        List<Item> collectables = new ArrayList<>();
        for (Item item : items) {
            if (item != null && item.isCollectable()) {
                collectables.add(item);
            }
        }
        return collectables;
    }
}
