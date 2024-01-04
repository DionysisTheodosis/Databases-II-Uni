package com.icsd.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class DatabaseTransactions {

    private static DatabaseHandler databaseHandler;

    public static TopAttractions[] getTop5Attractions() {
        TopAttractions[] top = new TopAttractions[5];
        try {
            try {
                databaseHandler = new DatabaseHandler();
            } catch (RuntimeException ex) {
                databaseNotFound();
            }
            String sql = "SELECT a.attractionName, ROUND(AVG(r.rating), 1) AS avgRating, "
                    + "COUNT(r.rating) AS numReviews, p.picture_url AS mainPhoto "
                    + "FROM attraction a "
                    + "JOIN review r ON a.attractionName = r.attractionName "
                    + "JOIN picture p ON a.attr_profilePic = p.pictureID "
                    + "GROUP BY a.attractionName "
                    + "ORDER BY avgRating DESC, numReviews DESC "
                    + "LIMIT 5";
            PreparedStatement preparedStatement = databaseHandler.getConnection().prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            int i = 0;
            try {
                while (resultSet.next()) {
                    String attractionName = resultSet.getString("attractionName");
                    double avgRating = resultSet.getDouble("avgRating");
                    int numReviews = resultSet.getInt("numReviews");
                    String mainPhoto = resultSet.getString("mainPhoto");
                    top[i++] = new TopAttractions(attractionName, numReviews, avgRating, mainPhoto);
//                    System.out.println("Attraction: " + attractionName);
//                    System.out.println("Average Rating: " + avgRating);
//                    System.out.println("Number of Reviews: " + numReviews);
//                    System.out.println("Main Photo: " + mainPhoto);
//                    System.out.println("-------------------------");

                }
            } catch (SQLException e) {
                System.err.println("SQLException");
            } finally {
                databaseHandler.closeConnection();
            }
        } catch (SQLException | RuntimeException ex) {
            if (ex instanceof RuntimeException) {
                throw new RuntimeException(ex);
            }
            System.err.println(ex.getMessage());
        }
        return top;

    }

    public static Attraction getAttraction(String attractionName) {
        Attraction attraction = new Attraction();
        ArrayList<PicturesAttrraction> pic = new ArrayList<>();
        ArrayList<OperationTime> operation = new ArrayList<>();
        ArrayList<String> emails = new ArrayList<>();
        ArrayList<String> telephones = new ArrayList<>();
        ArrayList<AttractionReviews> attrReviews = new ArrayList<>();
        String sql;
        try {
            try {
                databaseHandler = new DatabaseHandler();
            } catch (RuntimeException ex) {
                databaseNotFound();
            }
            sql = "SELECT "
                    + "    a.attractionName, "
                    + "    a.entrance_fee, "
                    + "    a.website, "
                    + "    a.attractionType, "
                    + "    a.areaName, "
                    + "    rg.regionName, "
                    + "    c.countryName, "
                    + "    ROUND(AVG(r.rating), 1) AS avgRating, "
                    + "    COUNT(r.rating) AS numReviews, "
                    + "    p.picture_url AS mainPhoto, "
                    + "    m.embeded_Url AS mapIframe "
                    + "FROM "
                    + "    attraction a "
                    + " LEFT JOIN "
                    + "    review r ON a.attractionName = r.attractionName "
                    + "JOIN "
                    + "    picture p ON a.attr_profilePic = p.pictureID "
                    + "JOIN "
                    + "    area ar ON a.areaName = ar.areaName "
                    + "JOIN "
                    + "    region rg ON ar.regionName = rg.regionName "
                    + "JOIN "
                    + "    country c ON rg.countryName = c.countryName "
                    + "JOIN "
                    + "    attraction_type at ON a.attractionType = at.typeName "
                    + "JOIN "
                    + "	map m ON a.attractionName = m.attractionName "
                    + "WHERE "
                    + "    a.attractionName = ? "
                    + "GROUP BY "
                    + "    a.attractionName, "
                    + "    m.embeded_Url "
                    + "ORDER BY "
                    + "    avgRating DESC, numReviews DESC; ";

            PreparedStatement preparedStatement = databaseHandler.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, attractionName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                attraction.setAttractionName(resultSet.getString("attractionName"));
                attraction.setEntranceFee(resultSet.getDouble("entrance_fee"));
                attraction.setWebsite(resultSet.getString("website"));
                attraction.setAttr_ProfilePic(resultSet.getString("mainPhoto"));
                attraction.setAttraction_type(resultSet.getString("attractionType"));
                attraction.setMap(resultSet.getString("mapIframe"));
                attraction.setPlace(resultSet.getString("countryName") + " , " + resultSet.getString("regionName")
                        + " , " + resultSet.getString("areaName"));
                attraction.setNumberOfReviews(resultSet.getInt("numReviews"));
                attraction.setAverageRating(resultSet.getDouble("avgRating"));
//                System.out.println("Attraction Name: " + attraction.getAttractionName());
//                System.out.println("Entrance Fee: " + attraction.getEntranceFee());
//                System.out.println("Attraction Type: " + attraction.getAttraction_type());
//                System.out.println("Website: " + attraction.getWebsite());
//                System.out.println("Profile Picture: " + attraction.getAttr_ProfilePic());
//                System.out.println("Map: " + attraction.getMap());
//                System.out.println("Avg Rating: " + attraction.getAverageRating());
//                System.out.println("Number Of Reviews: " + attraction.getNumberOfReviews());
//                System.out.println("Place: " + attraction.getPlace());
            }
        } catch (SQLException e) {
            System.err.println("SQLException");
        }

        try {

            sql = "SELECT picture_url ,username AS uploadFrom "
                    + "FROM picture "
                    + "WHERE (attractionName, username) IN ( "
                    + "SELECT attractionName, username "
                    + "FROM review "
                    + "WHERE attractionName = ? "
                    + ");";
            PreparedStatement preparedStatement = databaseHandler.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, attractionName);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                pic.add(new PicturesAttrraction(resultSet.getString("picture_url"), resultSet.getString("uploadFrom")));
//                System.out.println(pic.get(pic.size() - 1).getPictureUrl() + pic.get(pic.size() - 1).getUsername());

            }
            attraction.setAllPictures(pic.toArray(PicturesAttrraction[]::new));

        } catch (SQLException e) {
            System.err.println("SQLException");
        }

        try {

            sql = "SELECT "
                    + "   o.open AS opensAt, "
                    + "   o.close AS closingAt, "
                    + "   o.dayName AS day "
                    + "FROM attraction a "
                    + "JOIN operation_time o ON a.attractionName = o.attractionName "
                    + "WHERE (a.attractionName = ?)";

            PreparedStatement preparedStatement = databaseHandler.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, attractionName);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                operation.add(new OperationTime(resultSet.getString("day"), resultSet.getString("opensAt"),
                        resultSet.getString("closingAt")));
