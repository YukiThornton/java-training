package java8.ch04.ex02;

import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TableViewer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox box = new VBox(2);

        TableView<Item> table = new TableView<>();
        table.setOnMouseClicked(event -> {
            Item item = table.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Info");
            alert.setHeaderText("Item description");
            String s = item.toString() + "\n\nLoading properties...\n\n";
            item.loadProperties();
            s += item.toString();
            alert.setContentText(s);
            alert.show();
        });

        TableColumn<Item, String> itemColumn = new TableColumn<Item, String>("Item");
        itemColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Item, String> priceColumn = new TableColumn<Item, String>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        table.getColumns().addAll(itemColumn, priceColumn);

        ObservableList<Item> list = FXCollections.observableArrayList(
                new Item("apple", 100),
                new Item("banana", 150),
                new Item("peach", 400)
        );
        table.setItems(list);

        box.getChildren().add(table);

        primaryStage.setScene(new Scene(box));
        primaryStage.setTitle("Purchase List");
        primaryStage.show();
    }
    
    public class Item {
        private String name;
        private int price;
        private SimpleStringProperty nameProperty;
        private SimpleIntegerProperty priceProperty;
        
        public Item(String name, int price) {
            this.name = name;
            this.price = price;
            this.nameProperty = null;
            this.priceProperty = null;
        }

        @Override
        public String toString() {
            return "name=" + name + "\nnameProperty=" + nameProperty + "\nprice=" + price + "\npriceProperty=" + priceProperty;
        }

        public void loadProperties() {
            getNameProperty();
            getPriceProperty();
        }

        public String getName() {
            return name;
        }

        public StringProperty getNameProperty() {
            if (nameProperty == null) {
                this.nameProperty = new SimpleStringProperty(name);
            }
            return nameProperty;
        }

        public void setName(String name) {
            this.nameProperty.set(name);
            if (nameProperty != null) {
                this.nameProperty.set(name);
            }
        }

        public int getPrice() {
            return price;
        }

        public IntegerProperty getPriceProperty() {
            if (priceProperty == null) {
                this.priceProperty = new SimpleIntegerProperty(price);
            }
            return priceProperty;
        }

        public void setPrice(int price) {
            this.priceProperty.set(price);
            if (priceProperty != null) {
                this.priceProperty.set(price);
            }
        }
    }
}
