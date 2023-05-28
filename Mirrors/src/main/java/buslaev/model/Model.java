package buslaev.model;

import java.util.List;

public class Model {
    private int id = 0;
    private List<Mirror> mirrors;
    private int startX;
    private int startY;
    private int directionX;
    private int directionY;

    public Model() {
    }

    public Model(List<Mirror> mirrors, int startX, int startY, int directionX, int directionY) {
        this.mirrors = mirrors;
        this.startX = startX;
        this.startY = startY;
        this.directionX = directionX;
        this.directionY = directionY;
    }

    public int getStartX() {
        return startX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public int getStartY() {
        return startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public int getDirectionX() {
        return directionX;
    }

    public void setDirectionX(int directionX) {
        this.directionX = directionX;
    }

    public int getDirectionY() {
        return directionY;
    }

    public void setDirectionY(int directionY) {
        this.directionY = directionY;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Mirror> getMirrors() {
        return mirrors;
    }

    public void setMirrors(List<Mirror> mirrors) {
        this.mirrors = mirrors;
    }

    public String toCSVString(String CSV_SEPARATOR){
        StringBuilder stringBuffer = new StringBuilder();
        for (Mirror m :mirrors) {
            stringBuffer.append(m.toCSVString(CSV_SEPARATOR));
            stringBuffer.append("\n");
        }
        return stringBuffer.toString();
    }
}