//                System.out.println(
//                        operation.get(operation.size() - 1).getDay() + operation.get(operation.size() - 1).getStarts()
//                        + operation.get(operation.size() - 1).getEnds());

            }

            attraction.setOperationTime(operation.toArray(OperationTime[]::new));
        } catch (SQLException e) {
            System.err.println("SQLException");
        }

        try {

            sql = "SELECT e.email "
                    + "FROM attraction a "
                    + "JOIN email_attraction e ON a.attractionName = e.attractionName "
                    + "WHERE (a.attractionName = ?)";

            PreparedStatement preparedStatement = databaseHandler.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, attractionName);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                emails.add(resultSet.getString("email"));
//                System.out.println(emails.get(emails.size() - 1));

            }

            attraction.setEmail(emails.toArray(String[]::new));
        } catch (SQLException e) {
            System.err.println("SQLException");
        }
        try {
            sql = "SELECT telephone "
                    + "FROM telephone_attraction "
                    + "WHERE attractionName = ( "
                    + "    SELECT attractionName "
                    + "    FROM attraction "
                    + "    WHERE attractionName = ?)";

            PreparedStatement preparedStatement = databaseHandler.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, attractionName);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                telephones.add(resultSet.getString("telephone"));
//                System.out.println(telephones.get(telephones.size() - 1));

            }

            attraction.setTelephone(telephones.toArray(String[]::new));
        } catch (SQLException e) {
            System.err.println("SQLException");
        }

        try {

            sql = "SELECT r.*, p.picture_url "
                    + "FROM attraction a "
                    + "JOIN review r ON a.attractionName = r.attractionName "
                    + "JOIN user u ON u.username = r.username "
                    + "JOIN picture p ON p.pictureID = u.userProfilePic "
                    + "WHERE r.attractionName = ?";
            PreparedStatement preparedStatement = databaseHandler.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, attractionName);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                attrReviews.add(new AttractionReviews(resultSet.getString("attractionName"),
                        resultSet.getString("createdAt"), resultSet.getString("comment"), resultSet.getDouble("rating"),
                        resultSet.getString("username"), resultSet.getString("picture_url")));
