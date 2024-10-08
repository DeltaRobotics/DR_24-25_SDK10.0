package org.firstinspires.ftc.teamcode;


import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp(name="RI30Hdrive")
//@Disabled

public class RI30Hdrive extends LinearOpMode
{
    public boolean buttonB = true;
    public boolean buttonA = true;
    public boolean buttonC = true;
    public boolean openClose = true;
    public Servo armWrist = null;
    public Servo armFingerR = null;
    public Servo armFingerL = null;
    public Servo slideFingerR = null;
    public Servo slideFingerL = null;
    public Servo dumper = null;

    public DcMotor arm = null;
    public DcMotor slidesL = null;
    public DcMotor slidesR = null;

    public int slideEncoder = 0;
    public double speed = 0.5;

    @Override
    public void runOpMode() throws InterruptedException
    {

        robotHardware robot = new robotHardware(hardwareMap);

        robot.resetDriveEncoders();

        //dive motors
        arm = hardwareMap.dcMotor.get("frontArmMotor");
        slidesR = hardwareMap.dcMotor.get("slidesRight");
        slidesL = hardwareMap.dcMotor.get("slidesLeft");



        arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        slidesR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        slidesL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slidesR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slidesL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        slidesL.setDirection(DcMotorSimple.Direction.REVERSE);

        armWrist = hardwareMap.servo.get("frontClawWrist");
        armFingerR = hardwareMap.servo.get("frontClawRight");
        armFingerL = hardwareMap.servo.get("frontClawLeft");
        //slideFingerL = hardwareMap.servo.get("");
        //slideFingerR = hardwareMap.servo.get("");
        dumper = hardwareMap.servo.get("bucket");

        while(!isStarted() && !isStopRequested()){


            //smaller numbers go up higher
            dumper.setPosition(0.6);
            armFingerL.setPosition(0.6);
            armFingerR.setPosition(0.4);


            arm.setTargetPosition(600);
            arm.setPower(0.175);
            arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.wait(1000,robot.odometers);
            armWrist.setPosition(0);
        }



        while (opModeIsActive())
        {

            robot.mecanumDrive(gamepad1.right_stick_y, -gamepad1.right_stick_x, -gamepad1.left_stick_x, speed);

            //Bucket
            if (gamepad1.a){
                dumper.setPosition(.8);
            }
            if (gamepad1.b){
                dumper.setPosition(.65);
            }
            //Front Claw
            //if (gamepad1.x){
            //    armFingerL.setPosition(0.7);
            //    armFingerR.setPosition(0.3);
            //}
            //if (gamepad1.y){
            //    armFingerL.setPosition(0.6);
            //    armFingerR.setPosition(0.4); //smaller number goes inside
            //}

            //Slides
            if (gamepad1.a && buttonA){
                buttonA = false;
                slideEncoder -= 200;
            }
            else if (gamepad1.y && buttonB){

                buttonB = false;
                slideEncoder += 200;
            }
            if (!gamepad1.a && !buttonA){
                buttonA = true;
            }
            else if (!gamepad1.y && !buttonB){
                buttonB = true;
            }
            //One button press
            if (gamepad1.right_trigger > .5 && buttonC){
                buttonC = false;
                if (openClose) {
                    openClose = false;
                    armFingerL.setPosition(0.7);
                    armFingerR.setPosition(0.3);
                }
                else if (!openClose) {
                    openClose = true;
                    armFingerL.setPosition(0.6);
                    armFingerR.setPosition(0.4); //smaller number goes inside
                }

            }
            else if (gamepad1.right_trigger < .5 && !buttonC) {
                buttonC = true;
            }
            //Arm
            if (gamepad1.dpad_right){

                armWrist.setPosition(0);
                arm.setTargetPosition(400);
                arm.setPower(0.175);
                arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }
            if (gamepad1.dpad_down){

                armWrist.setPosition(0);
                arm.setTargetPosition(600);
                arm.setPower(0.175);
                arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }
            if (gamepad1.dpad_up){

                armWrist.setPosition(0.6);
                arm.setTargetPosition(75);
                arm.setPower(0.175);
                arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }
            //Slides stops
            if (slideEncoder < 0){
                slideEncoder = 0;
            }
            if (slideEncoder > 2200){
                slideEncoder = 2200;
            }
            //More slides stuff
            //2200 is the max for the slides
            slidesL.setTargetPosition(slideEncoder);
            slidesL.setPower(0.3);
            slidesL.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            slidesR.setTargetPosition(slideEncoder);
            slidesR.setPower(0.3);
            slidesR.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            telemetry.addData("slide encoder",slideEncoder);
            telemetry.update();

        }

    }
}
