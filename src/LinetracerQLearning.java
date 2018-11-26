/**
 * ライントレーサーのためのQ学習を行うインスタンス
 * 青マーク対応版
 * 過去の状態を2回もつ
 * 行動もさせる
 */
public class LinetracerQLearning implements ILinetracerQLearning {
    private MyRobot robot; // 呼び出し元のMyRobotのインスタンス
    private AbsAction action; // 実行する行動を定義したインスタンス
    private QLearning q; // Qテーブルの保持，更新するインスタンス
    private int s0; // 過去の状態s0
    private int s1; // 過去の状態s1
    private int s2; // 現在の状態s2
    private int previousStatesNum; // 過去の状態を何個まで保持しておく数
    private int previousStates[]; // 過去の状態を保持する配列


    /**
     * コンストラクタ
     * @param r 呼び出し元のMyRobotのインスタンス
     */
    public LinetracerQLearning(MyRobot r){
        this.robot = r;
        this.action = new Action(r);

        int states = 8*8*8; // 状態数
        int actions = action.getActionNum(); // 行動数
        double alpha = 0.5; // 学習率
        double gamma = 0.5; // 割引率
        this.q = new QLearning(states, actions, alpha, gamma);

        this.previousStatesNum = 7; // 7ステップ前まで保持するように指定
        this.previousStates = new int[previousStatesNum]; // 7ステップ前までの状態を保持
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

        // 行動後の新しい状態を観測
        int newState = getState();

        // 報酬を得る
        double reward = getReward();

        // Q値を更新する
        q.update(state, actionNum, newState, reward);
    }



    /**
     * 過去2回のセンサーの状態を更新する
     * 過去状態s0: 保持している中で最古の状態
     * 過去状態s1: 保持している中の中央の状態
     * 現在状態s2: 現在の状態
     */
    private void updateState() {
        for (int i = 0; i < previousStatesNum - 1; i++){
            previousStates[i] = previousStates[i+1];
        }
        previousStates[previousStatesNum-1] = robot.getSensorState();

        s0 = previousStates[0]; // 保持している過去の状態の中で，最古のもの
        s1 = previousStates[previousStatesNum/2]; // 保持している過去の状態の中で，中間のもの
        s2 = previousStates[previousStatesNum-1]; // 最新の状態
    }

    /**
     * センサーの状態を初期化する
     */
    public void initSensorState() {
        for (int i = 0; i < previousStatesNum; i++){
            previousStates[i] = robot.getSensorState();
        }
        s0 = robot.getSensorState();
        s1 = robot.getSensorState();
        s2 = robot.getSensorState();
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


    private int getReward() {
        // ゴールにいる時最大の報酬を与える
        if (robot.isOnGoal())
            return 20000;

        // 過去2回と現在において，黒のライン上にいる場合
        if (s0 == 2 && s1 == 2 && s2 == 2)
            return 10000;


        // 過去1回と現在において黒のライン上にいる場合
        if (s1 == 2 && s2 == 2)
            return 1000;

        // 現在のみ黒のライン上にいる場合
        if (s2 == 2)
            return 100;


        // 上記以外は負の報酬を与える
        return -10000;
    }

}
