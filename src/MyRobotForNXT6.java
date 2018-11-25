import java.util.Arrays;

/**
 * 実機NXT用ロボットクラス
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
        String str = "555835555555555555555835555555837379246383555555835555555555555555555555555556355558355555555555835635555635635563556364446446446445555583547283889245646455555558355555555555558373884826545583555555555555563644165148355563555558392676167614845737924515388462894845772382258856565635555555583555556355583555555555555558355555555835555555555583555555555555555555558355556364446464692464832289484555555635555563555555556364446446445563555635635583558355835583555555563644644455635563555563555555558379267646924663835583558355555558355558322894874636563555555555";
        String[] ary = str.split("");
        NXTDoAction3 doAction = new NXTDoAction3(this);

        /*
        Arrays.stream(ary).forEach(a -> {
            // 最適政策を実行する
            int action = Integer.parseInt(a);
            doAction.run(action);

            // 速度調整＆画面描画
            delay();

            // ゴールに到達すれば終了
            if (isOnGoal()) {
                break;
            }

        });
        */


        for (int i = 0; i < ary.length; i++){
            // 最適政策を実行する
            int action = Integer.parseInt(ary[i]);
            doAction.run(action);

            // 速度調整＆画面描画
            delay();
        }

        /*
        while (true) {
            // 最適政策を実行する
            int action = bestActions;
            doAction.run(action);

            // 速度調整＆画面描画
            delay();

            // ゴールに到達すれば終了
            if (isOnGoal()) {
                break;
            }
        }
        */
    }
}
