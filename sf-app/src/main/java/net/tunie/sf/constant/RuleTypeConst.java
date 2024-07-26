package net.tunie.sf.constant;

/**
 * 规则类型
 */
public class RuleTypeConst {
    /**
     * 普通
     */
    public static final int COMMON = 0;
    /**
     * 游戏 - 24点
     */
    public static final int GAME_POINT24 = 100;
    /**
     * 学习 - 单词打卡
     */
    public static final int LEARN_ENG_WORD = 200;


    public static boolean needQuesUpdate(int type) {
        return type == LEARN_ENG_WORD;
    }

}
