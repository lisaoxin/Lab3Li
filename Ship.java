/**

 * Project: Lab 3
 * Purpose Details: Space Game
 * Course: IST 242
 * Author: Alvin Li
 * Date Developed: 06/06/2024
 * Last Date Changed: 06/09/2024
 * Rev: 06/09/2024

 */

// Ship Class
public class Ship {
    /**
     * The ship name
     */
    private String name;
    /**
     * The ship's speed
     */
    private int speed;
    /**
     * The ship's damage it deals
     */
    private int damage;
    /**
     * The ship's defense
     */
    private int defense;

    /**
     * Adds four numbers and returns the result.
     *
     * @param name This is the name of the ship.
     * @param speed This is the size of the bike frame.
     * @param damage This is the size of the bike frame.
     * @param defense This is the size of the bike frame.
     */
    public Ship(String name, int speed, int damage, int defense) {
        this.name = name;
        this.speed = speed;
        this.damage = damage;
        this.defense = defense;
    }
    // getters and setters
    public String toString() {
        return "Name: " + name + ", Speed: " + speed + ", Damage: " + damage + ", Defense: " + defense;
    }

}
