import com.aliyuncs.exceptions.ClientException;
import com.itheima.utils.SMSUtils;
import sun.misc.MessageUtils;

public class MessageDemo {
    public static void main(String[] args) throws ClientException {

        SMSUtils.sendShortMessage("520520","18855997217","你好");
    }
}

