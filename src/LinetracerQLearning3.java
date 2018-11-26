/**
 * ライントレーサーのためのQ学習を行うインスタンス
 * 過去の状態を2つもつ
 * 行動もさせる
 */
public class LinetracerQLearning3 implements ILinetracerQLeaning{
    private MyRobot robot; // 呼び出し元のMyRobotのインスタンス
    private Action action;
    private QLearning q;
    private int s0;
    private int s1;
    private int s2;
    private int a0 = 0;
    private int a1 = 0;
    private int a2 = 0;


    /**
     * 内部でQLearningのインスタンスを保持する
     * @param r 呼び出し元のMyRobotのインスタンス
     */
    public LinetracerQLearning3(MyRobot r){
        this.robot = r;
        this.action = new Action1(r);

        int states = 512; // 状態数
        int actions = action.getActionNum(); // 行動数
        double alpha = 0.5; // 学習率
        double gamma = 0.5; // 割引率
        this.q = new QLearning(states, actions, alpha, gamma);

        initSensorState();
    }


    /**
     * 強化学習の1回の試行を実行し，Qテーブルを更新する
     */
    public void learning(){
        int state = getState(); // 今の状態を取得
        // epsilon-Greedy 法により行動を選択
        int actionNum = q.selectAction(state, 0.3);

        // 選択した行動をロボットに実行
        action.run(actionNum);
        // 過去の状態も合わせて，状態を更新する
        updateState();

        // 過去2回の行動を更新する
        updateAction(actionNum);

        // 行動後の新しい状態を観測
        int newState = getState();

        // 報酬を得る
        double reward = getReward();

        // Q値を更新する
        q.update(state, actionNum, newState, reward);
    }

    private void updateAction(int action) {
        a0 = a1;
        a1 = a2;
        a2 = action;
    }

    /**
     * 過去2回のセンサーの状態を更新する
     */
    private void updateState() {
        s0 = s1;
        s1 = s2;
        s2 = robot.getSensorState();
    }

    /**
     * センサーの状態を初期化する
     */
    public void initSensorState() {
        s0 = robot.getSensorState();
        s0 = robot.getSensorState();
        s0 = robot.getSensorState();
    }

    /**
     * Qテーブルの最適な行動を実行する
     * @return 最適な行動を返す
     */
    public int doBestAction(){
        int state = getState();
        int actionNum = q.selectAction(state);
        action.run(actionNum);
        updateState();
        return actionNum;
    }

    /**
     * 学習させたQテーブルを出力する
     */
    public void printQTable(){
        q.printQTable();
    }


    /**
     * 過去2回のセンサーの状態と現在のセンサー状態から，状態を算出する
     * @return
     */
    private int getState(){
        return 8*8*s0 + 8*s1 + s2;
    }


    /**
     * 報酬関数
     * @return robotの状態に応じた報酬を返す
     */
    private int getReward() {
        // ゴールにいる時最大の報酬
        if (robot.isOnGoal())
            return 20000;

        // ライン外にいる時のみ負の報酬
        if (s2 == 0)
            return -10000;

        // 過去2回と現在において，黒のライン上にいる場合
        if (s0 == 2 && s1 == 2 && s2 == 2)
            return 5000;

        // 過去1回と現在において黒のライン上にいる場合
        if (s1 == 2 && s2 == 2)
            return 1000;

        // 現在のみ黒のライン上にいる場合
        if (s2 == 2)
            return 100;

        // それ以外は少しだけ報酬を与える
        return 10;
    }

}
