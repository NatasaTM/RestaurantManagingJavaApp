package restaurant.server.thread;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import restaurant.common.domain.City;
import restaurant.common.domain.Employee;
import restaurant.common.domain.Menu;
import restaurant.common.domain.MenuCategory;
import restaurant.common.domain.MenuItem;
import restaurant.common.domain.MenuItemType;
import restaurant.common.domain.Order;
import restaurant.common.domain.OrderItem;
import restaurant.common.domain.Payment;
import restaurant.common.domain.Receipt;
import restaurant.common.domain.Role;
import restaurant.common.domain.Table;
import restaurant.common.domain.User;
import restaurant.common.transfer.Operation;
import restaurant.common.transfer.Request;
import restaurant.common.transfer.Response;
import restaurant.server.controller.CityController;
import restaurant.server.controller.EmployeeController;
import restaurant.server.controller.LoginController;
import restaurant.server.controller.MenuItemMenuController;
import restaurant.server.controller.OrderController;
import restaurant.server.controller.PaymentController;
import restaurant.server.controller.ReceiptController;
import restaurant.server.controller.RoleController;
import restaurant.server.controller.TableController;
import restaurant.server.session.Session;

/**
 *
 * @author Natasa Todorov Markovic
 */
public class ProcessClientRequests extends Thread {

    private Socket socket;
    private ObjectOutputStream sender;
    private ObjectInputStream receiver;
    private boolean signal;
    private String clientName;
    private Date loginTime;

