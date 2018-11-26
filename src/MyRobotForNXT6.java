/**
 * map6における実機NXT用ロボットクラス
 */
public class MyRobotForNXT6 extends Robot {

    /** leJOS での起動用 main 関数 */
    static void main(String[] args) { 
        try {
            // 時間計測
            Long time = System.currentTimeMillis();

            // ロボットオブジェクトを生成して実行
            new MyRobotForNXT6().run();

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
        String str = "5556555555555555555655555555665565555655555555555555555555555555555555455556555555565555545555545545555445454545554555555566466656555565555555555555555566527937656555556555554555555444344454554555565665565556555556558693635556555655555555655555555555555555555555655555555655555555555565555555555555555655555554546445555656565655555555455555545555555454545455545554644555555655655565555555454554545545554555545555556656666665565655556555555556555555656665655455555555";
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
