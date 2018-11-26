/**
 * map3における実機NXT用ロボットクラス
 */
public class MyRobotForNXT3 extends Robot {

    /** leJOS での起動用 main 関数 */
    static void main(String[] args) {
        try {
            // 時間計測
            Long time = System.currentTimeMillis();

            // ロボットオブジェクトを生成して実行
            new MyRobotForNXT3().run();

            time = (System.currentTimeMillis() - time) / 1000;
            System.out.println("Time = "+time.intValue() + "sec");

            // 7秒待ってから停止
            Thread.sleep(7000);
        }catch (InterruptedException e) {
            ;
        }
    }

    /**
     * 実行用関数
     */
    public void run() throws InterruptedException {
        /* 学習した最適政策を表す配列 */
        String str = "55555555555555555555555555555555555555555555555555555555555555555555754765745555655555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555";
        String[] ary = str.split("");

        // ロボットの行動を定義したインスタンスを作成
        AbsAction action = new Action(this);


        for (int i = 0; i < ary.length; i++){
            // 最適政策を実行する
            int actionNum = Integer.parseInt(ary[i]);
            action.run(actionNum);

            // 速度調整＆画面描画
            delay();
        }

    }
}