//                System.out.println(attrReviews.get(attrReviews.size() - 1).getAttractionName()
//                        + attrReviews.get(attrReviews.size() - 1).getComment()
//                        + attrReviews.get(attrReviews.size() - 1).getRating()
//                        + attrReviews.get(attrReviews.size() - 1).getCreationTime()
//                        + attrReviews.get(attrReviews.size() - 1).getUsername()
//                        + attrReviews.get(attrReviews.size() - 1).getUserPicture());

            }

            attraction.setAttrReviews(attrReviews.toArray(AttractionReviews[]::new));
        } catch (SQLException e) {
            System.err.println("SQLException");
        } finally {
            databaseHandler.closeConnection();
        }
        return attraction;
    }

    public static String[] getReviewPictures(String attractionName, String username) {
        String sql = "SELECT picture_url "
                + "FROM review r "
                + "JOIN picture p ON r.username = p.username AND r.attractionName = p.attractionName "
                + "WHERE r.username = ? AND r.attractionName = ?;";
        ArrayList<String> pictures = new ArrayList<>();
        String[] reviewPictures;
        try {
            try {
                databaseHandler = new DatabaseHandler();
            } catch (RuntimeException ex) {
                databaseNotFound();
            }
            PreparedStatement preparedStatement = databaseHandler.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, attractionName);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    pictures.add(resultSet.getString("picture_url"));

                }
                reviewPictures = pictures.toArray(String[]::new);
            }
            return reviewPictures;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        } finally {
            databaseHandler.closeConnection();
        }
    }

    public static Attraction_Type[] getAttractionTypes() {
        try {
            databaseHandler = new DatabaseHandler();
        } catch (RuntimeException ex) {
            databaseNotFound();
        }
        String sql = "SELECT DISTINCT at.typeName, p.picture_url "
                + "FROM attraction_type at "
                + "JOIN attraction a ON at.typeName = a.attractionType "
                + "JOIN picture p ON at.attrTypeImage = p.pictureID; ";
        ArrayList<Attraction_Type> attrTypes = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = databaseHandler.getConnection().prepareStatement(sql);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    attrTypes.add(
                            new Attraction_Type(resultSet.getString("typeName"), resultSet.getString("picture_url")));
//                    System.out.println(
//                            "attra######## " + attrTypes.get(i).getName() + " , " + attrTypes.get(i++).getPicture());
                }
                return attrTypes.toArray(Attraction_Type[]::new);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        } finally {
            databaseHandler.closeConnection();
        }
    }

    public static String[] getCountries() {
        try {
            databaseHandler = new DatabaseHandler();
        } catch (RuntimeException ex) {
            databaseNotFound();
        }
        String sql = "SELECT DISTINCT c.countryName "
                + "FROM attraction a "
                + "JOIN region r ON a.areaName = r.regionName "
                + "JOIN country c ON r.countryName = c.countryName; ";
        ArrayList<String> countryNames = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = databaseHandler.getConnection().prepareStatement(sql);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    countryNames.add(resultSet.getString("countryName"));
//                    System.out.println("attra######## " + countryNames.get(i++));
                }
                return countryNames.toArray(String[]::new);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());

            return null;
        } finally {
            databaseHandler.closeConnection();
        }
    }

    public static String[] getRegions() {
        try {
            databaseHandler = new DatabaseHandler();
        } catch (RuntimeException ex) {
            databaseNotFound();
        }
        String sql = "SELECT DISTINCT r.regionName "
                + "FROM attraction a "
                + "JOIN area ar ON a.areaName = ar.areaName "
                + "JOIN region r ON ar.regionName = r.regionName; ";
        ArrayList<String> regionNames = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = databaseHandler.getConnection().prepareStatement(sql);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    regionNames.add(resultSet.getString("regionName"));
//                    System.out.println("attra######## " + regionNames.get(i++));
                }
                return regionNames.toArray(String[]::new);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        } finally {
            databaseHandler.closeConnection();
        }
    }

    public static String[] getAreas() {
        try {
            databaseHandler = new DatabaseHandler();
        } catch (RuntimeException ex) {
            databaseNotFound();
        }
        String sql = "SELECT DISTINCT a.areaName "
                + "FROM attraction a; ";
        ArrayList<String> areaNames = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = databaseHandler.getConnection().prepareStatement(sql);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    areaNames.add(resultSet.getString("areaName"));
//                    System.out.println("attra######## " + areaNames.get(i++));
                }
                return areaNames.toArray(String[]::new);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        } finally {
            databaseHandler.closeConnection();
        }
    }

    public static Attraction[] getAttractionsBsdCountry(String countryName) {
        try {
            databaseHandler = new DatabaseHandler();
        } catch (RuntimeException ex) {
            databaseNotFound();
        }
        String sql = "SELECT "
                + " a.attractionName, "
                + "ROUND(AVG(r.rating), 1) AS avgRating, "
                + "COUNT(r.rating) AS numReviews, "
                + "p.picture_url AS mainPhoto "
                + "FROM "
                + "attraction a "
                + "LEFT JOIN "
                + "review r ON a.attractionName = r.attractionName "
                + "JOIN "
                + "picture p ON a.attr_profilePic = p.pictureID "
                + "JOIN "
                + "area ar ON a.areaName = ar.areaName "
                + "JOIN "
                + "region rg ON ar.regionName = rg.regionName "
                + "JOIN "
                + "country c ON rg.countryName = c.countryName "
                + "WHERE "
                + "c.countryName = ? "
                + "GROUP BY "
                + "a.attractionName "
                + "ORDER BY "
                + "avgRating DESC, numReviews DESC; ";
        ArrayList<Attraction> attractions = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = databaseHandler.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, countryName);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Attraction attraction = new Attraction();

                    attraction.setNumberOfReviews(resultSet.getInt("numReviews"));
                    attraction.setAttractionName(resultSet.getString("attractionName"));
                    attraction.setAverageRating(resultSet.getDouble("avgRating"));
                    attraction.setAttr_ProfilePic(resultSet.getString("mainPhoto"));
