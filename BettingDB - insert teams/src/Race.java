import java.text.SimpleDateFormat;
import java.util.Date;

public class Race {

    private int race_id;
    private String name;
    private String category;
    private String country;
    private String classification;
    private Date date;

    public Race(String name, String category, String country, String classification, Date date) {
        this.name = name;
        this.category = category;
        this.country = country;
        this.classification = classification;
        this.date = date;
    }

    public void setRace_id(int race_id) {
        this.race_id = race_id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getCountry() {
        return country;
    }

    public String getClassification() {
        return classification;
    }


    //date format
    SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd");

    public String getDate() {
        return newFormat.format(date);
    }



    @Override
    public String toString() {
        return newFormat.format(date) + " - " + category + " - " + country + " - " + name + " - " + classification;
    }
}
