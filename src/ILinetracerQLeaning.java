/**
 * MyRobotが保持するLintracerQLeaningから呼び出すメソッドのインターフェース
 */
public interface ILinetracerQLeaning {
    void learning();
    void initSensorState();
    int doBestAction();
    void printQTable();
}
