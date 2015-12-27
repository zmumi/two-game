/**
 * Created by Piotr Proc on 27.12.15.
 */

function UnitData(id, name, type, team) {
    this.id = id;
    this.name = name;
    this.type = type;
    this.team = team;
}

function handleMapUpdate(message) {
    var unitStatuses = message.body.unitStatuses;

    unitStatuses.forEach(function (unit) {

        var unitData = handleUnitData(unit);
        var position = new Field(unit.position.x, unit.position.y);

        var destinationSprite = getUnitSprite(position);
        if (destinationSprite != null)
            return;

        var sprite = getUnitSpriteWithId(unitData.id);

        if (sprite) {
            moveUnitOnServerOrder(sprite, position);
        } else {
            createNewSprite(unitData, position);
        }

    });
}

function handleUnitData(unit) {
    var unitType = unit.type.toLowerCase();
    var unitTeam;

    if (teamA.indexOf(unit.user) >= 0)
        unitTeam = 1;
    else
        unitTeam = 2;

    var unitName = unitType + "_" + unitTeam;

    return new UnitData(unit.unitId, unitName, unitType, unitTeam);
}
