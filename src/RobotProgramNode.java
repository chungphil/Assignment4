import java.util.ArrayList;
import java.util.Scanner;

/**
 * Interface for all nodes that can be executed,
 * including the top level program node
 */

interface RobotProgramNode {
	public void execute(Robot robot);
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
	public void execute(Robot robot) {

	}
}

class actNode implements RobotProgramNode{
	@Override
	public void execute(Robot robot){

	}
}

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