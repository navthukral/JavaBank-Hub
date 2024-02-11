package persistence;

import model.Account;
import model.AccountList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest extends JsonTest{

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            AccountList accountList = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyAccountList() {
        JsonReader reader = new JsonReader("./data/testReaderEmpty.json");
        try {
            AccountList accountList = reader.read();
            assertEquals(0, accountList.numAccounts());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneral() {
        JsonReader reader = new JsonReader("./data/testReaderGeneral.json");
        try {
            AccountList accountList = reader.read();
            List<Account> accountList1 = accountList.showAllAccount();
            assertEquals(2, accountList1.size());
            checkAccount("name1", "password1", 200, accountList1.get(0));
            checkAccount("name2", "password2", 20, accountList1.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }


}
