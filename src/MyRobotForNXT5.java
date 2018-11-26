/**
 * map5における実機NXT用ロボットクラス
 */
public class MyRobotForNXT5 extends Robot {

    /** leJOS での起動用 main 関数 */
    static void main(String[] args) {
        try {
            // 時間計測
            Long time = System.currentTimeMillis();

            // ロボットオブジェクトを生成して実行
            new MyRobotForNXT5().run();

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
        String str = "555555555555565554555555555555555555555555455555554555555554555555554555555555455555555455555555545555555554555555455555555555455555555455555554555555555554555555565555455555556555455555565555455555556555554555555565555455555555655554555555565555455555554555555455554555555545555554555555455555554555554555554555555545555554555555555556555455555555555";
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

