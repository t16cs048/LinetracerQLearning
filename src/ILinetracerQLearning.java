/**
 * MyRobotが保持するLinetracerQLearningから呼び出すメソッドのインターフェース
 */
public interface ILinetracerQLearning {
    void learning();
    void initSensorState();
    int doBestAction();
    void printQTable();
}
