public class Team {

    private int team_id;
    private String name;
    private String category;
    private String country;


    public Team(int team_id, String name, String category, String country) {
        this.team_id = team_id;
        this.name = name;
        this.category = category;
        this.country = country;
    }

    public Team(String name, String country, String category) {
        this.name = name;
        this.country = country;
        this.category = category;
    }

    public int getTeam_id() {
        return team_id;
    }

    public void setTeam_id(int team_id) {
        this.team_id = team_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "[" + category + "] [" + country + "] [" + name + "]";
    }
}
