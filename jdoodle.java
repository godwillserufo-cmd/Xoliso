import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class SaveLocationMapApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Save My Location on Google Maps");

        BorderPane root = new BorderPane();
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();

        // Load Google Maps HTML with JS
        String html = " <!DOCTYPE html>
            <html>
            <head>
                <meta name="viewport" content="initial-scale=1.0, user-scalable=no">
                <style>
                    #map { height: 100%; width: 100%; }
                    html, body { height: 100%; margin: 0; padding: 0; }
                </style>
                <script src="https://maps.googleapis.com/maps/api/js?key=https://maps.googleapis.com/maps/api/js?key=AIzaSyAOVYRIgupAurZup5y1PRh8Ismb1A3lLao&libraries=places&callback=initMap"></script>
                <script>
                    var map;
                    var marker;

                    function initMap() {
                        map = new google.maps.Map(document.getElementById('map'), {
                            center: {lat: -24.6585, lng: 25.9088},
                            zoom: 6
                        });
                    }

                    function addMarker(lat, lng) {
                        var position = {lat: lat, lng: lng};
                        if (marker) { marker.setMap(null); } // remove previous marker
                        marker = new google.maps.Marker({
                            position: position,
                            map: map,
                            title: 'Saved Location'
                        });
                        map.setCenter(position);
                        alert('Location saved at: ' + lat + ', ' + lng);
                    }

                    function getRandomLocation() {
                        // Simulate GPS location (replace with real GPS on devices)
                        return {lat: -24.6585 + Math.random(), lng: 25.9088 + Math.random()};
                    }
                </script>
            </head>
            <body onload="initMap()">
                <div id="map"></div>
            </body>
            </html>""";

        webEngine.loadContent(html);

        // Button to save location
        Button saveButton = new Button("Save My Location");
        saveButton.setOnAction(e -> {
            // Call JavaScript to get a random location and add marker
            webEngine.executeScript("""
                var loc = getRandomLocation();
                addMarker(loc.lat, loc.lng);
            """);
        });

        root.setCenter(webView);
        root.setBottom(saveButton);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}