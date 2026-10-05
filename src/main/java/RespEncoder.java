import java.nio.charset.StandardCharsets;
public class RespEncoder {
    public String encode(RespValue response) {
        if (response.getType() == RespValue.Type.SIMPLE_STRING) {
            return "+" + response.getValue() + "\r\n";
        }
        if (response.getType() == RespValue.Type.BULK_STRING) {
            String value = response.getValue();
            return "$" +
                    value.getBytes(StandardCharsets.UTF_8).length +
                    "\r\n" +
                    value +
                    "\r\n";
        }
        if (response.getType() == RespValue.Type.NULL) {
            return "$-1\r\n";
        }
        if (response.getType() == RespValue.Type.ERROR) {
            return "-" + response.getValue() + "\r\n";
        }
        return "";
    }
}