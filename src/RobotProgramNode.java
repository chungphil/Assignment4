import java.util.ArrayList;
import java.util.Scanner;

/**
 * Interface for all nodes that can be executed,
 * including the top level program node
 */

interface RobotProgramNode {
	public void execute(Robot robot);

}

abstract class SensorNode implements RobotProgramNode{
	public int evaluate(Robot robot){return Integer.MIN_VALUE;}
}

class progNode implements RobotProgramNode{
	private ArrayList<RobotProgramNode> compProgram;
	public progNode(ArrayList<RobotProgramNode> program){
		this.compProgram = program;
	}
	@Override
	public void execute(Robot robot) {
		for(RobotProgramNode r: compProgram){
			r.execute(robot);
		}
	}

	public String toString(){
		String returnStr = "";
		for(RobotProgramNode r: compProgram){
			returnStr = returnStr + r.toString();
		}
		return returnStr;
	}

}

class stmtNode implements RobotProgramNode{

	@Override
	public void execute(Robot robot) { }

}

class actNode implements RobotProgramNode{
	@Override
	public void execute(Robot robot){

	}

}

class blockNode implements RobotProgramNode{
	private ArrayList<RobotProgramNode> blockL;
	public blockNode(ArrayList<RobotProgramNode> bList){
		this.blockL = bList;
	}
	@Override
	public void execute(Robot robot) {
		for(RobotProgramNode r: blockL){
			r.execute(robot);
		}
	}
	public String toString(){
		String returnStr = "{";
		for(RobotProgramNode r: blockL){
			returnStr = returnStr + r.toString();
		}
		return returnStr + "}";
	}

}

// loop, if, while, cond nodes

class loopNode implements RobotProgramNode{
	private RobotProgramNode loopBlock;

	public loopNode(RobotProgramNode bN){
		this.loopBlock = bN;
	}
	@Override
	public void execute(Robot robot){
		while (true){
			loopBlock.execute(robot);
		}
	}

	public String toString(){
		return "loop" + loopBlock.toString();
	}

}

class ifNode implements RobotProgramNode{
	private condNode cNode;
	private RobotProgramNode ifBlock;

	public ifNode(condNode cond, RobotProgramNode block){
		this.cNode = cond;
		this.ifBlock = block;
	}

	@Override
	public void execute(Robot robot){
		if(cNode.evaluate(robot)){
			ifBlock.execute(robot);
		}
	}

	public String toString(){
		return "if"+"("+ cNode.toString()+ ")"+ ifBlock.toString();
	}

}

class whileNode implements RobotProgramNode{
	private condNode cNode;
	private RobotProgramNode whileBlock;

	public whileNode(condNode cond, RobotProgramNode block){
		this.cNode = cond;
		this.whileBlock = block;
	}

	@Override
	public void execute(Robot robot){
		while (cNode.evaluate(robot)){
			whileBlock.execute(robot);
		}
	}

	public String toString(){
		return "while"+"("+ cNode.toString()+ ")"+ whileBlock.toString();
	}
}

class condNode implements RobotProgramNode{
	private String relop;
	private SensorNode sensor;
	private int number;

	public condNode(String rel, SensorNode sen, int num){
		this.relop = rel;
		this.sensor = sen;
		this.number = num;
	}

	@Override
	public void execute(Robot robot){ }

	public boolean evaluate(Robot robot){
		if(relop == "lt"){
			return (sensor.evaluate(robot) < this.number);
		}
		else if(relop =="gt"){
			return (sensor.evaluate(robot) > this.number);
		}
		else if(relop =="eq"){
			return (sensor.evaluate(robot) == this.number);
		}
		return false;
	}

	@Override
	public String toString() {
		return relop + "(" + sensor.toString()+ "," + Integer.toString(number) + ")";
	}
}


// Action nodes
class turnLnode implements RobotProgramNode{

	public turnLnode(Scanner s){
		s.next();
	}
	public void execute(Robot robot){
		robot.turnLeft();
	}
	public String toString(){ return "turnL;";}

}

class turnRnode implements RobotProgramNode{

	public turnRnode(Scanner s){
		s.next();
	}
	public void execute(Robot robot){
		robot.turnRight();
	}
	public String toString(){ return "turnR;";}
}

class takeFnode implements RobotProgramNode{

	public takeFnode(Scanner s){
		s.next();
	}
	public void execute(Robot robot){
		robot.takeFuel();
	}
	public String toString(){ return "takeFuel;";}
}

class moveNode implements RobotProgramNode{

	public moveNode(Scanner s){
		s.next();
	}
	public void execute(Robot robot){
		robot.move();
	}
	public String toString(){ return "move;";}

}

class waitNode implements RobotProgramNode{

	public waitNode(Scanner s){
		s.next();
	}
	public void execute(Robot robot){
		robot.idleWait();
	}
	public String toString(){ return "wait;";}

}

class turnAnode implements RobotProgramNode{

	public turnAnode(Scanner s){
		s.next();
	}
	public void execute(Robot robot){
		robot.turnAround();
	}
	public String toString(){ return "turnAround;";}

}

class shieldONnode implements RobotProgramNode{

	public shieldONnode(Scanner s){
		s.next();
	}
	public void execute(Robot robot){
		robot.setShield(true);
	}
	public String toString(){ return "shieldOn;";}

}

class shieldOFFnode implements RobotProgramNode{

	public shieldOFFnode(Scanner s){
		s.next();
	}
	public void execute(Robot robot){
		robot.setShield(false);
	}
	public String toString(){ return "shieldOff;";}

}


// Sensor Nodes - evaluate() function
class fuelLnode extends SensorNode implements RobotProgramNode{

	public fuelLnode(Scanner s){
		s.next();
	}
	public void execute(Robot robot){}

	public int evaluate(Robot robot){
		return robot.getFuel();
	}
	public String toString(){ return "fuelLeft";}
}

class oppLRnode extends SensorNode implements RobotProgramNode{

	public oppLRnode(Scanner s){
		s.next();
	}
	public void execute(Robot robot){}

	public int evaluate(Robot robot){
		return robot.getOpponentLR();
	}
	public String toString(){ return "oppLR";}
}

class oppFBnode extends SensorNode implements RobotProgramNode{

	public oppFBnode(Scanner s){
		s.next();
	}
	public void execute(Robot robot){}

	public int evaluate(Robot robot){
		return robot.getOpponentFB();
	}
	public String toString(){ return "oppFB";}
}

class numBnode extends SensorNode implements RobotProgramNode{

	public numBnode(Scanner s){
		s.next();
	}
	public void execute(Robot robot){}

	public int evaluate(Robot robot){
		return robot.numBarrels();
	}
	public String toString(){ return "numBarrels";}
}

class barLRnode extends SensorNode implements RobotProgramNode{

	public barLRnode(Scanner s){
		s.next();
	}
	public void execute(Robot robot){}

	public int evaluate(Robot robot){
		return robot.getClosestBarrelLR();
	}
	public String toString(){ return "barrelLR";}
}

class barFBnode extends SensorNode implements RobotProgramNode{

	public barFBnode(Scanner s){
		s.next();
	}
	public void execute(Robot robot){}

	public int evaluate(Robot robot){
		return robot.getClosestBarrelFB();
	}
	public String toString(){ return "barrelFB";}
}

class wallDnode extends SensorNode implements RobotProgramNode{

	public wallDnode(Scanner s){
		s.next();
	}
	public void execute(Robot robot){}

	public int evaluate(Robot robot){
		return robot.getDistanceToWall();
	}
	public String toString(){ return "wallDist";}
}