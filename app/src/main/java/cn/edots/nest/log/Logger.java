package cn.edots.nest.log;

import android.util.Log;

/**
 * @author Parck.
 * @date 2017/9/28.
 * @desc
 */
public class Logger {

    private String tag;
    private boolean debug = true;

    public Logger(String tag, boolean debug) {
        this.tag = tag;
        this.debug = debug;
    }

    public void i(Object s) {
        log(Type.I, String.valueOf(s));
    }

    public void d(Object s) {
        log(Type.D, String.valueOf(s));
    }

    public void w(Object s) {
        log(Type.W, String.valueOf(s));
    }

    public void e(Object s) {
        log(Type.E, String.valueOf(s));
    }

    private void log(Type type, String s) {
        if (debug) {
            String message = "│☰            " + s + "            ☰│";
            String symbol = "";
            for (int i = 0; i < message.length(); i++) {
                symbol += "-";
            }
            message = "┌" + symbol + "┐" + "\n" + message;
            message = message + "\n" + "└" + symbol + "┘";
            switch (type) {
                case I:
                    Log.i(tag, message);
                    break;
                case D:
                    Log.d(tag, message);
                    break;
                case W:
                    Log.w(tag, message);
                    break;
                case E:
                    Log.e(tag, message);
                    break;
            }
        }
    }

    private enum Type {
        I, D, W, E
    }
}
