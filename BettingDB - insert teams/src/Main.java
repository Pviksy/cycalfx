import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        getRaces();
        getRiders();
    }

    static void getRiders() {
        DataAccessLayer db = new DataAccessLayer();

        for (int i = 1; i < categoryName.length+0; i++) {

            final String teamListURL =
                    "https://firstcycling.com/team.php?d=" + i + "&y=" + year;

            try {
                final Document teamList = Jsoup.connect(teamListURL).get();

                for (Element teamRow : teamList.select("table.sortTabell tr")) {
                    if (teamRow.select("td:nth-of-type(2)").text().equals("")) {
                        continue;
                    } else {
                        final String teamName = teamRow.select("td:nth-of-type(2)").text();
                        final String teamCountry = teamRow.select("td:nth-of-type(3)").text();
                        final String teamCategory = categoryName[i-1];

                        Team team = new Team(teamName, teamCountry, teamCategory);

                        db.addTeam(team);

                        System.out.println(team);


                        Elements links = teamRow.select("[href^=\"team.php\"]");
                        for (Element link : links) {

                            String teamPageURLid = link.attr("href");

                            final String teamPageURL =
                                    "https://firstcycling.com/" + teamPageURLid + "&riders=2";

                            try {
                                final Document teamPage = Jsoup.connect(teamPageURL).get();

                                for (Element riderRow : teamPage.select("table.sortTabell tr")) {
                                    if (riderRow.select("td:nth-of-type(1)").text().equals("")) {
                                        continue;
                                    } else if (riderRow.select("td").text().equals("No data")) {
                                        break;
                                    } else {

                                        final String riderName = riderRow.select("td:nth-of-type(1)").text();

                                        int riderAge = 0;
                                        try {
                                            riderAge = Integer.parseInt(riderRow.select("td:nth-of-type(3)").text());
                                        } catch (NumberFormatException e) {
                                            continue;
                                        }

                                        final String riderCountry = riderRow.select("td:nth-of-type(4)").text();

                                        String[] name = riderName.split(" ");

                                        String firstname;
                                        String lastname;

                                        if (name.length == 2) {
                                            firstname = name[1];
                                            lastname = name[0];
                                        } else if (name.length == 3) {
                                            firstname = name[name.length-1];
                                            lastname = name[0] + " " + name[1];
                                        } else {
                                            firstname = name[name.length-1];
                                            lastname = name[0] + " " + name[1] + " " + name[2];
                                        }

                                        Rider rider = new Rider(team.getTeam_id(), firstname, lastname, riderCountry, riderAge);

                                        db.addRider(rider);

                                        System.out.println(rider);
                                    }
                                }
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    static void getTeams() {
        DataAccessLayer db = new DataAccessLayer();

        for (int i = 1; i < categoryName.length+1; i++) {

            final String url =
                    "https://firstcycling.com/team.php?d=" + i + "&y=" + year;

            try {
                final Document document = Jsoup.connect(url).get();

                for (Element row : document.select("table.sortTabell tr")) {
                    if (row.select("td:nth-of-type(2)").text().equals("")) {
                        continue;
                    } else {
                        final String name = row.select("td:nth-of-type(2)").text();
                        final String country = row.select("td:nth-of-type(3)").text();
                        final String category = categoryName[i-1];

                        Team team = new Team(name, country, category);

                        db.addTeam(team);

                        System.out.println(team);
                    }
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    static void getRiderRanking() {

        final int pages = 3;

        for (int i = 1; i <= pages; i++) {

            final String url =
                    "https://firstcycling.com/ranking.php?k=fc&rank=&y=" + rankingYear + "&page=" + i;

            try {
                final Document document = Jsoup.connect(url).get();

                for (Element row : document.select("table.tablesorter tr")) {
                    if (row.select("td:nth-of-type(1)").text().equals("")) {
                        continue;
                    } else {
                        final String nameString = row.select("td:nth-of-type(2)").text();
                        final String yearOfBirth = row.select("td:nth-of-type(3)").text();
                        final String country = row.select("td:nth-of-type(4)").text();
                        final String team = row.select("td:nth-of-type(5)").text();

                        String[] name = nameString.split(" ");

                        String firstname;
                        String lastname;

                        if (name.length == 2) {
                            firstname = name[1];
                            lastname = name[0];
                        } else if (name.length == 3) {
                            firstname = name[name.length-1];
                            lastname = name[0] + " " + name[1];
                        } else {
                            firstname = name[name.length-1];
                            lastname = name[0] + " " + name[1] + " " + name[2];
                        }

                        int age = year - Integer.parseInt(yearOfBirth);

                        Rider rider = new Rider(firstname, lastname, team, country, age);
                        System.out.println(rider);
                    }
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }


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

    static final int year = 2021;
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