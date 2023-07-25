import java.util.Scanner;
public class creditcard {
    private String name, cardNo, expmonth;
    private boolean enabled;
    private int pin, currentCredit, creditLimit, cardType,amt;
    
    protected creditcard(String name,String cardNo,String expmonth,int pin,int cardType){
        this.name=name;
        this.cardNo=cardNo;
        this.expmonth= expmonth;
        this.pin=pin;
        this.cardType=cardType;
        enabled=true;
        if (cardType==1) {
            creditLimit=50000;
        }
        else if(cardType==2){
            creditLimit=100000;
        }
        else{
            creditLimit=500000;
        }
    }
    protected void changeStatus(boolean status){
        enabled=status;
    }
    protected void changePin(int newPin){
        this.pin=newPin;
        System.out.println("your pin has been sucessfully changed!");
    }
    protected void changeCardStatus(boolean status){
        this.enabled=status;
    }
    protected void display(){
        System.out.println("Your name is "+name);
        System.out.println("Your cardNo is "+cardNo);
        if (enabled==true) {
            System.out.println("Your credit card is currently enabled.");
        }
        if (enabled==false) {
            System.out.println("Your credit card is currently disabled.");
        }
        System.out.println("Your card will expire on "+expmonth);
        if (cardType==1) {
            System.out.println("You are silver member.");
        }
        if (cardType==2) {
            System.out.println("You are gold member.");
        }
        if (cardType==3) {
            System.out.println("You are platinum member.");
        }
        System.out.println("Your current credit is "+(creditLimit-amt));
        System.out.println("Your credit limit is "+creditLimit);
    }

    
    protected void transact(){
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter pin");
        int pin = sc.nextInt();
        if(enabled==true){
            if (pin==this.pin) {
                System.out.println("Enter amount you want to withdraw");
                amt=sc.nextInt();
                if (amt<=currentCredit) {
                    System.out.println("Amount successfully withdrawn.");
                }
                else{
                    System.out.println("Not enough credit.");
                }
            }
            else{
                System.out.println("Wrong pin");
            }
        }
        else{
            System.out.println("Your card is disabled");
        }
        sc.close();
    }


}
