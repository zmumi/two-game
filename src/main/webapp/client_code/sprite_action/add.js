/**
 * Created by Piotr Proc on 27.12.15.
 */


function addNewSprite(unitData, position){
    var armySprite = game.add.sprite(position.x, position.y, unitData.name);

    var barConfig = {
        width: fieldSize * 0.9,
        height: fieldSize / 5,
        x: position.x + fieldSize/2, //center of the bar should be in field width center
        y: position.y + fieldSize    //bar should be below unit
    };

    armySprite.id = unitData.id;
    armySprite.type = unitData.type;
    armySprite.team = unitData.team;
    armySprite.healthBar = new HealthBar(game, barConfig);

    armySprite.healthBar.setPercent(80);

    if(myTeam == unitData.team){
        game.camera.follow(armySprite);
        myArmyPool.add(armySprite);
    }else{
        oppositeArmyPool.add(armySprite);
    }

}