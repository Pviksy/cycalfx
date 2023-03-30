public class Rider {

    private int rider_id;
    private int team_id;
    private String firstname;
    private String lastname;
    private String team;
    private String country;
    private int age;
    private String name;


    public Rider(int team_id, String firstname, String lastname, String country, int age) {
        this.team_id = team_id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.country = country;
        this.age = age;
    }

    public Rider(String firstname, String lastname, String team, String country, int age) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.team = team;
        this.country = country;
        this.age = age;
        name = firstname + " " + lastname;
    }

    public int getRider_id() {
        return rider_id;
    }

    public void setRider_id(int rider_id) {
        this.rider_id = rider_id;
    }

    public int getTeam_id() {
        return team_id;
    }

    public void setTeam_id(int team_id) {
        this.team_id = team_id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "[" + age + "] - " + team_id + " - " + country + " - " + firstname + " " + lastname;
    }

    //@Override
    //public String toString() {
    //    return "[" + age + "] - " + team + " - " + name;
    //}
}
