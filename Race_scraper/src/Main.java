import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        getRaces();
    }

    static void getRaces() {
        DataAccessLayer db = new DataAccessLayer();

        for (int i = 0; i < nat.length; i++) {

            final String url =
                    "https://firstcycling.com/race.php?y=" + year + "&t=2&m=01&nat=" + nat[i];

            try {
                final Document document = Jsoup.connect(url).get();

                for (Element row : document.select("table.sortTabell tr")) {
                    if (row.select("td:nth-of-type(1)").text().equals("")) {
                        continue;
                    } else if (row.select("td:nth-of-type(2)").text().contains(".1") ||
                               row.select("td:nth-of-type(2)").text().contains(".Pro") ||
                               row.select("td:nth-of-type(2)").text().contains(".UWT")) {

                        final String dateString = row.select("td:nth-of-type(1)").text();
                        final String category = row.select("td:nth-of-type(2)").text();
                        String name = row.select("td:nth-of-type(3)").text();
                        final String country = nation[i];

                        //bobby tables
                        if (name.contains("'")) {
                            name = name.replace("'", " ");
                        }

                        if (dateString.length() <= 5) { //one day race
                            int raceDays = 1;

                            String[] oneDayRaceDate = dateString.split("\\.");

                            int startingMonth = Integer.parseInt(oneDayRaceDate[1]) - 1;
                            int startingDay = Integer.parseInt(oneDayRaceDate[0]);
                            int startingYear = year - 1900;


                            Date date = new Date(startingYear, startingMonth, startingDay);
                            Race race = new Race(name, category, country, "One day race", date);
                            System.out.println(race);

                            db.addRace(race);

                        } else { //stage race

                            String[] outerSplit = dateString.split("\\.");
                            //outerSplit[0] = startingDay
                            //outerSplit[2] = endingMonth

                            String[] innerSplit = outerSplit[1].split("-");
                            //innerSplit[0] = startingMonth
                            //innerSplit[1] = endingDay

                            int raceYear = year - 1900;
                            int startingMonth = Integer.parseInt(innerSplit[0]) - 1;
                            int startingDay = Integer.parseInt(outerSplit[0]);

                            int endingMonth = Integer.parseInt(outerSplit[2]) - 1;
                            int endingDay = Integer.parseInt(innerSplit[1]);


                            Date startingDate = new Date(raceYear, startingMonth, startingDay);
                            Date endingDate = new Date(raceYear, endingMonth, endingDay);

                            Date stageDate;
                            final int oneDay = 1000 * 60 * 60 * 24; //one day in milliseconds

                            int raceDays = (int) ((endingDate.getTime() - startingDate.getTime())/oneDay + 1);


                            if (raceDays < 21) {

                                stageDate = new Date(startingDate.getTime());
                                for (int stage = 1; stage <= raceDays; stage++) {
                                    String classification = "Stage " + stage;
                                    Race race = new Race(name, category, country, classification, stageDate);
                                    System.out.println(race);

                                    db.addRace(race);

                                    stageDate = new Date(stageDate.getTime() + oneDay); //adds one day for each stage
                                }

                            } else {

                                //week 1 (stage 1 - stage 9)
                                stageDate = new Date(startingDate.getTime());
                                for (int stage = 1; stage <= 3; stage++) {
                                    String classification = "Stage " + stage;
                                    Race race = new Race(name, category, country, classification, stageDate);
                                    System.out.println(race);

                                    db.addRace(race);

                                    stageDate = new Date(stageDate.getTime() + oneDay);
                                }

                                //week 1 (stage 4 - stage 9)
                                stageDate = new Date(stageDate.getTime() + oneDay);
                                for (int stage = 4; stage <= 9; stage++) {
                                    String classification = "Stage " + stage;
                                    Race race = new Race(name, category, country, classification, stageDate);
                                    System.out.println(race);

                                    db.addRace(race);

                                    stageDate = new Date(stageDate.getTime() + oneDay);
                                }

                                //week 2 (stage 10 - stage 15)
                                stageDate = new Date(stageDate.getTime() + oneDay);
                                for (int stage = 10; stage <= 15; stage++) {
                                    String classification = "Stage " + stage;
                                    Race race = new Race(name, category, country, classification, stageDate);
                                    System.out.println(race);

                                    db.addRace(race);

                                    stageDate = new Date(stageDate.getTime() + oneDay);
                                }

                                //week 3 (stage 16 - stage 21)
                                stageDate = new Date(stageDate.getTime() + oneDay);
                                for (int stage = 16; stage <= 21; stage++) {
                                    String classification = "Stage " + stage;
                                    Race race = new Race(name, category, country, classification, stageDate);
                                    System.out.println(race);

                                    db.addRace(race);

                                    stageDate = new Date(stageDate.getTime() + oneDay);
                                }
                            }
                            Race GC = new Race(name, category, country, "GC", endingDate);
                            System.out.println(GC);

                            db.addRace(GC);

                            if (category.equals("2.UWT"))
                                addOtherClassifications(name, category, country, endingDate);
                        }
                    }
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void addOtherClassifications(String name, String category, String country, Date date) {
        DataAccessLayer db = new DataAccessLayer();

        Race Points = new Race(name, category, country, "Points", date);
        Race KOM = new Race(name, category, country, "KOM", date);
        Race Youth = new Race(name, category, country, "Youth", date);
        Race Teams = new Race(name, category, country, "Teams", date);

        System.out.println(Points);
        System.out.println(KOM);
        System.out.println(Youth);
        System.out.println(Teams);

        db.addRace(Points);
        db.addRace(KOM);
        db.addRace(Youth);
        db.addRace(Teams);
    }

    static final int year = 2023;
    static final int rankingYear = year - 1;

    static final String[] categoryName = {"UWT", "PRT", "CT"};

    static final String[] nat = {"ALB", "AND", "ARG", "AUS", "AUT", "BEL", "BUL", "CAN", "CHN", "COL",
                                 "CRO", "CZE", "DEN", "ECU", "EST", "FRA", "GAB", "GER", "GBR", "GRE",
                                 "HUN", "IRN", "ITA", "JPN", "KOS", "LUX", "MAS", "NED", "NZL", "NOR",
                                 "OMA", "POL", "POR", "ROM", "RWA", "KSA", "SBA", "SVK", "SLO", "RSA",
                                 "ESP", "SWE", "SUI", "SYR", "TPE", "THA", "TUR", "UAE", "USA", "VEN"};

    static final String[] nation = {"Albania", "Andorra", "Argentina", "Australia", "Austria", "Belgium", "Bulgaria", "Canada", "China", "Colombia",
                                    "Croatia", "Czech Republic", "Denmark", "Ecuador", "Estonia", "France", "Gabon", "Germany", "Great Britain", "Greece",
                                    "Hungary", "Iran", "Italy", "Japan", "Kosovo", "Luxembourg", "Malaysia", "Netherlands", "New Zealand", "Norway",
                                    "Oman", "Poland", "Portugal", "Romania", "Rwanda", "Saudi Arabia", "Serbia", "Slovakia", "Slovenia", "South Africa",
                                    "Spain", "Sweden", "Switzerland", "Syria", "Taiwan", "Thailand", "Turkey", "United Arab Emirates", "USA", "Venezuela"};

}