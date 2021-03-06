import java.util.ArrayList;

/**
 * ロボットクラスの作成例：単純なライントレーサーロボット
 */
public class MyRobot extends Robot {
    /**
     * 実行用関数
     */
    public void run() throws InterruptedException {
        // step 1: Q学習する
        // QLearningのインスタンスを作る
        // LinetracerQLearning3 q = new LinetracerQLearning3(this);
        // LinetracerQLearning4 q = new LinetracerQLearning4(this);
        LinetracerQLearning5 q = new LinetracerQLearning5(this);

        int trials = 1000; // 強化学習の試行回数
        int steps = 1500; // 1試行あたりの最大ステップ数

        for (int t = 1; t <= trials; t++) { // 試行回数だけ繰り返し
            init();
            for (int s = 0; s < steps; s++) { // ステップ数だけ繰り返し
                q.learning();
            }
        }

        q.printQTable();

        // step 2: 学習したQテーブルの最適政策に基づいてスタート位置からゴール位置まで移動
        // ロボットを初期位置に戻す
        init();

        // 最初は，過去の状態は今のセンサーの状態と同じ
        q.initSensorState();

        // 行動を記録しておく変数
        ArrayList<Integer> selectActions = new ArrayList<>();

        while (true) {
            // 最適政策を実行する
            int action = q.doBestAction();

            // 行動をスタックしていく
            selectActions.add(action);

            // 速度調整＆画面描画
            delay();

            // ゴールに到達すれば終了
            if (isOnGoal()) {
                for ( int a: selectActions){
                    System.out.print(a);
                }
                break;
            }
        }
    }

    /**
     *
     * @return
     */
    public int getSensorState(){
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
        // 青を識別する時，以下の状態が加わる
        else if(lA == WHITE && lB == BLUE && lC == WHITE){
            state = 8;
        }
        else if(lA == WHITE && lB == BLUE && lC == BLACK){
            state = 9;
        }
        else if(lA == BLACK && lB == BLUE && lC == WHITE){
            state = 10;
        }
        else if(lA == BLACK && lB == BLUE && lC == BLACK){
            state = 11;
        }

        return state;
    }

}
