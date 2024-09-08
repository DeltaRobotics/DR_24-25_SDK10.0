package org.firstinspires.ftc.teamcode;


import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp(name="RI30Hdrive")
//@Disabled

public class RI30Hdrive extends LinearOpMode
{

    public Servo armWrist = null;
    public Servo armFingerR = null;
    public Servo armFingerL = null;
    public Servo slideFingerR = null;
    public Servo slideFingerL = null;
    public Servo dumper = null;

    public DcMotor arm = null;
    public DcMotor slideL = null;
    public DcMotor slideR = null;

    public double speed = 0.5;

    @Override
    public void runOpMode() throws InterruptedException
    {

        robotHardware robot = new robotHardware(hardwareMap);

        robot.resetDriveEncoders();

        //dive motors
        arm = hardwareMap.dcMotor.get("frontArmMotor");
        slideR = hardwareMap.dcMotor.get("slidesRight");
        slideL = hardwareMap.dcMotor.get("slidesLeft");

        arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        armWrist = hardwareMap.servo.get("frontClawWrist");
        armFingerR = hardwareMap.servo.get("frontClawRight");
        armFingerL = hardwareMap.servo.get("frontClawLeft");
        //slideFingerL = hardwareMap.servo.get("");
        //slideFingerR = hardwareMap.servo.get("");
        dumper = hardwareMap.servo.get("bucket");

        while(!isStarted() && !isStopRequested()){


            //smaller numbers go up higher
            dumper.setPosition(0.65);
            armFingerL.setPosition(0.6);
            armFingerR.setPosition(0.4);


            arm.setTargetPosition(400);
            arm.setPower(0.175);
            arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.wait(200,robot.odometers);
            armWrist.setPosition(0);
        }

        while (opModeIsActive())
        {

            robot.mecanumDrive(gamepad1.right_stick_y, -gamepad1.right_stick_x, gamepad1.left_stick_x, speed);


            if (gamepad1.a){

                dumper.setPosition(.8);
            }

            if (gamepad1.b){

                dumper.setPosition(.65);
            }

            if (gamepad1.x){

                armFingerL.setPosition(0.7);
                armFingerR.setPosition(0.3);


            }
            if (gamepad1.y){

                armFingerL.setPosition(0.6);
                armFingerR.setPosition(0.4); //smaller number goes inside


            }

            if (gamepad1.dpad_right){

                armWrist.setPosition(0);
                arm.setTargetPosition(400);
                arm.setPower(0.175);
                arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }
            if (gamepad1.dpad_down){

                armWrist.setPosition(0);
                arm.setTargetPosition(450);
                arm.setPower(0.175);
                arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }
            if (gamepad1.dpad_up){

                armWrist.setPosition(0.6);
                arm.setTargetPosition(75);
                arm.setPower(0.175);
                arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }


        }

    }
}
