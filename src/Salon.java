public class Salon {
    private String nomSalon;
    private int uptime;

    public Salon(String nomSalon, int uptime) {
        this.nomSalon = nomSalon;
        this.uptime = uptime;
    }

    public String getNomSalon() {
        return nomSalon;
    }

    public void setNomSalon(String nomSalon) {
        this.nomSalon = nomSalon;
    }

    public int getUptime() {
        return uptime;
    }

    public void setUptime(int uptime) {
        this.uptime = uptime;
    }

    public void incrementer() {
        this.uptime++;
    }
    
    @Override
    public String toString() {
        return this.nomSalon + " (" + this.uptime + "s)";
    }

}
