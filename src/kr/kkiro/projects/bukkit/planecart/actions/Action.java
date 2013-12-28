package kr.kkiro.projects.bukkit.planecart.actions;

public abstract class Action {

	private boolean doLoop = false;
	
	public abstract void onExecute() throws Exception;
	
	public void onLoop() throws Exception {
		
	}
	
	public boolean getLoop() {
		return doLoop;
	}
	
	public void setLoop(boolean loop) {
		doLoop = loop;
	}

}
