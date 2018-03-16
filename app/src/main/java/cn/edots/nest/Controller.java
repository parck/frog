package cn.edots.nest;

import java.io.Serializable;

/**
 * @Author WETOOP
 * @Date 2018/3/16.
 * @Description
 */

public interface Controller extends Serializable {

    void initialize();

    void restore();

    void destroy();

}