package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;

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
		
    }
    public void teleopInit(){
        stopAll();
    }
    public void teleopPeriodic() {
        double Vx = input.getX();
	double Vy = input.getY();
        double w = input.getTwist();
        System.out.println("x: "+Vx+"     y: "+Vy+"     w: "+w);
        controlDrive(Vx, Vy, w);
        
        /*
        boolean shooterOn = input.getRawButton(4);
        boolean shooterOff = input.getRawButton(5);
        double shooterSpeed = input.getZ();
        controlShooter(shooterOn, shooterOff, shooterSpeed);
        
        boolean collectorIn = input.getRawButton(2);
        boolean collectorOut = input.getRawButton(3);
        controlCollectorAndScrew(collectorIn, collectorOut);
        
        boolean indexerOn = input.getTrigger();
        controlIndexer(indexerOn);
        */
    }
    public void testInit(){
	
    }
    public void testPeriodic() {
	
    }
    public void disabledInit(){
        
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
    /*
    public void controlShooter(boolean on, boolean off, double speed){
        if(on){
            shooter.set(speed);
        }else if(off){
            shooter.set(0.0);
        }
    }
    public void controlCollectorAndScrew(boolean in, boolean out){
        collector.set(in?1.0:out?-1.0:0.0);
    }
    public void controlIndexer(boolean on){
        indexer.set(on?1.0:0.0);
    }
    */
}