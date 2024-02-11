package persistence;

import model.Account;
import model.AccountList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            AccountList accountList = new AccountList();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyAccountList() {
        try {
            AccountList accountList = new AccountList();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyAccountList.json");
            writer.open();
            writer.write(accountList);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyAccountList.json");
            accountList = reader.read();
            assertEquals(0, accountList.numAccounts());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneral() {
        try {
            AccountList accountList = new AccountList();
            accountList.addAccount(new Account("name1", "password1", 200));
            accountList.addAccount(new Account("name2", "password2", 20));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneral.json");
            writer.open();
            writer.write(accountList);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneral.json");
            accountList = reader.read();
            assertEquals(2, accountList.numAccounts());
            List<Account> accountList1 = accountList.showAllAccount();
            assertEquals(2, accountList1.size());
            checkAccount("name1", "password1", 200, accountList1.get(0));
            checkAccount("name2", "password2", 20, accountList1.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
