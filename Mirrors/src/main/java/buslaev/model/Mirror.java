package buslaev.model;

public class Mirror {
    private int id = 0;
    private boolean isSphere;
    private boolean isConcave;
    private int R;
    private int posX;
    private int posY;

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public Mirror() {
    }

    public Mirror(int id, boolean isSphere, Boolean isConcave, Integer r, int posX, int posY) {
        this.id = id;
        this.isSphere = isSphere;
        this.isConcave = isConcave;
        R = r;
        this.posX = posX;
        this.posY = posY;
    }

    public Mirror(boolean isSphere, Boolean isConcave, Integer r, int posX, int posY) {
        this.isSphere = isSphere;
        this.isConcave = isConcave;
        R = r;
        this.posX = posX;
        this.posY = posY;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isSphere() {
        return isSphere;
    }

    public void setSphere(boolean sphere) {
        isSphere = sphere;
    }

    public Boolean getConcave() {
        return isConcave;
    }

    public void setConcave(Boolean concave) {
        if(!isSphere)
            return;
        isConcave = concave;
    }

    public Integer getR() {
        return R;
    }

    public void setR(Integer r) {
        R = r;
    }

    public String toCSVString(String CSV_SEPARATOR){
        return isSphere +
                CSV_SEPARATOR +
                isConcave +
                CSV_SEPARATOR +
                R;
    }
}