    public ProcessClientRequests(Socket socket) {
        try {
            this.socket = socket;
            this.sender = new ObjectOutputStream(this.socket.getOutputStream());
            this.receiver = new ObjectInputStream(this.socket.getInputStream());
            this.signal = true;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (signal && !isInterrupted()) {
            try {
                Request request = (Request) receiver.readObject();
                Response response = new Response();
                try {
                    System.out.println("O: " + request.getOperation());
                    switch (request.getOperation()) {
                        case LOGIN: {
                            User u = (User) request.getArgument();
                            User user = (User) LoginController.getInstance().login(u.getUsername(), u.getPassword());
                            response.setResult(user);
                            setClientName(user.getEmployee().getFirstname() + " " + user.getEmployee().getLastname());
                            setLoginTime(new Date());
                            //Session.getInstance().getClients().add(this);
                            Session.getInstance().addClient(this);
                            break;
                        }

                        case END: {
                            interrupt();
                            signal = false;
                            closeClient();
                            Session.getInstance().removeClient(this);
                            break;
                        }
                        case MENU_CATEGORY_GET_ALL: {
                            System.out.println("Pozvana metoda: " + Operation.MENU_CATEGORY_GET_ALL.toString());
                            List<MenuCategory> menuCategories = MenuItemMenuController.getInstance().getAllMenuCategories();
                            System.out.println(menuCategories.toString());
                            response.setResult(menuCategories);
                            break;
                        }
                        case MENU_ITEM_ADD: {
                            System.out.println("Pozvana metoda: " + Operation.MENU_ITEM_ADD.toString());
                            MenuItem menuItem = (MenuItem) request.getArgument();
                            MenuItemMenuController.getInstance().addMenuItem(menuItem);
                            response.setResult("Jelo je uspesno dodato");
                            break;
                        }
                        case MENU_ITEM_GET_ALL: {
                            List<MenuItem> menuItems = MenuItemMenuController.getInstance().getAllMenuItems();
                            response.setResult(menuItems);
                            break;
                        }
                        case MENU_ITEM_GET_ALL_BY_CATEGORY: {
                            String categoryName = (String) request.getArgument();
                            List<MenuItem> menuItems = MenuItemMenuController.getInstance().getAllMenuItemsByCategory(categoryName);
                            response.setResult(menuItems);
                            break;
                        }
                        case MENU_ITEM_GET_ALL_BY_TYPE: {
                            MenuItemType menuItemType = (MenuItemType) request.getArgument();
                            List<MenuItem> menuItems = MenuItemMenuController.getInstance().getAllMenuItemsByType(menuItemType);
                            response.setResult(menuItems);
                            break;
                        }
                        case MENU_ITEM_UPDATE: {
                            MenuItem menuItem = (MenuItem) request.getArgument();
                            MenuItemMenuController.getInstance().updateMenuItem(menuItem);
                            response.setResult("Jelo je uspesno izmenjeno.");
                            break;
                        }
                        case MENU_ITEM_DELETE: {
                            MenuItem menuItem = (MenuItem) request.getArgument();
                            MenuItemMenuController.getInstance().deleteMenuItem(menuItem);
                            response.setResult("Jelo je uspesno obrisano.");
                            break;
                        }
                        case MENU_ADD: {
                            Menu menu = (Menu) request.getArgument();
                            MenuItemMenuController.getInstance().addMenu(menu);
                            response.setResult("Jelovnik je uspesno dodat");
                            break;
                        }
                        case MENU_ITEM_FIND_BY_ID: {
                            Integer id = (Integer) request.getArgument();
                            MenuItem menuItem = MenuItemMenuController.getInstance().menuItemFindById(id);
                            response.setResult(menuItem);
                            break;

                        }
                        case MENU_FIND_BY_ID: {
                            Integer id = (Integer) request.getArgument();
                            Menu menu = MenuItemMenuController.getInstance().findMenuById(id);
                            response.setResult(menu);
                            break;
                        }
                        case MENU_GET_ALL: {
                            List<Menu> menus = MenuItemMenuController.getInstance().menuGetAll();
                            response.setResult(menus);
                            break;
                        }
                        case MENU_GET_MENU_ITEMS_BY_CATEGORY: {
                            String categoryName = (String) request.getArguments().get(0);
                            Menu menu = (Menu) request.getArguments().get(1);
                            List<MenuItem> menuItems = MenuItemMenuController.getInstance().menuItemGetAllByCategoryFromMenu(categoryName, menu);
                            response.setResult(menuItems);
                            System.out.println("Ime kat: " + categoryName + "Meni: " + menu);
                            System.out.println(menuItems.toString());
                            break;
                        }
                        case MENU_UPDATE: {
                            Menu menu = (Menu) request.getArgument();
                            MenuItemMenuController.getInstance().menuUpdate(menu);
                            response.setResult("Jelovnik je uspesno izmenjen");
                            break;
                        }
                        case MENU_DELETE: {
                            Menu menu = (Menu) request.getArgument();
                            MenuItemMenuController.getInstance().menuDelete(menu);
                            response.setResult("Jelovnik je uspesno obrisan");
                            break;
                        }
                        case MENU_ADD_MENU_ITEM: {
                            MenuItem menuItem = (MenuItem) request.getArguments().get(0);
                            Menu menu = (Menu) request.getArguments().get(1);
                            MenuItemMenuController.getInstance().menuAddItem(menuItem, menu);
                            response.setResult("Jelo je uspesno dodato u jelovnik");
                            break;
                        }
                        case MENU_DELETE_MENU_ITEM: {
                            Menu menu = (Menu) request.getArguments().get(0);
                            System.out.println("Da li postoji: " + menu);
                            MenuItem menuItem = (MenuItem) request.getArguments().get(1);
                            System.out.println("Da li postoji: " + menuItem);
                            MenuItemMenuController.getInstance().menuDeleteMenuItem(menu, menuItem);
                            response.setResult("Jelo je uspesno obrisano iz jelovnika.");
                            break;
                        }
                        case MENU_CATEGORY_ADD: {
                            MenuCategory menuCategory = (MenuCategory) request.getArgument();
                            MenuItemMenuController.getInstance().menuCategoryAdd(menuCategory);
                            response.setResult("Kategorija je uspesno dodata.");
                            break;
                        }
                        case TABLE_ADD: {
                            Table table = (Table) request.getArgument();
                            TableController.getInstance().addTable(table);
                            response.setResult("Sto je uspesno dodat");
                            break;
                        }
                        case TABLE_GET_ALL: {
                            List<Table> tables = TableController.getInstance().getAllTables();
                            response.setResult(tables);
                            break;
                        }
                        case TABLE_DELETE: {
                            Table table = (Table) request.getArgument();
                            TableController.getInstance().deleteTable(table);
                            response.setResult("Sto je uspesno obrisan");
                            break;
                        }
                        case TABLE_UPDATE: {
                            Table table = (Table) request.getArgument();
                            TableController.getInstance().updateTable(table);
                            response.setResult("Sto je uspesno izmenjen");
                            break;
                        }
                        case EMPLOYEE_GET_ALL: {
                            List<Employee> employees = EmployeeController.getInstance().getAllEmployees();
                            response.setResult(employees);
                            break;
                        }
                        case EMPLOYE_FIND_BY_ID: {
                            Integer id = (Integer) request.getArgument();
                            Employee employee = EmployeeController.getInstance().findEmployeeById(id);
                            response.setResult(employee);
                            break;

                        }
                        case CITY_GET_ALL: {
                            List<City> cities = CityController.getInstance().getAllCities();
                            response.setResult(cities);
                            break;

                        }
                        case EMPLOYEE_ADD: {
                            Employee employee = (Employee) request.getArgument();
                            EmployeeController.getInstance().addEmployee(employee);
                            response.setResult("Zaposleni je uspesno dodat");
                            break;
                        }
                        case CITY_ADD: {
                            City city = (City) request.getArgument();
                            CityController.getInstance().addCity(city);
                            response.setResult("Grad je uspesno dodat");
                            break;
                        }
                        case EMPLOYEE_UPDATE: {
                            Employee employee = (Employee) request.getArgument();
                            EmployeeController.getInstance().updateEmployee(employee);
                            response.setResult("Podaci o zaposlenom su uspesno izmenjeni!");
                            break;
                        }
                        case EMPLOYEE_DELETE: {
                            Employee employee = (Employee) request.getArgument();
                            EmployeeController.getInstance().deleteEmployee(employee);
                            response.setResult("Zaposleni je uspesno obrisan!");
                            break;
                        }
                        case TABLE_IS_AVAILABLE: {
                            List<Table> tables = TableController.getInstance().tableIsAvailable();
                            response.setResult(tables);
                            break;
                        }
                        case MENU_GET_ALL_BY_STATUS: {
                            List<Menu> menus = MenuItemMenuController.getInstance().getAllMenuByStatus();
                            response.setResult(menus);
                            break;
                        }
                        case ORDER_ADD: {
                            Order order = (Order) request.getArgument();
                            OrderController.getInstance().addOrder(order);
                            response.setResult("Porudzbina je uspesno sacuvana");
                            break;
                        }
                        case ORDER_FIND_BY_CONDITION: {
                            LocalDate date = (LocalDate) request.getArguments().get(0);
                            Boolean statusReady = (Boolean) request.getArguments().get(1);
                            Boolean statusPaied = (Boolean) request.getArguments().get(2);
                            Employee employee = (Employee) request.getArguments().get(3);
                            Table table = (Table) request.getArguments().get(4);
                            List<Order> orders = OrderController.getInstance().findByCondition(date, statusReady, statusPaied, employee, table);
                            response.setResult(orders);
                            break;
                        }
                        case ORDER_GET_ALL: {
                            List<Order> orders = OrderController.getInstance().getAllOrders();
                            response.setResult(orders);
                            break;
                        }
                        case ORDER_FIND_BY_ID: {
                            Long id = (Long) request.getArgument();
                            Order order = OrderController.getInstance().findOrderById(id);
                            response.setResult(order);
                            break;
                        }
                        case ORDER_UPDATE: {
                            Order order = (Order) request.getArgument();
                            OrderController.getInstance().orderUpdate(order);
                            response.setResult("Porudzbina je uspesno promenjena");
                            break;
                        }
                        case RECEIPT_ADD: {
                            Receipt receipt = (Receipt) request.getArgument();
                            ReceiptController.getInstance().receiptAdd(receipt);
                            response.setResult(receipt);
                            break;
                        }
                        case PAYMENT_ADD: {
                            Payment payment = (Payment) request.getArgument();
                            PaymentController.getInstance().addPayment(payment);
                            response.setResult("Placanje je uspesno uneto");
                            break;
                        }
                        case USER_ADD: {
                            User user = (User) request.getArgument();

                            LoginController.getInstance().addUser(user);
                            response.setResult("Korisnik je uspesno unet");
                            break;
                        }
                        case ROLE_GET_ALL: {
                            List<Role> roles = RoleController.getInstance().getAllRoles();
                            response.setResult(roles);
                            break;
                        }
                        case RECEIPT_GET_ALL: {
                            List<Receipt> receipts = ReceiptController.getInstance().getAllReceipts();
                            response.setResult(receipts);
                            break;
                        }
                        case PAYMENT_GET_ALL: {
                            List<Payment> payments = PaymentController.getInstance().getAllPayments();
                            response.setResult(payments);
                            break;
                        }
                        case RECEIPT_FIND_BY_ID: {
                            Long id = (Long) request.getArgument();
                            Receipt receipt = ReceiptController.getInstance().findById(id);
                            response.setResult(receipt);
                            break;
                        }
                        case PAYMENT_FIND_BY_RECEIPT: {
                            Receipt receipt = (Receipt) request.getArgument();
                            Payment payment = PaymentController.getInstance().findByReceipt(receipt);
                            response.setResult(payment);
                            break;
                        }
                        case ORDERITEM_FIND_BY_QUERY: {

                            Order order = (Order) request.getArguments().get(0);
                            MenuItemType menuItemType = (MenuItemType) request.getArguments().get(1);
                            boolean isReady = (Boolean) request.getArguments().get(2);
                            List<OrderItem> orderItems = OrderController.getInstance().findOrderItemsByQuery(order, menuItemType, isReady);
                            response.setResult(orderItems);
                            break;
                        }
                        case ORDERITEM_FIND_BY_ID: {
                            Integer id = (Integer) request.getArgument();
                            OrderItem orderItem = OrderController.getInstance().findOrderItemById(id);
                            response.setResult(orderItem);
                            break;
                        }
                        case ORDERITEM_UPDATE: {
                            OrderItem orderItem = (OrderItem) request.getArgument();
                            OrderController.getInstance().udateOrderItem(orderItem);
                            response.setResult("Stavka je uspesno oznacena kao gotova");
                            break;
                        }
                        case RECEIPT_FIND_BY_QUERY: {
                            List<Receipt> receipts = ReceiptController.getInstance().findUnpaiedReceipts();
                            response.setResult(receipts);
                            break;
                        }
                        case TABLE_SET_IS_AVAILABLE: {
                            Table table = (Table) request.getArguments().get(0);
                            boolean isAvailable = (boolean) request.getArguments().get(1);
                            TableController.getInstance().tableSetIsAvailable(table, isAvailable);
                            break;
                        }
                        case USER_GET_ALL: {
                            List<User> users = LoginController.getInstance().getAllUsers();
                            response.setResult(users);
                            break;
                        }
                        case USER_FIND_BY_ID:{
                            String username = (String) request.getArgument();
                            User user = LoginController.getInstance().findUserById(username);
                            response.setResult(user);
                            break;
                        }
                        
                        case USER_DELETE:{
                            User user = (User) request.getArgument();
                            LoginController.getInstance().deleteUser(user);
                            response.setResult("Nalog je uspesno izbrisan");
                            break;
                        }
                        case USER_UPDATE:{
                           User user = (User) request.getArgument();
                           LoginController.getInstance().updateUser(user);
                           response.setResult("Nalog je uspesno izmenjen");
                            break;
                        }
                        case RECEIPT_FIND_UNPAIED_BY_EMPLOYEE:{
                            Employee employee = (Employee) request.getArgument();
                            List<Receipt> receipts = ReceiptController.getInstance().findUnpaiedReceiptsByEmployee(employee);
                            response.setResult(receipts);
                            break;
                        }

                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    response.setException(ex);
                }
                sender.writeObject(response);
            } catch (SocketException e) {
                try {
                    interrupt();
                    closeClient();
                    Session.getInstance().getClients().remove(this);
                } catch (IOException ex) {
                    Logger.getLogger(ProcessClientRequests.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        System.out.println("Process Client " + getClientName() + " Requests Thread End");
    }

    public void closeClient() throws IOException {
        socket.close();
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.socket);
        hash = 79 * hash + Objects.hashCode(this.clientName);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ProcessClientRequests other = (ProcessClientRequests) obj;
        if (!Objects.equals(this.clientName, other.clientName)) {
            return false;
        }
        return Objects.equals(this.socket, other.socket);
    }

}
