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
	private RobotProgramNode elseBlock;

	public ifNode(condNode cond, RobotProgramNode block, RobotProgramNode eblock){
		this.cNode = cond;
		this.ifBlock = block;
		this.elseBlock = eblock;
	}

	@Override
	public void execute(Robot robot){
		if(cNode.evaluate(robot)){
			ifBlock.execute(robot);
		}else{
			if(elseBlock != null){
					elseBlock.execute(robot);
			}
		}
	}

	public String toString(){
		String ifStr = "if"+"("+ cNode.toString()+ ")"+ ifBlock.toString();
		if(elseBlock != null)
			ifStr += "else" + elseBlock.toString();
		return ifStr;
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
	private condNode Cnode1;
	private condNode Cnode2;
	private expNode Enode1;
	private expNode Enode2;

	public condNode(String rel, expNode e1, expNode e2){
		this.relop = rel;
		this.Enode1 = e1;
		this.Enode2 = e2;
	}

	public condNode(String rel, condNode c1, condNode c2){
		this.relop = rel;
		this.Cnode1 = c1;
		this.Cnode2 = c2;
	}

	@Override
	public void execute(Robot robot){ }

	public boolean evaluate(Robot robot){

		if(relop.matches("lt")){

			return (Enode1.evaluate(robot) < Enode2.evaluate(robot));
		}
		else if(relop.matches("gt")){

			return (Enode1.evaluate(robot) > Enode2.evaluate(robot));
		}
		else if(relop.matches("eq")){

			return (Enode1.evaluate(robot) == Enode2.evaluate(robot));
		}
		else if(relop.matches("and")){

			return (Cnode1.evaluate(robot) && Cnode2.evaluate(robot));
		}
		else if(relop.matches("or")){

			return (Cnode1.evaluate(robot) || Cnode2.evaluate(robot));
		}
		else if(relop.matches("not")){

			return (!Cnode1.evaluate(robot));
		}

		return false;
	}

	@Override
	public String toString() {
		if(relop.matches("lt|gt|eq")){
			return relop + "(" + Enode1.toString()+ "," + Enode2.toString() + ")";
		} else if(relop.matches("and|or")){
			return relop + "(" + Cnode1.toString() + "," + Cnode2.toString() + ")";
		}
		return relop + "(" + Cnode1.toString() + ")";
	}
}

class expNode implements RobotProgramNode{
	private String opSign;
	private expNode expN1;
	private expNode expN2;
	private int numb = Integer.MIN_VALUE;
	private SensorNode sens;

	public expNode(String op, expNode exp1, expNode exp2){
		this.opSign = op;
		this.expN1 = exp1;
		this.expN2 = exp2;
	}

	public expNode(int n){this.numb = n; this.opSign = "";}

	public expNode(SensorNode sn){this.sens = sn; this.opSign = "";}

	@Override
	public void execute(Robot robot){ }

	public int evaluate(Robot robot){

		if(opSign.matches("add")){
			int result = expN1.evaluate(robot) + expN2.evaluate(robot);
			return result;
		}
		else if(opSign.matches("sub")){
			int result = expN1.evaluate(robot) - expN2.evaluate(robot);
			return result;
		}
		else if(opSign.matches("mul")){
			int result = expN1.evaluate(robot) * expN2.evaluate(robot);
			return result;
		}
		else if(opSign.matches("div")) {
			int result = expN1.evaluate(robot) / expN2.evaluate(robot);
			return result;
		}
		else if(numb > Integer.MIN_VALUE){
			return numb;
		}

		return sens.evaluate(robot);
	}

	@Override
	public String toString() {
		if(numb > Integer.MIN_VALUE){return Integer.toString(numb);}
		else if(opSign.matches("add|sub|mul|div")){
			return opSign + "(" + expN1.toString()+ "," + expN2.toString() + ")";
		}
		return sens.toString();
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
	private expNode exp= null;

	public moveNode(Scanner s,expNode e){
		s.next();
		this.exp = e;
	}
	public void execute(Robot robot){
		if(exp != null){
			int nmove = exp.evaluate(robot);
			for(int i = 1;i <nmove; i++){
				robot.move();
			}
		}
		robot.move();
	}
	public String toString(){
		String moveexp = "move";
		if(exp != null){
			moveexp += ("(" + exp.toString() + ")");
		}
		return moveexp + ";";
	}

}

class waitNode implements RobotProgramNode{
	private expNode exp = null;
	public waitNode(Scanner s, expNode e){
		s.next();
		this.exp = e;
	}
	public void execute(Robot robot){
		if(exp != null){
			int nwait = exp.evaluate(robot);
			for(int i = 1;i <nwait; i++){
				robot.idleWait();
			}
		}
		robot.idleWait();
	}
	public String toString(){
		String waitexp = "wait";
		if(exp != null){
			waitexp += ("(" + exp.toString() + ")");
		}
		return waitexp + ";";
	}

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