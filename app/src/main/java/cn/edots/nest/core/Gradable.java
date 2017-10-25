package cn.edots.nest.core;

import java.io.Serializable;

/**
 * @author Parck.
 * @date 2017/10/23.
 * @desc
 */

public interface Gradable extends Serializable {

    void setType(int type);

    int getType();

}
