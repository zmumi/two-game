package two.game.logic;

import com.google.common.collect.ImmutableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import two.game.config.ControlPointConfig;
import two.game.config.GameConfig;
import two.game.model.ControlPoint;
import two.game.model.Point;
import two.game.model.constant.IGameMap;
import two.game.model.constant.MapParser;
import two.game.model.constant.UnitType;
import two.game.model.status.AttackEvent;
import two.game.model.status.MissileStatus;
import two.game.model.status.TeamStatus;
import two.game.model.status.UnitStatus;

import java.util.*;
import java.util.stream.Collectors;

/**
 * remember that object is shared and all actions should
 */
public class GameState {
    private static final Logger logger = LoggerFactory.getLogger(GameState.class);
    private final IGameMap map;
    private final Map<String, Long> userIdToSequenceId;
    private Boolean gameStarted;
    private List<MissileStatus> missileStatuses;
    private List<AttackEvent> attackEvents;
    private List<TeamStatus> teamStatuses;
    private List<UnitStatus> unitStatuses;
    private List<ControlPoint> controlPoints;
    private Long updateSequenceId;

    public GameState() {

        this(MapParser.parse(), new LinkedList<>(), new LinkedList<>(), new LinkedList<>(), new LinkedList<>(), ControlPointConfig.controlPointLocations);
        ControlPoint cp = new ControlPoint(new Point(224.0, 224.0));
        this.getTeamStatuses().add(new TeamStatus("Team A", 1000., new HashSet<>(Arrays.asList("user1")), new HashSet<>()));
        this.getTeamStatuses().add(new TeamStatus("Team B", 1000., new HashSet<>(Arrays.asList("user2")), new HashSet<>(Arrays.asList(cp))));
        this.getUnitStatuses().add(new UnitStatus(1L, UnitType.TANK, "user1", 80, 200, 2, 4, 2, new Point(96.0, 96.0),
                new Point(224.0, 64.0)));
        this.getUnitStatuses().add(new UnitStatus(2L, UnitType.TANK, "user2", 80, 200, 2, 4, 2, new Point(108.0, 108.0)
                , new Point(96.0, 128.0)));
    }

    public GameState(IGameMap map, List<MissileStatus> missileStatuses, List<AttackEvent> attackEvents,
                     List<TeamStatus> teamStatuses, List<UnitStatus> unitStatuses, List<ControlPoint> controlPoints) {
        this.map = map;
        this.missileStatuses = missileStatuses;
        this.attackEvents = attackEvents;
        this.teamStatuses = teamStatuses;
        this.unitStatuses = unitStatuses;
        this.controlPoints = controlPoints;
        this.gameStarted = false;
        this.userIdToSequenceId = new HashMap<>();
        this.updateSequenceId = 0L;
    }

    public Map<String, Long> getUserIdToSequenceId() {
        return userIdToSequenceId;
    }

    public IGameMap getMap() {
        return map;
    }

    public List<MissileStatus> getMissileStatuses() {
        return missileStatuses;
    }

    public void setMissileStatuses(List<MissileStatus> missileStatuses) {
        this.missileStatuses = missileStatuses;
    }

    public synchronized List<AttackEvent> getAttackEvents() {
        logger.info("Got Attacks: " + attackEvents.size());
        ImmutableList<AttackEvent> attacks = ImmutableList.copyOf(this.attackEvents);
        attackEvents.clear();
        return attacks;
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

    public List<ControlPoint> getControlPoints() {
        return controlPoints;
    }

    public void setControlPoints(List<ControlPoint> controlPoints) {
        this.controlPoints = controlPoints;
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


    public void addUnit(UnitType unitType, String user, Integer teamNumber) {
        Point startPoint = GameConfig.getStartPoint(teamNumber);
        long unitId = getUniqueUnitId();

        // todo: so many magic constants...
        this.getUnitStatuses().add(new UnitStatus(unitId, unitType, user, 100, 2, 2, 4, 2, startPoint, startPoint));
    }

    public void addUnit(UnitStatus status) {
        this.unitStatuses.add(status);
    }

    public synchronized void addAttack(AttackEvent attackEvent) {
        this.attackEvents.add(attackEvent);
        logger.info("Add Attacks: " + attackEvents.size());
    }

    public void addMissile(MissileStatus missileStatus) {
        this.missileStatuses.add(missileStatus);
    }

    private long getUniqueUnitId(){
        long LOWER_RANGE = 0;
        long UPPER_RANGE = 1000000;
        Random random = new Random();

        return LOWER_RANGE +
                (long)(random.nextDouble()*(UPPER_RANGE - LOWER_RANGE));
    }
}
