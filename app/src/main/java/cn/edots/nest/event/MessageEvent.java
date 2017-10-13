package cn.edots.nest.event;

/**
 * @author Parck.
 * @date 2017/10/12.
 * @desc
 */

public class MessageEvent {

    public static final int CMD_FINISH_ACTIVITY = 0;

    private String message;
    private int cmd;
    private Class<?> clazz;

    public MessageEvent(String message) {
        this.message = message;
    }

    public MessageEvent(int cmd, Class<?> clazz) {
        this.cmd = cmd;
        this.clazz = clazz;
    }

    public String getMessage() {
        return message;
    }

    public int getCMD() {
        return cmd;
    }

    public Class<?> getClazz() {
        return clazz;
    }
}
