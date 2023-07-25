import java.util.Scanner;
public class CreditTester {
    public static void main(String[] args){
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter your name");
        String name=sc.nextLine();
        System.out.println("Enter card number");
        String cardNo=sc.nextLine();
        System.out.println("Enter Expiry date");
        String expmonth=sc.nextLine(); 
        System.out.println("Enter pin");
        int pin = sc.nextInt();
        System.out.println("Enter the card type(Platinum =3, Gold =2, Silver =1)");
        int cardType = sc.nextInt();
        
        
        creditcard c = new creditcard(name, cardNo,expmonth, pin,cardType);
        System.out.println("Do you wish to change pin? (Enter 1 for yes 0 for no)");
        int n = sc.nextInt();
        if(n==1)
        {
        System.out.println("to change pin Enter old pin");
        int oldpin = sc.nextInt();
        
        if(oldpin==pin){
            System.out.println("Enter new pin");
            int newpin = sc.nextInt();
            c.changePin( newpin);
        }else{
            System.out.println("You have entered wrong pin. Please try again!");
        }}
        System.out.println("Enter the new card status");
        boolean status=sc.nextBoolean();
        c.changeStatus(status);
        c.transact();
        c.display();
        sc.close();



    }
}
