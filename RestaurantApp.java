import controller.RestaurantController;
import model.Menu;

import java.util.ArrayList;

public class RestaurantApp {
    public static void main(String[] args) {
        // TAMPILAN MENU MAKANAN
        ArrayList<Menu> menuList = new ArrayList<>();
        menuList.add(new Menu("Nasi Goreng", 15000));
        menuList.add(new Menu("Mie Goreng", 12000));
        menuList.add(new Menu("Ayam Goreng", 20000));
        menuList.add(new Menu("Bebek Bakar", 25000));
        menuList.add(new Menu("Sate Kambing", 30000));

        // UNTUK INSTANCE DARI FILE RESTAURANT CONTROLLER
        RestaurantController restaurantController = new RestaurantController(menuList);

        // UNTUK TAMPILAN MENU UTAMA
        restaurantController.showMainMenu();
    }
}
