/**
 * Created by Piotr Proc on 01.12.15.
 */

function Picker(){

}

Picker.getUnitSprite = function (field) {
    return existsInGroups([myArmyPool, oppositeArmyPool], field, onTheSameField);
};

Picker.getUnitSpriteWithId = function (unitData) {
    return existsInGroups([myArmyPool, oppositeArmyPool], unitData, haveTheSameId);
};

Picker.getControlPoint = function (field){
    return existsInGroup(controlPointPool, field, onTheSameField);
};

function haveTheSameId(objectA, objectB){
    return objectA.id == objectB.id;
}

function onTheSameField(objectA, objectB){
    return objectA.x == objectB.x && objectA.y == objectB.y;
}