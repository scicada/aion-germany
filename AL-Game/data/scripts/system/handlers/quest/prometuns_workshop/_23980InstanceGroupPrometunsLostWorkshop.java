/**
 * This file is part of Aion-Lightning <aion-lightning.org>.
 *
 *  Aion-Lightning is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Aion-Lightning is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details. *
 *  You should have received a copy of the GNU General Public License
 *  along with Aion-Lightning.
 *  If not, see <http://www.gnu.org/licenses/>.
 */
package quest.prometuns_workshop;

import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;

/**
 * @author QuestGenerator by Mariella
 */
public class _23980InstanceGroupPrometunsLostWorkshop extends QuestHandler {

	private final static int questId = 23980;
	private final static int[] mobs = { 650016, 650021, 650026 };

	public _23980InstanceGroupPrometunsLostWorkshop() {
		super(questId);
	}

	@Override
	public void register() {
		qe.registerQuestNpc(806818).addOnQuestStart(questId); // Stuck Rith
		qe.registerQuestNpc(806818).addOnTalkEvent(questId); // Stuck Rith
		qe.registerQuestNpc(653223).addOnQuestStart(questId); // Rim Ore Grinder
		qe.registerQuestNpc(653223).addOnTalkEvent(questId); // Rim Ore Grinder
		qe.registerQuestNpc(806845).addOnQuestStart(questId); // Curious Rith
		qe.registerQuestNpc(806845).addOnTalkEvent(questId); // Curious Rith
		qe.registerQuestNpc(836554).addOnQuestStart(questId); // Harun
		qe.registerQuestNpc(836554).addOnTalkEvent(questId); // Harun

		for (int mob : mobs) {
			qe.registerQuestNpc(mob).addOnKillEvent(questId);
		}
	}

	@Override
	public boolean onLvlUpEvent(QuestEnv env) {
		return defaultOnLvlUpEvent(env, 1000, true);
	}

	@Override
	public boolean onDialogEvent(QuestEnv env) {
		Player player = env.getPlayer();
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		DialogAction dialog = env.getDialog();
		int targetId = env.getTargetId();

		if (qs == null || qs.getStatus() == QuestStatus.NONE ) {
	  		if (targetId == 653223) {
				switch (dialog) {
					case QUEST_SELECT: {
						return sendQuestDialog(env, 4762);
					}
					case QUEST_ACCEPT_1:
					case QUEST_ACCEPT_SIMPLE: {
						return sendQuestStartDialog(env);
					}
					case QUEST_REFUSE_SIMPLE: {
						return closeDialogWindow(env);
					}
					default:
						break;
				}
			}
		}
		else if (qs.getStatus() == QuestStatus.START) {
			int var = qs.getQuestVarById(0);
			switch (targetId) {
				case 806818: {
					switch (dialog) {
						case QUEST_SELECT: {
							switch (var) {
								case 0: {
									return sendQuestDialog(env, 1011);	
								}
								case 1: {
									return sendQuestDialog(env, 1352);		
								}
							}
							
						}
						case SETPRO2: {
							qs.setQuestVar(2);
							updateQuestStatus(env);
							return closeDialogWindow(env);
						}
						case FINISH_DIALOG: {
							return sendQuestSelectionDialog(env);
						}
						default: 
							break;
					}
					break;
				}
				case 653223: {
					switch (dialog) {
						case QUEST_SELECT: {
							return sendQuestDialog(env, 1011);
						}
						case FINISH_DIALOG: {
							return sendQuestSelectionDialog(env);
						}
						default: 
							break;
					}
					break;
				}
				case 806845: {
					switch (dialog) {
						case QUEST_SELECT: {
							return sendQuestDialog(env, 4080);
						}
						case SET_SUCCEED: {
							qs.setQuestVar(10);
							qs.setStatus(QuestStatus.REWARD);
							updateQuestStatus(env);
							return closeDialogWindow(env);
						}
						default: 
							break;
					}
					break;
				}
				default:
					break;
			}
		} else if (qs.getStatus() == QuestStatus.REWARD) {
			if (targetId == 836554) {
				return sendQuestEndDialog(env);
			}
		}

		return false;
	}
	/*
	@Override
	public boolean onKillEvent(QuestEnv env) {
		Player player = env.getPlayer();
		QuestState qs = player.getQuestStateList().getQuestState(questId);

		if (qs != null && qs.getStatus() == QuestStatus.START) {
			int var = qs.getQuestVarById(0);
			int var1 = qs.getQuestVarById(1);

			// (0) Step: 3, Count: 1, Mobs : 650016
			// (1) Step: 6, Count: 1, Mobs : 650021
			// (2) Step: 8, Count: 1, Mobs : 650026

			switch (var) {
				case 3: {
					return defaultOnKillEvent(env, 650016, 3, 4, 0);
				}
				case 6: {
					return defaultOnKillEvent(env, 650021, 6, 7, 0);
				}
				case 8: {
					qs.setQuestVar(9);
					qs.setStatus(QuestStatus.REWARD);
					updateQuestStatus(env);
					return true;
				}
				default:
					break;
			}
			return false;
		}
		return false;
	}
	*/
}
