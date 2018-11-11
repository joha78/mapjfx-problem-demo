package sample;

import com.panemu.tiwulfx.control.DetachableTab;
import com.panemu.tiwulfx.control.DetachableTabPane;
import com.sothawo.mapjfx.Coordinate;
import com.sothawo.mapjfx.MapType;
import com.sothawo.mapjfx.MapView;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;

public class Controller {
    @FXML
    private TextField bingMapsApiKeyField;

    @FXML
    private DetachableTabPane tabPane;

    @FXML
    private Button addButton;

    @FXML
    private void initialize() {
        tabPane.setScope("scope1");
        tabPane.setStageOwnerFactory(parentStage -> null);
        tabPane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);

        addButton.setOnAction(event -> {
            if (bingMapsApiKeyField.getText() == null || bingMapsApiKeyField.getText().trim().isEmpty()) {
                DetachableTab newTab = new DetachableTab("Error", new Label("Please provide an API key for bing maps"));
                tabPane.getTabs().add(newTab);
                tabPane.getSelectionModel().select(newTab);
            } else {
                DetachableTab newTab = new DetachableTab("Map", buildMap());
                tabPane.getTabs().add(newTab);
                tabPane.getSelectionModel().select(newTab);
            }
        });
    }

    private Node buildMap() {
        MapView mapView = new MapView();
        mapView.setAnimationDuration(750);
        mapView.setBingMapsApiKey(bingMapsApiKeyField.getText());
        mapView.setMapType(MapType.BINGMAPS_ROAD);
        mapView.setZoom(13);
        mapView.setCenter(new Coordinate(40.741895, -73.989308));
        mapView.initializedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                mapView.setZoom(13);
            }
        });
        mapView.initialize();
        return mapView;
    }
}
