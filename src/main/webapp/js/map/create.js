/**
 * Created by Piotr Proc on 30.11.15.
 */

var textFont = {
    font: '32 px Arial',
    fill: '#ff0044'
};

function create() {

    game.world.setBounds(0, 0, mapSize, mapSize);
    //  We're going to be using physics, so enable the Arcade Physics system
    game.physics.startSystem(Phaser.Physics.ARCADE);

    var map = game.add.sprite(0, 0, 'map'); //  A simple background for our game

    resourceText = game.add.text(0, 0, 'Zasoby: ' + resource, textFont);
    resourceText.fixedToCamera = true;
    resourceText.cameraOffset.setTo(viewSize - 3.75 * fieldSize, 0.25 * fieldSize);

    var button = game.add.button(viewSize - 1.45 * fieldSize, 0.25 * fieldSize,
        'change_player_button', changeCameraToOtherPlayer, this, 2, 1, 0);
    var button2 = game.add.button(viewSize - 0.75 * fieldSize, 0.25 * fieldSize,
        'reinforcement_button', sendSupportRequest, this, 2, 1, 0);
    button.fixedToCamera = true;
    button2.fixedToCamera = true;

    game.camera.follow(armySprites[followedUnitID]);

    game.input.keyboard.onDownCallback = function(e) {
        if(e.keyCode == 32){ //code for space key
            changeCameraToOtherPlayer();
        }
    };

//    game.input.keyboard.onDownCallback = function(e) {
//        if(e.keyCode == 13){ //code for enter key
//            alert(user);
//            if(user){
//                if(user == "user1")
//                    user = "user2";
//                else
//                    user = "user1";
//            }
//        }
//    };

    var controlPoint = game.add.sprite(7*fieldSize, 7*fieldSize, "control_point");
    var controlPoint2 = game.add.sprite(9*fieldSize, 9*fieldSize, "control_point");

    controlPoints.push(controlPoint);
    controlPoints.push(controlPoint2);
}

function changeCameraToOtherPlayer() {
    followedUnitID = (followedUnitID + 1) % myTeamList.length;
    game.camera.follow(myTeamList[followedUnitID]);
}
