package two.game.logic;

import two.game.model.constant.GameMap;
import two.game.model.constant.MapElement;
import two.game.model.status.AttackEvent;
import two.game.model.status.MissleStatus;
import two.game.model.status.TeamStatus;
import two.game.model.status.UnitStatus;

import java.util.*;

public class GameState {
    private final GameMap map;
    private final Map<String, Long> userIdToSequenceId;
    private Boolean gameStarted;
    private List<MissleStatus> missileStatuses;
    private List<AttackEvent> attackEvents;
    private List<TeamStatus> teamStatuses;
    private List<UnitStatus> unitStatuses;
    private Long updateSequenceId;

    public GameState() {
        this((x, y) -> MapElement.GROUND, new LinkedList<>(), new LinkedList<>(), new LinkedList<>(), new LinkedList<>());
        this.getTeamStatuses().add(new TeamStatus("Team A", 1000., new HashSet<>()));
        this.getTeamStatuses().add(new TeamStatus("Team B", 1000., new HashSet<>()));
    }

    public GameState(GameMap map, List<MissleStatus> missileStatuses, List<AttackEvent> attackEvents, List<TeamStatus> teamStatuses, List<UnitStatus> unitStatuses) {
        this.map = map;
        this.missileStatuses = missileStatuses;
        this.attackEvents = attackEvents;
        this.teamStatuses = teamStatuses;
        this.unitStatuses = unitStatuses;
        this.gameStarted = false;
        this.userIdToSequenceId = new HashMap<>();
        this.updateSequenceId = 0l;
    }

    public Map<String, Long> getUserIdToSequenceId() {
        return userIdToSequenceId;
    }

    public GameMap getMap() {
        return map;
    }

    public List<MissleStatus> getMissileStatuses() {
        return missileStatuses;
    }

    public void setMissileStatuses(List<MissleStatus> missileStatuses) {
        this.missileStatuses = missileStatuses;
    }

    public List<AttackEvent> getAttackEvents() {
        return attackEvents;
    }

    public void setAttackEvents(List<AttackEvent> attackEvents) {
        this.attackEvents = attackEvents;
    }

    public List<TeamStatus> getTeamStatuses() {
        return teamStatuses;
    }

    public void setTeamStatuses(List<TeamStatus> teamStatuses) {
        this.teamStatuses = teamStatuses;
    }

    public List<UnitStatus> getUnitStatuses() {
        return unitStatuses;
    }

    public void setUnitStatuses(List<UnitStatus> unitStatuses) {
        this.unitStatuses = unitStatuses;
    }

    public Boolean isStarted() {
        return gameStarted;
    }

    public void setStarted(Boolean gameStarted) {
        this.gameStarted = gameStarted;
    }

    public Long getUpdateSequenceId() {
        return updateSequenceId;
    }

    public void bumpUpdateSequenceId() {
        this.updateSequenceId += 1;
    }
}