

public class Obstacles {
    private String name;
    private int size;
    private int damage;
    private int speed;

    public Obstacles(String name, int size, int damage, int speed) {
        this.name = name;
        this.size = size;
        this.damage = damage;
        this.speed = speed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    @Override
    public String toString() {
        return "Obstacles{" +
                "name='" + name + '\'' +
                ", size=" + size +
                ", damage=" + damage +
                ", speed=" + speed +
                '}';
    }
}
