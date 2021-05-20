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
	@Override
	public void execute(Robot robot){

	}
}

class blockNode implements RobotProgramNode{
	@Override
	public void execute(Robot robot){

	}
}

//class TurnNode implements RobotProgramNode{
//	String LR;
//	public TurnNode(String LR){
//		this.LR = LR;
//	}
//
//	public void execute(Robot robot) {
//		if (LR == "L"){
//			robot.turnLeft();
//		}
//		else {
//			robot.turnRight();
//		}
//
//	}
//}

class turnLnode implements RobotProgramNode{

	public turnLnode(Scanner s){
		s.next();
	}
	public void execute(Robot robot){
		robot.turnLeft();
	}
}

class turnRnode implements RobotProgramNode{

	public turnRnode(Scanner s){
		s.next();
	}
	public void execute(Robot robot){
		robot.turnRight();
	}
}

class takeFnode implements RobotProgramNode{

	public takeFnode(Scanner s){
		s.next();
	}
	public void execute(Robot robot){
		robot.takeFuel();
	}
}

class moveNode implements RobotProgramNode{

	public moveNode(Scanner s){
		s.next();
	}
	public void execute(Robot robot){
		robot.move();
	}
}

class waitNode implements RobotProgramNode{

	public waitNode(Scanner s){
		s.next();
	}
	public void execute(Robot robot){
		robot.idleWait();
	}
}