//                    System.out.println("Attraction: " +  attraction.getAttractionName());
//                    System.out.println("Average Rating: " + attraction.getAverageRating());
//                    System.out.println("Number of Reviews: " + attraction.getNumberOfReviews());
//                    System.out.println("Main Photo: " + attraction.getAttr_ProfilePic());
//                    System.out.println("-------------------------");
                    attractions.add(attraction);
                }
                return attractions.toArray(Attraction[]::new);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        } finally {
            databaseHandler.closeConnection();
        }
    }

    public static Attraction[] getAttractionsBsdRegion(String regionName) {
        try {
            databaseHandler = new DatabaseHandler();
        } catch (RuntimeException ex) {
            databaseNotFound();
        }
        String sql = "SELECT "
                + "a.attractionName, "
                + "ROUND(AVG(r.rating), 2) AS avgRating, "
                + "COUNT(r.rating) AS numReviews, "
                + "p.picture_url AS mainPhoto "
                + "FROM "
                + "attraction a "
                + "LEFT JOIN "
                + "review r ON a.attractionName = r.attractionName "
                + "JOIN "
                + "picture p ON a.attr_profilePic = p.pictureID "
                + "JOIN "
                + "area ar ON a.areaName = ar.areaName "
                + "JOIN "
                + "region rg ON ar.regionName = rg.regionName "
                + "WHERE "
                + "rg.regionName = ? "
                + "GROUP BY "
                + "a.attractionName "
                + "ORDER BY "
                + "avgRating DESC, numReviews DESC; ";
        ArrayList<Attraction> attractions = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = databaseHandler.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, regionName);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Attraction attraction = new Attraction();

                    attraction.setNumberOfReviews(resultSet.getInt("numReviews"));
                    attraction.setAttractionName(resultSet.getString("attractionName"));
                    attraction.setAverageRating(resultSet.getDouble("avgRating"));
                    attraction.setAttr_ProfilePic(resultSet.getString("mainPhoto"));
