/**
 * 行動を定義したクラス
 */
public class Action extends AbsAction {
    private int actionNum = 9;
    private Robot robot;

    public Action(Robot r){
        this.robot = r;
    }


    public int getActionNum() {
        return actionNum;
    }

    /**
     * 呼び出し元のロボットを行動させる
     * @param action
     */
    public void run(int action){
        int angle = 15 * (action - 5);

        double speed = 0;
        if (action < 6)
            speed = 0.2 * action;
        else
            speed = 2.0 - (0.2 * action);

        robot.rotate(angle);
        robot.forward(speed);
    }


}
