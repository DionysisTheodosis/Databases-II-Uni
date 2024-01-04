package com.icsd.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import com.icsd.dao.*;
import com.icsd.util.ImageUtils;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class MainFxmlController {
    
    @FXML
    private ImageView backgroundImage;
    @FXML
    private ImageView logoImage;
    @FXML
    private ScrollPane mainScrollPane;
    @FXML
    private ImageView menuId;
    @FXML
    private ImageView logoutId;
    @FXML
    private ImageView searchId;
    @FXML
    private ImageView homeId;
    @FXML
    private HBox topFVHBox;
    @FXML
    private VBox buttonMenuSearch;
    @FXML
    private VBox buttonMenuHome;
    @FXML
    private VBox buttonMenuExit;
    @FXML
    private ScrollPane attractionScrollPane;
    @FXML
    private ScrollPane resultScrollPane;
    @FXML
    private StackPane stackPane;
    
    @FXML
    private ComboBox<String> searchCmbo;
    
    @FXML
    private ComboBox<String> typeOfCmbo;
    
    @FXML
    private ImageView searchButton;
    
    @FXML
    private HBox attrTypeHbox;
    
    @FXML
    private AnchorPane searchLogoAp;
    
    @FXML
    private AnchorPane attrTypeAnchorP;
    
    private boolean flag;
    
    @FXML
    void initialize() throws IOException {
        
        flag = false;
        
        menuId.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                flag = !flag;
                stackPane.setVisible(flag);
            }
        });
        
        TopAttractions[] top = DatabaseTransactions.getTop5Attractions();
        setAttractionPreview(top);
        setMenuButtons();
        setImage("/com/icsd/pictures/logo.png", logoImage);
        setImage("/com/icsd/pictures/menu.png", menuId);
        setImage("/com/icsd/pictures/home_icon64.png", homeId);
        setImage("/com/icsd/pictures/search_icon64.png", searchId);
        setImage("/com/icsd/pictures/logout_icon64.png", logoutId);
        setImage("/com/icsd/pictures/background.jpg", backgroundImage);
        logoImage.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                attractionScrollPane.setVisible(false);
                mainScrollPane.setVisible(true);
                resultScrollPane.setVisible(false);
                mainScrollPane.setVvalue(0);
                mainScrollPane.setVmax(1);
            }
        });
        mainScrollPane.setVvalue(-1.7);
        setSearchType();
        searchAttraction();
        setAttractionType();
        
    }
    
    private void setImage(String imagePath, ImageView imageView) {
        
        imageView.setImage(ImageUtils.initImageFromInternal(imagePath));
        
    }
    
    private void setMenuButtons() {
        setMenuHomebutton();
        setMenuSearchbutton();
        setMenuExitbutton();
    }
    
    private void setMenuHomebutton() {
        buttonMenuHome.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                
                mainScrollPane.setVisible(true);
                attractionScrollPane.setVisible(false);
                flag = true;
                flag = !flag;
                stackPane.setVisible(flag);
                resultScrollPane.setVisible(false);
                mainScrollPane.setVvalue(-1.7);
                
            }
        });
    }
    
    private void setMenuSearchbutton() {
        buttonMenuSearch.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                mainScrollPane.setVisible(true);
                attractionScrollPane.setVisible(false);
                resultScrollPane.setVisible(false);
                flag = true;
                flag = !flag;
                stackPane.setVisible(flag);
                mainScrollPane.setVvalue(1);
                
            }
        });
    }
    
    private void setMenuExitbutton() {
        buttonMenuExit.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                System.exit(0);
            }
        });
    }
    
    private void setAttractionPreview(TopAttractions[] top) {
        for (int i = 0; i < 5; i++) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/icsd/main/view/attractionPreview.fxml"));
                AnchorPane attractionRoot = loader.load();
                AttractionPrVController attrPrv = loader.getController();
                attrPrv.setAttractionName(top[i].getAttractionName());
                attrPrv.setAttractionPicture(top[i].getPicture_url());
                attrPrv.setRating(top[i].getRating());
                attrPrv.setSumReviews(top[i].getSumOfReviews());
                attrPrv.getAnchorPane().setOnMouseClicked(event -> {
                    if (event.getButton() == MouseButton.PRIMARY) {
                        try {
                            FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/com/icsd/main/view/attractionFxml.fxml"));
                            AnchorPane attractionCRoot = loader2.load();
                            AttractionController attrCn = loader2.getController();
                            attrCn.setAttractionName(attrPrv.getAttractionName());
                            attractionScrollPane.setContent(attractionCRoot);
                            attractionScrollPane.setVisible(true);
                            attractionScrollPane.setVvalue(0.0);
                            mainScrollPane.setVisible(false);
                            resultScrollPane.setVisible(false);
                            
                        } catch (IOException ex) {
                            System.err.println(ex.getMessage());
                        }
                    }
                });
                topFVHBox.getChildren().add(attractionRoot);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }
    
    private void setAttractionType() {
        Attraction_Type[] attractionTypes = DatabaseTransactions.getAttractionTypes();
        for (Attraction_Type attraction_type : attractionTypes) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/icsd/main/view/attractionTypeImage.fxml"));
                AnchorPane attractionTypeRoot = loader.load();
                AttractionTypeController attrType = loader.getController();
                attrType.setName(attraction_type.getName());
                attrType.setImage(attraction_type.getPicture());
                attractionTypeRoot.setOnMouseClicked(event -> {
                    if (event.getButton() == MouseButton.PRIMARY) {
                        setResultPane("Aξιοθέατα για τον τύπο " + attrType.getName(), DatabaseTransactions.getAttractionsBsdType(attrType.getName()));
                    }
                });
                attrTypeHbox.getChildren().add(attractionTypeRoot);
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        }
    }
    
    private void setSearchType() {
        String[] searchType = {"Ονόματος", "Χώρας", "Περιφέρειας", "Περιοχής", "Τέλος Εισόδου", "Τύπου Αξιοθέατου"};
        ObservableList<String> items = FXCollections.observableArrayList(searchType);
        attrTypeAnchorP.setVisible(false);
        searchCmbo.setVisible(true);
        searchLogoAp.setVisible(true);
        typeOfCmbo.setItems(items);
        typeOfCmbo.setValue(typeOfCmbo.getItems().get(0));
        typeOfCmbo.setOnAction(event -> {
            String selectedValue = typeOfCmbo.getValue();
            if (selectedValue.equals("Τύπου Αξιοθέατου")) {
                attrTypeAnchorP.setVisible(true);
                searchCmbo.setVisible(false);
                
                searchLogoAp.setVisible(false);
                
            } else {
                if (selectedValue.equals("Ονόματος") || selectedValue.equals("Τέλος Εισόδου")) {
                    searchCmbo.setEditable(true);
                    searchCmbo.setItems(null);
                    searchCmbo.setValue("");
                    if (selectedValue.equals("Τέλος Εισόδου")) {
                        searchCmbo.setValue("0");
                    }
                    
                } else {
                    searchCmbo.setEditable(false);
                }
                attrTypeAnchorP.setVisible(false);
                searchCmbo.setVisible(true);
                searchLogoAp.setVisible(true);
            }
            setSearchCmbo();
        });
        setSearchCmbo();
        
    }
    
    private void setSearchCmbo() {
        switch (typeOfCmbo.getValue()) {
            case "Χώρας" ->
                setCountries();
            case "Περιφέρειας" ->
                setRegions();
            case "Περιοχής" ->
                setAreas();
            case "Τύπος Αξιοθέατου" ->
                setAttractionType();
        }
        searchCmbo.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            String character = event.getCharacter();
            if (typeOfCmbo.getValue().equals("Ονόματος")) {
                if (character.matches("[0-9]")) {
                    event.consume();
                }
            } else if (typeOfCmbo.getValue().equals("Τέλος Εισόδου")) {
                if (!character.matches("[0-9]")) {
                    event.consume();
                }
            }
            
        }
        );
    }
    
    private void setCountries() {
        String[] countries = DatabaseTransactions.getCountries();
        String[] itemL = new String[countries.length + 1];
        itemL[0] = "Όλες";
        System.arraycopy(countries, 0, itemL, 1, countries.length);
        ObservableList<String> items = FXCollections.observableArrayList(itemL);
        searchCmbo.setItems(items);
        searchCmbo.setValue(searchCmbo.getItems().get(0));
        
    }
    
    private void setRegions() {
        
        String[] regions = DatabaseTransactions.getRegions();
        String[] itemL = new String[regions.length + 1];
        itemL[0] = "Όλες";
        System.arraycopy(regions, 0, itemL, 1, regions.length);
        ObservableList<String> items = FXCollections.observableArrayList(itemL);
        searchCmbo.setItems(items);
        searchCmbo.setValue(searchCmbo.getItems().get(0));
    }
    
    private void setAreas() {
        String[] areas = DatabaseTransactions.getAreas();
        String[] itemL = new String[areas.length + 1];
        itemL[0] = "Όλες";
        System.arraycopy(areas, 0, itemL, 1, areas.length);
        ObservableList<String> items = FXCollections.observableArrayList(itemL);
        searchCmbo.setItems(items);
        searchCmbo.setValue(searchCmbo.getItems().get(0));
        
    }
    
    private void searchAttraction() {
        searchButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.PRIMARY) {
                    if ((searchCmbo.getValue() != null) && (!searchCmbo.getValue().isBlank() || !searchCmbo.getValue().isEmpty())) {
                        switch (typeOfCmbo.getValue()) {
                            case "Ονόματος" -> {
                                Attraction attraction = DatabaseTransactions.getAttraction(searchCmbo.getValue());
                                if (attraction != null && attraction.getAttractionName() != null) {
                                    System.out.println(attraction);
                                    try {
                                        FXMLLoader loader2 = new FXMLLoader(MainFxmlController.this.getClass().getResource("/com/icsd/main/view/attractionFxml.fxml"));
                                        AnchorPane attractionCRoot = loader2.load();
                                        AttractionController attrCn = loader2.getController();
                                        attrCn.setAttractionName(attraction.getAttractionName());
                                        attractionScrollPane.setContent(attractionCRoot);
                                        attractionScrollPane.setVisible(true);
                                        attractionScrollPane.setVvalue(0.0);
                                        mainScrollPane.setVisible(false);
                                        resultScrollPane.setVisible(false);
                                    } catch (IOException ex) {
                                        System.err.println(ex.getMessage());
                                    }
                                } else {
                                    Alert notFoundAlert = new Alert(AlertType.NONE);
                                    notFoundAlert.getButtonTypes().setAll(ButtonType.OK);
                                    notFoundAlert.setTitle("Δεν βρέθηκε αξιοθέατο");
                                    notFoundAlert.setContentText("Δεν βρέθηκε αξιοθέατο με το όνομα: " + searchCmbo.getValue());
                                    notFoundAlert.setOnCloseRequest(eve -> {
                                        notFoundAlert.hide();
                                    });
                                    notFoundAlert.show();
                                }
                            }
                            case "Τέλος Εισόδου" -> {
                                if (searchCmbo.getValue().equals("0")) {
                                    setResultPane(("Aξιοθέατα με δωρεάν τέλος εισόδου "), DatabaseTransactions.getAttractionsBsdFreeEnFee());
                                } else {
                                    setResultPane(("Aξιοθέατα με τέλος εισόδου μέχρι " + searchCmbo.getValue() + "€"), DatabaseTransactions.getAttractionsBsdEnFee(Double.valueOf(searchCmbo.getValue())));
                                }
                            }
                            case "Χώρας" -> {
                                if (searchCmbo.getValue().equals("Όλες")) {
                                    setResultPane("Aξιοθέατα στην Χώρα " + searchCmbo.getValue(), DatabaseTransactions.getAllAttractions());
                                } else {
                                    setResultPane("Aξιοθέατα στην Χώρα " + searchCmbo.getValue(), DatabaseTransactions.getAttractionsBsdCountry(searchCmbo.getValue()));
                                }
                                
                            }
                            case "Περιφέρειας" -> {
                                if (searchCmbo.getValue().equals("Όλες")) {
                                    setResultPane("Aξιοθέατα στην Περιφέρεια " + searchCmbo.getValue(), DatabaseTransactions.getAllAttractions());
                                } else {
                                    setResultPane("Aξιοθέατα στην Περιφέρεια " + searchCmbo.getValue(), DatabaseTransactions.getAttractionsBsdRegion(searchCmbo.getValue()));
                                }
                                
                            }
                            case "Περιοχής" -> {
                                if (searchCmbo.getValue().equals("Όλες")) {
                                    setResultPane("Aξιοθέατα στην Περιοχή " + searchCmbo.getValue(), DatabaseTransactions.getAllAttractions());
                                } else {
                                    setResultPane("Aξιοθέατα στην Περιοχή " + searchCmbo.getValue(), DatabaseTransactions.getAttractionsBsdArea(searchCmbo.getValue()));
                                }
                                
                            }
                            case "Τύπος Αξιοθέατου" -> {
                                
                                setResultPane("Aξιοθέατα για τον τύπο " + searchCmbo.getValue(), DatabaseTransactions.getAttractionsBsdArea(searchCmbo.getValue()));
                                
                            }
                        }
                    }
                }
            }
        });
    }
    
    private void setResultPane(String title, Attraction[] attractions) {
        
        try {
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/icsd/main/view/attractionResult.fxml"));
            AnchorPane attractionsRoot = loader.load();
            AttractionResultController attractionRe = loader.getController();
            attractionRe.setTitle(title);
            for (Attraction attraction : attractions) {
                try {
                    FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/com/icsd/main/view/attractionPreview.fxml"));
                    AnchorPane attractionRoot = loader1.load();
                    AttractionPrVController attr = loader1.getController();
                    attr.setAttractionName(attraction.getAttractionName());
                    attr.setAttractionPicture(attraction.getAttr_ProfilePic());
                    attr.setRating(attraction.getAverageRating());
                    attr.setSumReviews(attraction.getNumberOfReviews());
                    attr.getAnchorPane().setOnMouseClicked(event -> {
                        if (event.getButton() == MouseButton.PRIMARY) {
                            try {
                                FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/com/icsd/main/view/attractionFxml.fxml"));
                                AnchorPane attractionCRoot = loader2.load();
                                AttractionController attrCn = loader2.getController();
                                attrCn.setAttractionName(attr.getAttractionName());
                                attractionScrollPane.setContent(attractionCRoot);
                                attractionScrollPane.setVisible(true);
                                attractionScrollPane.setVvalue(0.0);
                                mainScrollPane.setVisible(false);
                                resultScrollPane.setVisible(false);
                                
                            } catch (IOException ex) {
                                System.err.println(ex.getMessage());
                            }
                            
                        }
                    });
                    AnchorPane pane = new AnchorPane(attractionRoot);
                    attractionRe.setVbox(pane);
                    
                } catch (IOException ex) {
                    System.err.println(ex.getMessage());
                }
            }
            resultScrollPane.setContent(attractionsRoot);
            resultScrollPane.setVisible(true);
            attractionScrollPane.setVisible(false);
            attractionScrollPane.setVvalue(0.0);
            mainScrollPane.setVisible(false);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
        
    }
    
}