//                    System.out.println("Attraction: " +  attraction.getAttractionName());
//                    System.out.println("Average Rating: " + attraction.getAverageRating());
//                    System.out.println("Number of Reviews: " + attraction.getNumberOfReviews());
//                    System.out.println("Main Photo: " + attraction.getAttr_ProfilePic());
//                    System.out.println("-------------------------");
                    attractions.add(attraction);
                }
                return attractions.toArray(Attraction[]::new);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        } finally {
            databaseHandler.closeConnection();
        }
    }

    public static Attraction[] getAttractionsBsdArea(String areaName) {
        try {
            databaseHandler = new DatabaseHandler();
        } catch (RuntimeException ex) {
            databaseNotFound();
        }
        String sql = "SELECT "
                + "a.attractionName, "
                + "ROUND(AVG(r.rating), 1) AS avgRating, "
                + "COUNT(r.rating) AS numReviews, "
                + "p.picture_url AS mainPhoto "
                + "FROM "
                + "attraction a "
                + "LEFT JOIN "
                + "review r ON a.attractionName = r.attractionName "
                + "JOIN "
                + "picture p ON a.attr_profilePic = p.pictureID "
                + "WHERE "
                + "a.areaName = ? "
                + "GROUP BY "
                + "a.attractionName "
                + "ORDER BY "
                + "avgRating DESC, numReviews DESC; ";
        ArrayList<Attraction> attractions = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = databaseHandler.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, areaName);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Attraction attraction = new Attraction();

                    attraction.setNumberOfReviews(resultSet.getInt("numReviews"));
                    attraction.setAttractionName(resultSet.getString("attractionName"));
                    attraction.setAverageRating(resultSet.getDouble("avgRating"));
                    attraction.setAttr_ProfilePic(resultSet.getString("mainPhoto"));
                    System.out.println("Attraction: " + attraction.getAttractionName());
                    System.out.println("Average Rating: " + attraction.getAverageRating());
                    System.out.println("Number of Reviews: " + attraction.getNumberOfReviews());
                    System.out.println("Main Photo: " + attraction.getAttr_ProfilePic());
                    System.out.println("-------------------------");
                    attractions.add(attraction);
                }
                return attractions.toArray(Attraction[]::new);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        } finally {
            databaseHandler.closeConnection();
        }
    }

    public static Attraction[] getAttractionsBsdType(String attraction_type) {
        try {
            databaseHandler = new DatabaseHandler();
        } catch (RuntimeException ex) {
            databaseNotFound();
        }
        String sql = "SELECT "
                + "a.attractionName, "
                + "ROUND(AVG(r.rating), 1) AS avgRating, "
                + "COUNT(r.rating) AS numReviews, "
                + "p.picture_url AS mainPhoto "
                + "FROM "
                + "attraction a "
                + "LEFT JOIN "
                + "review r ON a.attractionName = r.attractionName "
                + "JOIN "
                + "picture p ON a.attr_profilePic = p.pictureID "
                + "WHERE "
                + "a.attractionType =  ? "
                + "GROUP BY "
                + "a.attractionName "
                + "ORDER BY "
                + "avgRating DESC, numReviews DESC; ";

        ArrayList<Attraction> attractions = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = databaseHandler.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, attraction_type);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Attraction attraction = new Attraction();

                    attraction.setNumberOfReviews(resultSet.getInt("numReviews"));
                    attraction.setAttractionName(resultSet.getString("attractionName"));
                    attraction.setAverageRating(resultSet.getDouble("avgRating"));
                    attraction.setAttr_ProfilePic(resultSet.getString("mainPhoto"));
