/**
 * 行動を定義したクラス
 * 5度間隔で回転し，進む行動
 * 行動数は35通り
 */
public class Action2 extends Action {
    private int actionNum = 35;
    private Robot robot;

    public Action2(Robot r){
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
        if (action < 18)
            robot.rotate(-5 * action);
        else
            robot.rotate(5 * action);

        robot.forward(1);

    }


}
