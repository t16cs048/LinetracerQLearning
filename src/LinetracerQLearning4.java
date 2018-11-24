/**
 * ライントレーサーのためのQ学習を行うインスタンス
 * 過去の状態数を3つもつ
 * 行動もさせる
 */
public class LinetracerQLearning4 extends QLearning{
    private MyRobot robot; // 呼び出し元のMyRobotのインスタンス
    private int s0;
    private int s1;
    private int s2;
    private int s3;
    private int a0 = 0;
    private int a1 = 0;
    private int a2 = 0;
    private int a3 = 0;


    /**
     *
     * @param r 呼び出し元のMyRobotのインスタンス
     */
    public LinetracerQLearning4(MyRobot r){
        super();
        int states = 4096; // 状態数
        int actions = 9; // 行動数
        double alpha = 0.5; // 学習率
        double gamma = 0.5; // 割引率
        setAll(states, actions, alpha, gamma);

        this.robot = r;
        initSensorState();
    }


    /**
     * 強化学習の1回の試行を実行し，Qテーブルを更新する
     */
    public void learning(){
        int state = getState(); // 今の状態を取得
        // epsilon-Greedy 法により行動を選択
        int action = selectAction(state, 0.3);

        // 選択した行動をロボットに実行
        doAction(action);
        // 過去の状態も合わせて，状態を更新する
        updateState();

        // 過去2回の行動を更新する
        updateAction(action);

        // 行動後の新しい状態を観測
        int newState = getState();

        // 報酬を得る
        double reward = getReward();

        // Q値を更新する
        update(state, action, newState, reward);
    }

    private void updateAction(int action) {
        a0 = a1;
        a1 = a2;
        a2 = a3;
        a3 = action;
    }

    /**
     * 過去2回のセンサーの状態を更新する
     */
    private void updateState() {
        s0 = s1;
        s1 = s2;
        s2 = s3;
        s3 = robot.getSensorState();
    }

    /**
     * センサーの状態を初期化する
     */
    public void initSensorState() {
        s0 = robot.getSensorState();
        s1 = robot.getSensorState();
        s2 = robot.getSensorState();
        s3 = robot.getSensorState();
    }

    /**
     * Qテーブルの最適な行動を実行する
     * @return 最適な行動を返す
     */
    public int doBestAction(){
        int state = getState();
        int action = selectAction(state);
        doAction(action);
        updateState();
        return action;
    }


    /**
     * 過去2回のセンサーの状態と現在のセンサー状態から，状態を算出する
     * @return
     */
    private int getState(){
        return 8*8*8*s0 + 8*8*s1 + 8*s2 + s3;
    }


    /**
     *
     * @param action
     */
    private void doAction(int action){
        int angle = 15 * (action - 5);

        double speed = 0;
        if (action < 6)
            speed = 0.2 * action;
        else
            speed = 2.0 - (0.2 * action);

        robot.rotate(angle);
        robot.forward(speed);
    }

    private int getReward() {
        if (robot.isOnGoal())
            return 100000;

        // ライン外
        if (s3 == 0)
            return -100000;

        if (s0 == 2 && s1 == 2 && s2 == 2 && s3 == 2)
            return 10000;

        if (s1 == 2 && s2 == 2 && s3 == 2)
            return 5000;

        if (s2 == 2 && s3 == 2)
            return 1000;

        if (s3 == 2)
            return 100;

        return 5;
    }

}
