package controller;

import model.Admin;
import model.Menu;
import model.RegularUser;
import model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class RestaurantController {
    private ArrayList<Menu> menuList;
    private Map<String, Map<String, Integer>> pesananPelanggan; // Map pelanggan ke pesanannya
    private int totalHarga;
    private User user;
    private String namaPelanggan;
    private Map<String, String> userCredentials;

    private static final int MAX_LOGIN_ATTEMPTS = 2;
    private int currentLoginAttempts;
    private Scanner scanner;

    public RestaurantController(ArrayList<Menu> menuList) {
        this.menuList = menuList;
        this.pesananPelanggan = new HashMap<>();
        this.currentLoginAttempts = 0;
        this.userCredentials = new HashMap<>();
        this.scanner = new Scanner(System.in);

        // DATA USER YANG HANYA DAPAT DIAKSES
        userCredentials.put("admin", "adminpass");
        userCredentials.put("admin1", "adminpass");
        // DATA ADMIN YANG HANYA DAPAT DIAKSES
        userCredentials.put("haqi", "023");
        userCredentials.put("danta", "106");
        userCredentials.put("hasbi", "092");
    }

    // TAMPILAN MENU UTAMA
    public void showMainMenu() {
        int mainChoice;
        do {
            System.out.println("\n=== Menu Utama ===");
            System.out.println("1. Login sebagai User");
            System.out.println("2. Login sebagai Admin");
            System.out.println("0. Keluar");
            System.out.print("Masukkan pilihan: ");
            mainChoice = scanner.nextInt();
            scanner.nextLine();

            switch (mainChoice) {
                case 1:
                    loginAsUser();
                    if (user != null) {
                        userMenu();
                    }
                    break;
                case 2:
                    loginAsAdmin();
                    if (user != null) {
                        adminMenu();
                    }
                    break;
                case 0:
                    System.out.println("Keluar dari aplikasi");
                    break;
                default:
                    System.out.println("Pilihan tidak valid. Silakan coba lagi.");
            }
        } while (mainChoice != 0);
    }

    // TAMPILAN MENU SELAMAT DATANG RESTOBOY SEBAGAI USER
    public void loginAsUser() {
        System.out.println("== SELAMAT DATANG DI RESTOBOY ==");
        System.out.print("Masukkan username user : ");
        String username = scanner.nextLine();
        System.out.print("Masukkan pasword  user : ");
        String password = scanner.nextLine();

        if (isValidRegularUser(username, password)) {
            user = new RegularUser(username, password);
            System.out.println("Selamat datang, " + username + "!");
            namaPelanggan = username; // SET TAMPILAN PELANGGAN SETALAH LOGIN
        } else {
            System.out.println("Login user Gagal. Nama pengguna atau kata sandi tidak valid.");
            currentLoginAttempts++;

            if (currentLoginAttempts >= MAX_LOGIN_ATTEMPTS) {
                System.out.println("Anda telah melebihi batas percobaan login. Program akan keluar.");
                System.exit(0);
            } else {
                loginAsUser();
            }
        }
    }

    // TAMPILAN MENU SELAMAT DATANG RESTOBOY SEBAGAI ADMIN
    public void loginAsAdmin() {
        System.out.println("== SELAMAT DATANG DI RESTOBOY ==");
        System.out.print("Masukkan username admin : ");
        String username = scanner.nextLine();
        System.out.print("Masukkan pasword admin  : ");
        String password = scanner.nextLine();

        if (isValidAdmin(username, password)) {
            user = new Admin(username, password);
            System.out.println("Selamat datang, Admin!");
        } else {
            System.out.println("Login gagal sebagai admin. Nama pengguna atau kata sandi tidak valid.");
            currentLoginAttempts++;

            if (currentLoginAttempts >= MAX_LOGIN_ATTEMPTS) {
                System.out.println("Anda telah melebihi batas percobaan login. Program akan keluar.");
                System.exit(0);
            } else {
                loginAsAdmin();
            }
        }
    }

    // TAMPILAN USER MENU SEBAGAI USER
    public void userMenu() {
        int userChoice;
        do {
            System.out.println("\n=== User Menu ===");
            if (user instanceof RegularUser) {
                System.out.println("Selamat datang, " + namaPelanggan + "!");
            }
            System.out.println("1. Lihat Menu");
            System.out.println("2. Pesan Menu");
            System.out.println("3. Lihat Pesanan");
            System.out.println("4. Cetak Struk");
            if (user instanceof Admin) {
                System.out.println("5. Kelola Pesanan (Admin)");
                System.out.println("6. Lihat Data Nama Pelanggan (Admin)");
                System.out.println("7. Lihat Pengguna (Admin)");
            }
            System.out.println("0. Keluar");

            System.out.print("Masukan pilihan: ");
            userChoice = scanner.nextInt();
            scanner.nextLine();

            switch (userChoice) {
                case 1:
                    viewMenu();
                    break;
                case 2:
                    orderMenu();
                    break;
                case 3:
                    viewOrders();
                    break;
                case 4:
                    printReceipt();
                    break;
                case 5:
                    if (user instanceof Admin) {
                        manageOrdersAdmin();
                    } else {
                        System.out.println("Anda bukan admin. Tidak dapat mengakses fitur ini.");
                    }
                    break;
                case 6:
                    if (user instanceof Admin) {
                        viewCustomerOrdersData();
                    } else {
                        System.out.println("Anda bukan admin. Tidak dapat mengakses fitur ini.");
                    }
                    break;
                case 7:
                    if (user instanceof Admin) {
                        viewUsers();
                    } else {
                        System.out.println("Anda bukan admin. Tidak dapat mengakses fitur ini.");
                    }
                    break;
                case 0:
                    System.out.println("Keluar dari menu user.");
                    user = null;
                    break;
                default:
                    System.out.println("Pilihan tidak valid. Silahkan coba lagi.");
            }
            if (userChoice == 0 && user == null) {
                showMainMenu();
            }
        } while (userChoice != 0);
    }

    // TAMPILAN ADMIN MENU SEBAGAI ADMIN
    public void adminMenu() {
        int adminChoice;
        do {
            System.out.println("\n=== Menu Admin ===");
            System.out.println("1. Kelola Menu");
            System.out.println("2. Lihat Data Pesanan Pelanggan");
            System.out.println("3. Lihat Daftar Pengguna");
            System.out.println("0. Keluar");

            System.out.print("Masukan Pilihan: ");
            adminChoice = scanner.nextInt();
            scanner.nextLine();

            switch (adminChoice) {
                case 1:
                    manageMenu();
                    break;
                case 2:
                    viewCustomerOrdersData();
                    break;
                case 3:
                    viewUsers();
                    break;
                case 0:
                    System.out.println("Keluar dari menu admin.");
                    break;
                default:
                    System.out.println("Pilihan tidak valid. Silahkan coba lagi.");
            }
        } while (adminChoice != 0);
    }

    private boolean isValidAdmin(String username, String password) {
        return userCredentials.containsKey(username) && userCredentials.get(username).equals(password);
    }

    private boolean isValidRegularUser(String username, String password) {
        return userCredentials.containsKey(username) && userCredentials.get(username).equals(password);
    }

    private void viewMenu() {
        System.out.println("\n=== Menu Makanan ===");
        for (int i = 0; i < menuList.size(); i++) {
            Menu menu = menuList.get(i);
            System.out.println((i + 1) + ". " + menu.getName() + "\tRp " + menu.getPrice());
        }
    }

    private void orderMenu() {
        System.out.print("Masukan nomor menu untuk dipesan: ");
        int menuNumber = scanner.nextInt();
        scanner.nextLine();

        if (isMenuNumberValid(menuNumber)) {
            String menuChoice = menuList.get(menuNumber - 1).getName();
            System.out.println("Harga " + menuChoice + " adalah Rp " + getMenuPrice(menuChoice));
            System.out.print("Masukan jumlah untuk dipesan: ");
            int quantity = scanner.nextInt();
            scanner.nextLine();

            int price = getMenuPrice(menuChoice);
            pesananPelanggan.putIfAbsent(namaPelanggan, new HashMap<>());
            pesananPelanggan.get(namaPelanggan).put(menuChoice,
                    pesananPelanggan.get(namaPelanggan).getOrDefault(menuChoice, 0) + quantity);
            totalHarga += price * quantity;

            System.out.println("Pesanan berhasil ditambahkan!");
        } else {
            System.out.println("Nomor menu tidak valid. Silahkan coba lagi");
        }
    }

    private void viewOrders() {
        System.out.println("\n=== Pesanan untuk " + namaPelanggan + " ===");
        if (pesananPelanggan.containsKey(namaPelanggan)) {
            Map<String, Integer> pesananPelangganDetail = pesananPelanggan.get(namaPelanggan);
            for (Map.Entry<String, Integer> detailEntry : pesananPelangganDetail.entrySet()) {
                System.out.println(detailEntry.getKey() + "\tJumlah: " + detailEntry.getValue());
            }
            System.out.println("Total Harga \tRp " + totalHarga);
        } else {
            System.out.println("Belum ada pesanan untuk " + namaPelanggan + ".");
        }
    }

    private void viewOrders1() {
        System.out.println("\t");
        System.out.println("Nama Menu: ");
        if (pesananPelanggan.containsKey(namaPelanggan)) {
            Map<String, Integer> pesananPelangganDetail = pesananPelanggan.get(namaPelanggan);
            for (Map.Entry<String, Integer> detailEntry : pesananPelangganDetail.entrySet()) {
                System.out.println(detailEntry.getKey() + "\tJumlah: " + detailEntry.getValue());
            }
            System.out.println("Total Harga \tRp " + totalHarga);
        } else {
            System.out.println("Belum ada pesanan untuk " + namaPelanggan + ".");
        }
    }

    private void printReceipt() {
        System.out.println("\n=== STRUK PEMBAYARAN ===");
        viewOrders1();
        System.out.println("\nTerima Kasih, " + namaPelanggan + " atas pesanannya!");
        pesananPelanggan.get(namaPelanggan).clear();
        totalHarga = 0;
    }

    private void manageOrdersAdmin() {
        System.out.print("Masukan nama pelanggan: ");
        String customer = scanner.nextLine();

        System.out.print("Masukan nama menu yang akan dihapus dari pesanan: ");
        String menuToRemove = scanner.nextLine();

        if (pesananPelanggan.containsKey(customer) && pesananPelanggan.get(customer).containsKey(menuToRemove)) {
            pesananPelanggan.get(customer).remove(menuToRemove);
            System.out.println(menuToRemove + " berhasil dihapus dari daftar pesanan pelanggan.");
        } else {
            System.out.println(menuToRemove + " tidak ditemukan dalam daftar pesanan pelanggan.");
        }
    }

    private void viewCustomerOrdersData() {
        System.out.println("\n=== Data Nama Pelanggan ===");
        for (Map.Entry<String, Map<String, Integer>> entry : pesananPelanggan.entrySet()) {
            System.out.println("Pelanggan: " + entry.getKey());
            Map<String, Integer> pesananPelangganDetail = pesananPelanggan.get(namaPelanggan);
            for (Map.Entry<String, Integer> detailEntry : pesananPelangganDetail.entrySet()) {
                System.out.println(detailEntry.getKey() + "\tJumlah: " + detailEntry.getValue());
            }
        }
    }

    private void viewUsers() {
        System.out.println("\n=== list Pengguna ===");
        if (user instanceof Admin) {
            System.out.println("Admin: " + user.getUsername());
        }
    }

    private int getMenuPrice(String menuChoice) {
        for (Menu menu : menuList) {
            if (menu.getName().equalsIgnoreCase(menuChoice)) {
                return menu.getPrice();
            }
        }
        return 0;
    }

    private boolean isMenuNumberValid(int menuNumber) {
        return menuNumber >= 1 && menuNumber <= menuList.size();
    }

    // TAMPILAN MENU MANAGEMENT DI ADMIN
    private void manageMenu() {
        int menuChoice;
        do {
            System.out.println("\n=== Management Menu ===");
            System.out.println("1. Tambahkan Menu");
            System.out.println("2. Hapus Menu");
            System.out.println("3. Lihat Menu");
            System.out.println("0. Kembali");

            System.out.print("Masukan pilihan: ");
            menuChoice = scanner.nextInt();
            scanner.nextLine();

            switch (menuChoice) {
                case 1:
                    addMenu();
                    break;
                case 2:
                    removeMenu();
                    break;
                case 3:
                    viewMenu();
                    break;
                case 0:
                    System.out.println("Kembali ke menu admin.");
                    break;
                default:
                    System.out.println("Pilihan tidak valid. Silahkan coba lagi");
            }
        } while (menuChoice != 0);
    }

    private void addMenu() {
        System.out.print("Masukan nama menu baru    : ");
        String menuName = scanner.nextLine();
        System.out.print("Masukan harga menu baru   : ");
        int menuPrice = scanner.nextInt();
        scanner.nextLine();

        Menu newMenu = new Menu(menuName, menuPrice);
        menuList.add(newMenu);
        System.out.println("Menu " + menuName + " berhasil ditambahkan!");
    }

    private void removeMenu() {
        System.out.print("Masukkan nama menu yang akan dihapus: ");
        String menuName = scanner.nextLine();

        boolean removed = false;
        for (Menu menu : menuList) {
            if (menu.getName().equalsIgnoreCase(menuName)) {
                menuList.remove(menu);
                removed = true;
                break;
            }
        }

        if (removed) {
            System.out.println("Menu " + menuName + " berhasil dihapus.");
        } else {
            System.out.println("Menu " + menuName + " tidak ditemukan.");
        }
    }
}
