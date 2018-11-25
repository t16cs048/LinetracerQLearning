public class NXTDoAction3 {
    private MyRobotForNXT6 robot;

    public NXTDoAction3(MyRobotForNXT6 r){
        this.robot = r;
    }

    public void run(int action){
        int angle = 15 * (action - 5);

        double speed = 0;
        if (action < 6)
            speed = 0.2 * action;
        else
            speed = 2.0 - (0.2 * action);

        robot.rotate(angle);
        robot.forward(speed);
    }
}
