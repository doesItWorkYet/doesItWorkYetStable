/*
Copyright (c) 2016 Robert Atkinson

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Robert Atkinson nor the names of his contributors may be used to
endorse or promote products derived from this software without specific prior
written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESSFOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="ClawTest", group="Testing")  // @Autonomous(...) is the other common choice
//@Disabled
public class clawTest extends LinearOpMode {

    /* Declare OpMode members. */
    private ElapsedTime runtime = new ElapsedTime();
    DcMotor motor1 = null;
    DcMotor leftMotor = null;
    DcMotor rightMotor = null;
    DcMotor scoopPan = null;
    Servo servo2 = null;
    public final int TICKS_PER_REV = 1440;



    @Override
    public void runOpMode() throws InterruptedException {
        motor1 = hardwareMap.dcMotor.get("motor1");
        scoopPan = hardwareMap.dcMotor.get("scoopPan");
        rightMotor = hardwareMap.dcMotor.get("leftMotor");
        leftMotor = hardwareMap.dcMotor.get("rightMotor");
        leftMotor.setDirection(DcMotor.Direction.REVERSE);
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        scoopPan.setPower(0.00);
        motor1.setPower(0.00);
        double x = 0.00;
        waitForStart();
        runtime.reset();
        boolean rightReverse = false;
        boolean leftReverse = false;
        setDcMotorRPM(leftMotor, 0);
        setDcMotorRPM(rightMotor, 0);

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.update();
            if (gamepad1.dpad_left) {
                scoopPan.setPower(0.20);
            }
            if (gamepad1.dpad_right) {
                scoopPan.setPower(-0.20);
            }
            if (gamepad1.dpad_right == false & gamepad1.dpad_left == false) {
                scoopPan.setPower(0);
            }


            int gearState = 1;
            if (gamepad1.dpad_up) {
                if (gearState < 5) {
                    gearState += 1;
                }
            }
            if (gamepad1.dpad_down) {
                if (gearState > 1) {
                    gearState -= 1;
                }
            }
            if (gamepad1.right_trigger > .2) {
                rightReverse = true;
            } else if (gamepad1.right_trigger <= .2) {
                rightReverse = false;
            }
            if (gamepad1.left_trigger > .2) {
                leftReverse = true;
            } else if (gamepad1.left_trigger <= .2) {
                leftReverse = false;
            }
            if (gamepad1.right_bumper) {
                rightMotor.setPower(0.2 * gearState);
            }
            if (gamepad1.left_bumper) {
                leftMotor.setPower(0.2 * gearState);
            }
            if (rightReverse) {
                rightMotor.setPower(-0.2 * gearState);
            }
            if (leftReverse) {
                leftMotor.setPower(-0.2 * gearState);
            }

            if (gamepad1.right_bumper == false && rightReverse == false) {
                rightMotor.setPower(0);
            }
            if (gamepad1.left_bumper == false && leftReverse == false) {
                leftMotor.setPower(0);
            }

            if (gamepad1.a) {
                motor1.setPower(0.20);
            }
            if (gamepad1.b) {
                motor1.setPower(-0.20);
            }
            if (gamepad1.a == false & gamepad1.b == false) {
                motor1.setPower(0);
            }

            idle(); // Always call idle() at the bottom of your while(opModeIsActive()) loop
        }
    }

    public void setDcMotorRPM(DcMotor motor, double rpm){
        if (rpm > 120) {
            rpm = 120;
        }
        else if (rpm < 0) {
            rpm = 0;
        }
        int rpmToTicksPerMinute = (int) (rpm*TICKS_PER_REV + 0.5);
        motor.setMaxSpeed(rpmToTicksPerMinute);
        motor.setPower(1.0);
    }

    public void setDcMotorRPMReverse(DcMotor motor, double rpm){
        if (rpm > 120) {
            rpm = 120;
        }
        else if (rpm < 0) {
            rpm = 0;
        }
        int rpmToTicksPerMinute = (int) (rpm*TICKS_PER_REV + 0.5);
        motor.setMaxSpeed(rpmToTicksPerMinute);
        motor.setPower(-1.0);
    }

}
