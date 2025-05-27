import java.util.Scanner;
import java.io.*;

//room deatails
class Room {
    int roomNo;
    String roomType;
    double rate;
    boolean isAvailable;

    public Room(int roomNo, String roomType, double rate) {
        this.roomNo = roomNo;
        this.roomType = roomType;
        this.rate = rate;
        this.isAvailable = true;
    }

    public void bookRoom(String userName) {
        if (isAvailable) {
            isAvailable = false;
            System.out.println("\nDear " + userName + ", your " + roomType + " room (Room No: " + roomNo + ") has been booked successfully!");
        } else {
            System.out.println("Sorry, Room " + roomNo + " is already booked.");
        }
    }
}

//payment
class Payment {
    public boolean processPayment(double amount, double correctAmount) {
        return amount == correctAmount;
    }
}

//main
public class hotel{
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Room[] rooms = {
            new Room(101, "Single", 1000), new Room(102, "Single", 1000),
            new Room(103, "Single", 1000), new Room(104, "Single", 1000),
            new Room(105, "Single", 1000), new Room(106, "Single", 1000),

            new Room(201, "Double", 1500), new Room(202, "Double", 1500),
            new Room(203, "Double", 1500), new Room(204, "Double", 1500),
            new Room(205, "Double", 1500), new Room(206, "Double", 1500)
        };

        System.out.print("Welcome to our hotel! Please enter your name: ");
        String userName = scan.nextLine();

        System.out.println("\nSelect your room type:");
        System.out.println("1 - Single (₹1000)");
        System.out.println("2 - Double (₹1500)");

        System.out.print("\nEnter your choice (1/2): ");
        int typeChoice = scan.nextInt();
        

        String chosenType = switch (typeChoice) {
            case 1 -> "Single";
            case 2 -> "Double";
            default -> "";
        };

        if (chosenType.isEmpty()) {
            System.out.println("Invalid choice! Please restart and enter a valid option.");
            scan.close();
            return;
        }

        System.out.println("\nAvailable Rooms for " + chosenType + ":");
        boolean RoomAvailable = false;
        for (Room room : rooms) {
            if (room.roomType.equalsIgnoreCase(chosenType) && room.isAvailable) {
                System.out.println("Room " + room.roomNo + " (₹" + room.rate + ")");
                RoomAvailable = true;
            }
        }

        if (!RoomAvailable) {
            System.out.println("Sorry, no available rooms of the selected type.");
            scan.close();
            return;
        }

        System.out.print("\nEnter the room number you want to book: ");
        int chosenroomNo = scan.nextInt(); 

        Room selectedRoom = null;
        for (Room room : rooms) {
            if (room.roomNo == chosenroomNo && room.roomType.equalsIgnoreCase(chosenType) && room.isAvailable) {
                selectedRoom = room;
                break;
            }
        }

        if (selectedRoom == null) {
            System.out.println("Invalid room number or room already booked. Please restart and try again.");
            scan.close();
            return;
        }

        Payment payment = new Payment();
        boolean paymentSuccess = false;

        while (!paymentSuccess) {
            System.out.print("\nPlease enter the payment amount (₹" + selectedRoom.rate + "): ");
            double userAmount = scan.nextDouble(); 

            if (payment.processPayment(userAmount, selectedRoom.rate)) {
                selectedRoom.bookRoom(userName);
                System.out.println("Payment successful! Your booking is confirmed.");
                paymentSuccess = true;
            } else {
                System.out.println("Incorrect amount. Type 'retry' to try again or 'exit' to cancel booking.");
                String retryOrExit = scan.nextLine().trim().toLowerCase();

                if (retryOrExit.equals("exit")) {
                    System.out.println("Room booking cancelled.");
                    scan.close();
                    return;
                }
            }
        }

        scan.close();
    }
}
