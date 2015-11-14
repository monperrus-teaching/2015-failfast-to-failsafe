import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;

public class Main {
    public static void main(String[] args) throws Exception {
        SimpleEmail simpleEmail = new SimpleEmail();
        simpleEmail.setMsg("");

        HtmlEmail htmlEmail = new HtmlEmail();
        htmlEmail.embed("123-d!fkf.cldlf=fd.so+=", "test");

        try {
            String nullArg = null;
            htmlEmail.addTo(nullArg);
        } catch(Exception e) {
            // Empty
        }
    }
}
