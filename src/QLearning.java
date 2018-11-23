import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Ｑ学習を行うクラス
 */
public class QLearning {

    /**
     * Ｑ学習を行うオブジェクトを生成する
     *
     * @param states  状態数
     * @param actions 行動数
     * @param alpha   学習率（0.0〜1.0）
     * @param gamma   割引率（0.0〜1.0）
     */
    public QLearning(int states, int actions, double alpha, double gamma) {
        this.qTable = new double[states][actions];
        this.alpha = alpha;
        this.gamma = gamma;
        this.states = states;
        this.actions = actions;
    }

    /**
     * epsilon-Greedy 法により行動を選択する
     *
     * @param state   現在の状態
     * @param epsilon ランダムに行動を選択する確率（0.0〜1.0）
     * @return 選択された行動番号
     */
    public int selectAction(int state, double epsilon) {
        // ランダムジェネレーターのインスタンスを生成
        Random rnd = new Random();
        int action; // 行動を初期化

        // epsilon による選択する行動決定規則
        if (epsilon > rnd.nextDouble()){ // ランダムな行動を選択
            action = rnd.nextInt(actions) + 1;
        }
        else { // Gready法により行動を選択
            action = selectAction(state);
        }

        return action;
    }

    /**
     * Greedy 法により行動を選択する
     *
     * @param state 現在の状態
     * @return 選択された行動番号
     */
    public int selectAction(int state) {
        // 与えられた状態において，最大となるQ値を探す
        double max = qTable[state][0];
        for(double q: qTable[state]){
            if (max <= q)
                max = q;
        }

        // Q値が最大となる行動が複数ある場合，その中からランダムに１つの行動を選択する
        // 最大となる行動を格納しておくリストを作成
        ArrayList<Integer> maxActions = new ArrayList<>();
        for(int i = 0; i < qTable[state].length; i++){
            if (qTable[state][i] == max) {
                maxActions.add(i+1);
            }
        }

        // 選択する行動のリストの中から，ランダムに１つ取り出す
        Collections.shuffle(maxActions);
        return maxActions.get(0);
    }

    /**
     * Ｑ値を更新する
     *
     * @param before 状態
     * @param action 行動
     * @param after  遷移後の状態
     * @param reward 報酬
     */
    public void update(int before, int action, int after, double reward) {
        qTable[before][action-1] = qTable[before][action-1] + alpha * (reward + getMaxQ(after) - qTable[before][action-1]);
    }


    //フィールド
    private double qTable[][] = null;
    private double alpha = 0;
    private double gamma = 0;
    private int actions = 0;
    private int states = 0;

    /**
     * Qテーブルのにおいて，状態Sの時の行動で最大となる報酬が期待される時の，報酬を求める
     * @param state 求めたい状態
     * @return stateの時の報酬の最大値
     */
    private double getMaxQ(int state){
        double max = 0;

        for(double q: qTable[state]){
            if (max < q){
                max = q;
            }
        }
        return max;
    }

    public void printQTable(){
        for (int i = 0; i < states; i++){
            for (int j = 0; j < actions; j++){
                System.out.print(qTable[i][j] + " ");
            }
            System.out.println("");
        }
    }
}

