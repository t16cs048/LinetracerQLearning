/**
 * 行動を定義する抽象基底クラス
 */
abstract class Action {
    abstract int getActionNum();
    abstract void run(int action);
}