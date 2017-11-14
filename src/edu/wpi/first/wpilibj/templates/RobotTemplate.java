package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Watchdog;

public class RobotTemplate extends IterativeRobot {
    /**
	 * Ports:
	 * 1 - shooter
	 * 2 - left
	 * 3 - right
	 * 4 - center
	 * 5 - screw & collector
	 * 6 - indexer
     */
    Talon shooter = new Talon(1);
    Talon[]drive = new Talon[]{
        new Talon(3), //right
        new Talon(2), //left
        new Talon(4), //back/centre
    };
    Talon collector = new Talon(5);
    Talon indexer = new Talon(6);
    
    Joystick input = new Joystick(1);
    
    public void robotInit() {
        System.out.println("I Took! -- 7/24,7:11");
    }
    public void autonomousInit(){
        
    }
    public void autonomousPeriodic() {
	Watchdog.getInstance().feed();
    }
    public void teleopInit(){
        stopAll();
    }
    public void teleopPeriodic() {
        Watchdog.getInstance().feed();
        
        double Vx = input.getX();
	double Vy = input.getY();
        double Vtwist = -input.getThrottle();
        
        double z = input.getZ();
        
        System.out.println("x: "+Vx+" y: "+Vy+" z: "+z+" q: "+Vtwist);
       
        controlDriveNoNorm(Vx, Vy, Vtwist);
        
        
        boolean shooterOn = input.getRawButton(5);
        boolean shooterOff = input.getRawButton(6);
        double shooterSpeed = (z+1)/2;
        controlShooter(shooterOn, shooterOff, shooterSpeed);
        
        boolean collectorIn = input.getRawButton(7);
        boolean collectorOut = input.getRawButton(8);
        double collectorSpeed = 0.4;
        controlCollectorAndScrew(collectorIn, collectorOut, collectorSpeed);
        
        boolean indexerOn = input.getTrigger();
        controlIndexer(indexerOn);
        
    }
    public void testInit(){
	
    }
    public void testPeriodic() {
	Watchdog.getInstance().feed();
    }
    public void disabledInit(){
        
    }
    public void disabledPeriodic(){
        Watchdog.getInstance().feed();
    }
    public void stopAll(){
            //shooter.set(0);
            drive[0].set(0);
            drive[1].set(0);
            drive[2].set(0);
            //collector.set(0);
            //indexer.set(0);
    }
    public void controlDrive(double x, double y, double w){
        final double[][]driveMat = new double[][]{
            {-1,  Math.sqrt(3), 1}, 
            {-1, -Math.sqrt(3), 1}, 
            
            { 2,            0 , 1}
        };
        final double norm = 2 + Math.sqrt(3);
        for(int i=0;i<3;i++){
            double[]v = driveMat[i];
            double pow = x*v[0] + y*v[1] + w*v[2];
            drive[i].set(pow/norm);
        }
    }
    
    public void controlDriveNoNorm(double x, double y, double w){
        final double[][]driveMat = new double[][]{
            {-1,  Math.sqrt(3), 1}, 
            {-1, -Math.sqrt(3), 1}, 
            
            { 2,            0 , 1}
        };
        final double norm = 2 + Math.sqrt(3);
        for(int i=0;i<3;i++){
            double[]v = driveMat[i];
            double pow = x*v[0] + y*v[1] + w*v[2];
            drive[i].set(pow);
        }
    }
    
    public void controlDriveMaxNorm(double x, double y, double w){
            final double[][]driveMat = new double[][]{
                {-1,  Math.sqrt(3), 1}, 
                {-1, -Math.sqrt(3), 1}, 
                { 2,            0 , 1}
            };
            double[] power = new double[3];
            for(int i=0;i<3;i++){
                power[i] = x*driveMat[i][0] + y*driveMat[i][1] + w*driveMat[i][2]; 
            }
            double norm = largestOfThree(power[0],power[1],power[2]);

            for(int i=0;i<3;i++){
                    drive[i].set(power[i]/norm);
            }
        }

    public double largestOfThree(double a, double b, double c){
            return  1>(c>(a>b?a:b)?c:(a>b?a:b))?1:(c>(a>b?a:b)?c:(a>b?a:b));
    }
  boolean sticky = false;
  
    public void controlShooter(boolean on, boolean off, double speed){
        if (on) {
            sticky = true;
        } else if (off) {
            sticky = false;
        }
        if(!sticky){
            shooter.set(0.0);
        }else {
            System.out.println(-speed);
            shooter.set(-speed);
        }
    }
    
    public void controlCollectorAndScrew(boolean in, boolean out, double speed){
        collector.set(in?speed:out?-speed:0.0);
    }
    public void controlIndexer(boolean on){
        indexer.set(on?0.4:0.01);
    }
    
}