package cn.edots.nest;

/**
 * @author Parck.
 * @date 2017/10/10.
 * @desc
 */

public interface Standardize {

    /**
     * 执行优先级1
     */
    void setupData();

    /**
     * 执行优先级2
     */
    void initView();

    /**
     * 执行优先级3
     */
    void setListeners();

    /**
     * 执行优先级4
     */
    void onCreateLast();

}