//                    System.out.println("Attraction: " +  attraction.getAttractionName());
//                    System.out.println("Average Rating: " + attraction.getAverageRating());
//                    System.out.println("Number of Reviews: " + attraction.getNumberOfReviews());
//                    System.out.println("Main Photo: " + attraction.getAttr_ProfilePic());
//                    System.out.println("-------------------------");
                    attractions.add(attraction);
                }
                return attractions.toArray(Attraction[]::new);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        } finally {
            databaseHandler.closeConnection();
        }
    }

    public static Attraction[] getAttractionsBsdFreeEnFee() {
        try {
            databaseHandler = new DatabaseHandler();
        } catch (RuntimeException ex) {
            databaseNotFound();
        }
        String sql = "SELECT "
                + "a.attractionName, "
                + "a.entrance_fee, "
                + "ROUND(AVG(r.rating), 1) AS avgRating, "
                + "COUNT(r.rating) AS numReviews, "
                + "p.picture_url AS mainPhoto "
                + "FROM "
                + "attraction a "
                + "LEFT JOIN "
                + "review r ON a.attractionName = r.attractionName "
                + "JOIN "
                + "picture p ON a.attr_profilePic = p.pictureID "
                + "WHERE "
                + "a.entrance_fee = 0 "
                + "GROUP BY "
                + "a.attractionName "
                + "ORDER BY "
                + "avgRating DESC, numReviews DESC; ";
        ArrayList<Attraction> attractions = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = databaseHandler.getConnection().prepareStatement(sql);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Attraction attraction = new Attraction();

                    attraction.setNumberOfReviews(resultSet.getInt("numReviews"));
                    attraction.setAttractionName(resultSet.getString("attractionName"));
                    attraction.setAverageRating(resultSet.getDouble("avgRating"));
                    attraction.setAttr_ProfilePic(resultSet.getString("mainPhoto"));
//                    System.out.println("Attraction: " +  attraction.getAttractionName());
//                    System.out.println("Average Rating: " + attraction.getAverageRating());
//                    System.out.println("Number of Reviews: " + attraction.getNumberOfReviews());
//                    System.out.println("Main Photo: " + attraction.getAttr_ProfilePic());
//                    System.out.println("-------------------------");
                    attractions.add(attraction);
                }
                return attractions.toArray(Attraction[]::new);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        } finally {
            databaseHandler.closeConnection();
        }
    }

    public static Attraction[] getAttractionsBsdEnFee(Double entrance_fee) {
        try {
            databaseHandler = new DatabaseHandler();
        } catch (RuntimeException ex) {
            databaseNotFound();
        }
        String sql = "SELECT "
                + "a.attractionName, "
                + "a.entrance_fee, "
                + "ROUND(AVG(r.rating), 1) AS avgRating, "
                + "COUNT(r.rating) AS numReviews, "
                + "p.picture_url AS mainPhoto "
                + "FROM "
                + "attraction a "
                + "LEFT JOIN "
                + "review r ON a.attractionName = r.attractionName "
                + "JOIN "
                + "picture p ON a.attr_profilePic = p.pictureID "
                + "WHERE "
                + "a.entrance_fee <= ? "
                + "GROUP BY "
                + "a.attractionName "
                + "ORDER BY "
                + "avgRating DESC, numReviews DESC; ";
        ArrayList<Attraction> attractions = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = databaseHandler.getConnection().prepareStatement(sql);
            preparedStatement.setDouble(1, entrance_fee);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Attraction attraction = new Attraction();

                    attraction.setNumberOfReviews(resultSet.getInt("numReviews"));
                    attraction.setAttractionName(resultSet.getString("attractionName"));
                    attraction.setAverageRating(resultSet.getDouble("avgRating"));
                    attraction.setAttr_ProfilePic(resultSet.getString("mainPhoto"));
//                    System.out.println("Attraction: " +  attraction.getAttractionName());
//                    System.out.println("Average Rating: " + attraction.getAverageRating());
//                    System.out.println("Number of Reviews: " + attraction.getNumberOfReviews());
//                    System.out.println("Main Photo: " + attraction.getAttr_ProfilePic());
//                    System.out.println("-------------------------");
                    attractions.add(attraction);
                }
                return attractions.toArray(Attraction[]::new);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        } finally {
            databaseHandler.closeConnection();
        }
    }
    public static Attraction[] getAllAttractions() {
        try {
            databaseHandler = new DatabaseHandler();
        } catch (RuntimeException ex) {
            databaseNotFound();
        }
        String sql = "SELECT "
                + "a.attractionName, "
                + "ROUND(AVG(r.rating), 1) AS avgRating, "
                + "COUNT(r.rating) AS numReviews, "
                + "p.picture_url AS mainPhoto "
                + "FROM "
                + "attraction a "
                + "LEFT JOIN "
                + "review r ON a.attractionName = r.attractionName "
                + "JOIN "
                + "picture p ON a.attr_profilePic = p.pictureID "
                + "GROUP BY "
                + "a.attractionName "
                + "ORDER BY "
                + "avgRating DESC, numReviews DESC; ";
        ArrayList<Attraction> attractions = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = databaseHandler.getConnection().prepareStatement(sql);
           
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Attraction attraction = new Attraction();
                    attraction.setNumberOfReviews(resultSet.getInt("numReviews"));
                    attraction.setAttractionName(resultSet.getString("attractionName"));
                    attraction.setAverageRating(resultSet.getDouble("avgRating"));
                    attraction.setAttr_ProfilePic(resultSet.getString("mainPhoto"));
                    System.out.println("Attraction: " + attraction.getAttractionName());
                    System.out.println("Average Rating: " + attraction.getAverageRating());
                    System.out.println("Number of Reviews: " + attraction.getNumberOfReviews());
                    System.out.println("Main Photo: " + attraction.getAttr_ProfilePic());
                    System.out.println("-------------------------");
                    attractions.add(attraction);
                }
                return attractions.toArray(Attraction[]::new);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        } finally {
            databaseHandler.closeConnection();
        }
    }

    public static User getUser(String username) {
        try {
            databaseHandler = new DatabaseHandler();
        } catch (RuntimeException ex) {
            databaseNotFound();
        }
        User user = new User();
        String sql = "SELECT "
                + "u.username, "
                + "u.surname, "
                + "u.lastname, "
                + "p.picture_url "
                + "FROM user u "
                + "LEFT JOIN picture p ON u.userProfilePic = p.pictureID "
                + "WHERE ( u.username=?); ";

        try {
            PreparedStatement preparedStatement = databaseHandler.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, username);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {

                    user.setUsername(resultSet.getString("username"));
                    user.setName(resultSet.getString("surname"));
                    user.setLastname(resultSet.getString("lastname"));
                    user.setProfilePic(resultSet.getString("picture_url"));
//                    System.out.println("Username: " +  user.getUsername());
//                    System.out.println("Name: " + user.getName());
//                    System.out.println("Last Name: " + user.getLastname());
//                    System.out.println("Profile Photo: " + user.getProfilePic());
//                    System.out.println("-------------------------");

                }
                return user;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        } finally {
            databaseHandler.closeConnection();
        }
    }

    public static Review[] getReviewsBsdUser(String username) {
        try {
            databaseHandler = new DatabaseHandler();
        } catch (RuntimeException ex) {
            databaseNotFound();
        }
        String sql = "SELECT "
                + "r.attractionName, "
                + "r.rating, "
                + "r.comment, "
                + "r.createdAt "
                + "FROM user u "
                + "JOIN review r ON u.username = r.username "
                + "WHERE (u.username = ?); ";
        ArrayList<Review> reviews = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = databaseHandler.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, username);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Review review = new Review();

                    review.setAttractionNameS(resultSet.getString("attractionName"));
                    review.setRating(resultSet.getDouble("rating"));
                    review.setComment(resultSet.getString("comment"));
                    review.setCreationTime(resultSet.getString("createdAt"));
                    review.setUsernameS(username);
//                    System.out.println("Attraction: " +  review.getAttractionNameS());
//                    System.out.println("Rating: " + review.getRating());
//                    System.out.println("Comment : " + review.getComment());
//                    System.out.println("Created At : " + review.getCreationTime());
//                    System.out.println("-------------------------");
                    reviews.add(review);
                }
                return reviews.toArray(Review[]::new);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        } finally {
            databaseHandler.closeConnection();
        }
    }

    private static void databaseNotFound() {
        Alert notFoundAlert = new Alert(Alert.AlertType.ERROR);
        notFoundAlert.getButtonTypes().setAll(ButtonType.OK);
        notFoundAlert.setTitle("Δεν βρέθηκε η βάση δεδομένων");
        notFoundAlert.setContentText("Παρακαλώ βεβαιωθείτε ότι η βάση δεδομένων είναι στον ίδιο φάκελο με το εκτελούμενο αρχείο και ξαναπροσπαθήστε");
        notFoundAlert.setOnCloseRequest(eve -> {
            notFoundAlert.hide();
        });
        notFoundAlert.showAndWait();
    }
    

}
