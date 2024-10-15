
package practica_ud1;

import java.io.Serializable;

public class Jugador implements Serializable{
    
    private int id, experience, life_level, coins;
    private String nick_name;
    
    public Jugador(String nick_name, int life_level, int coins, int experience){
        this.id = 0;
        this.nick_name = nick_name;
        this.life_level = life_level;
        this.coins = coins;
        this.experience = experience;
    }
    public Jugador(){
        this.id = 0;
        this.nick_name = "";
        this.life_level = 100;
        this.coins = 0;
        this.experience = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getLife_level() {
        return life_level;
    }

    public void setLife_level(int life_level) {
        this.life_level = life_level;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Jugador other = (Jugador) obj;
        return this.id == other.id;
    }

    @Override
    public String toString() {
        return this.getId() + ";" + this.getNick_name() + ";" + this.getExperience() + ";" + this.getLife_level() + ";" + this.getCoins();
    }
}
