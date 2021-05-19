/**
 * Interface for all nodes that can be executed,
 * including the top level program node
 */

interface RobotProgramNode {
	public void execute(Robot robot);
}

class progNode implements RobotProgramNode{

	@Override
	public void execute(Robot robot) {

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

class TurnNode implements RobotProgramNode{
	String LR;
	public TurnNode(String LR){
		this.LR = LR;
	}

	public void execute(Robot robot) {
		if (LR == "L"){
			robot.turnLeft();
		}
		else if (LR == "R") {
			robot.turnRight();
		}
		else{
			robot.turnAround();
		}
	}
}