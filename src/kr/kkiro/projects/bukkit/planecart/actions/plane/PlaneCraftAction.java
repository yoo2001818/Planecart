package kr.kkiro.projects.bukkit.planecart.actions.plane;

import kr.kkiro.projects.bukkit.planecart.actions.Action;
import kr.kkiro.projects.bukkit.planecart.exceptions.InvaildBlockException;

import org.bukkit.block.Block;

public class PlaneCraftAction extends Action {

	private Block baseBlock;
	
	public PlaneCraftAction(Block baseBlock) {
		this.baseBlock = baseBlock;
	}

	@Override
	public void onExecute() throws InvaildBlockException {
		//TODO add type
		if(baseBlock.getTypeId() != 0) {
			throw new InvaildBlockException();
		}
	}

}
