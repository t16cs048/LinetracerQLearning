/**
 * ロボットクラスの作成例：単純なライントレーサーロボット
 */
public class MyRobot extends Robot {

    /**
     * 実行用関数
     */
    public void run() throws InterruptedException {
        try {
            // step 1: Q学習する
            // QLearningのインスタンスを作る
            int states = 8; // 状態数
            int actions = 13; // 行動数
            double alpha = 0.5; // 学習率
            double gamma = 0.5; // 割引率
            QLearning q1 = new QLearning(states, actions, alpha, gamma);

            int trials = 500; // 強化学習の試行回数
            int steps = 500; // 1試行あたりの最大ステップ数

            for (int t = 1; t <= trials; t++) { // 試行回数だけ繰り返し

                /* ロボットを初期位置に戻す */
                init();

                for (int s = 0; s < steps; s++) { // ステップ数だけ繰り返し
                    int state = getState(); // 今の状態
                    // epsilon-Greedy 法により行動を選択
                    int action = q1.selectAction(state, 0.3);

                    // 選択した行動をロボットに実行
                    doAction2(action);

                    // 行動後の新しい状態を観測
                    int newState = getState();
                    // 報酬を得る
                    double reward = getReward2(action, newState);

                    // Q値を更新する
                    q1.update(state, action, newState, reward);
                }
            }

            q1.printQTable();

            // step 2: 学習したQテーブルの最適政策に基づいてスタート位置からゴール位置まで移動
            // ロボットを初期位置に戻す
            init();

            while (true) {
                // デバッグ用
                System.out.println("A:" + getColor(LIGHT_A) + " B:" + getColor(LIGHT_B) + " C:" + getColor(LIGHT_C));

                // 現在の状態を取得
                int state = getState();

                // Qテーブルる最適政策に基づいて行動を決定する
                int action = q1.selectAction(state);

                // ロボットに行動させる
                doAction2(action);

                // 速度調整＆画面描画
                delay();

                // ゴールに到達すれば終了
                if (isOnGoal())
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     *
     * @return
     */
    private int getState(){
        // 戻り値の変数
        int state = 0;

        // 各センサーが読み取った値を取得
        int lA = getColor(LIGHT_A);
        int lB = getColor(LIGHT_B);
        int lC = getColor(LIGHT_C);

        // 状態を定義
        if(lA == WHITE && lB == WHITE && lC == WHITE){
            state = 0;
        }
        else if(lA == WHITE && lB == WHITE && lC == BLACK){
            state = 1;
        }
        else if(lA == WHITE && lB == BLACK && lC == WHITE){
            state = 2;
        }
        else if(lA == WHITE && lB == BLACK && lC == BLACK){
            state = 3;
        }
        else if(lA == BLACK && lB == WHITE && lC == WHITE){
            state = 4;
        }
        else if(lA == BLACK && lB == WHITE && lC == BLACK){
            state = 5;
        }
        else if(lA == BLACK && lB == BLACK && lC == WHITE){
            state = 6;
        }
        else if(lA == BLACK && lB == BLACK && lC == BLACK){
            state = 7;
        }

        return state;
    }


    private void doAction1(int action){
        switch (action){
            case 1:
                // 左90度回転後，進む
                rotateLeft(90);
                forward(2);
                break;
            case 2:
                // 左67.5度回転後，進む
                rotateLeft(67.5);
                forward(2);
                break;
            case 3:
                // 左45度回転後，進む
                rotateLeft(45);
                forward(2);
                break;
            case 4:
                // 左22.5度回転後，進む
                rotateLeft(22.5);
                forward(2);
                break;
            case 5:
                // 全身する
                forward(2);
                break;
            case 6:
                // 右22.5度回転後，進む
                rotateRight(22.5);
                forward(2);
                break;
            case 7:
                // 右45度回転後，進む
                rotateRight(45);
                forward(2);
                break;
            case 8:
                // 右67.5度回転後，進む
                rotateRight(67.5);
                forward(2);
                break;
            case 9:
                // 右90度回転後，進む
                rotateRight(90);
                forward(2);
                break;
            case 10:
                // その場で左90度回転
                rotateLeft(90);
                break;
            case 11:
                // その場で左60度回転
                rotateLeft(60);
                break;
            case 12:
                // その場で左30度回転
                rotateLeft(30);
                break;
            case 13:
                // その場で右30度回転
                rotateRight(30);
                break;
            case 14:
                // その場で右60度回転
                rotateRight(60);
                break;
            case 15:
                // その場で右90度回転
                rotateRight(90);
                break;

        }
    }

    private void doAction2(int action) {
        int angle = 15 * (action - 7);
        rotate(angle);
        forward(1);
    }

    private double getReward1 (int action, int state){
        // ゴールにいれば最大
        if (isOnGoal())
            return 100000;

        // ライン上にいなければ負
        if (state == 0)
            return -1000;

        // ただの回転であれば0
        if (action > 9 && action < 16)
            return 0;

        // それ以外，つまりライン上にいれば正
        return 10;
    }

    private double getReward2(int action, int state) {
        if (isOnGoal())
            return 100000;

        if (state == 0)
            return -100;

        return 100;
    }

}
