import com.company.lab1.banks.entities.banksAccounts.BankAccount;
import com.company.lab1.banks.services.ConsoleService;

public class Main {
    public static void main(String[] args) {
        ConsoleService service = new ConsoleService();
        BankAccount account = service.enterData();
        service.seeMuchAccounts(account);
        service.cashWithdrawal(account, 100);
        service.topUpYourAccount(account, 1000);
    }
}
