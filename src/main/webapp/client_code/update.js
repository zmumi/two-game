/**
 * Created by Piotr Proc on 30.11.15.
 */

function update() {
    game.physics.arcade.overlap(bulletPool, oppositeArmyPool, bulletReachedTarget);

    if (game.input.mousePointer.isDown) {

        if (game.input.mousePointer.button == Phaser.Mouse.LEFT_BUTTON) {
            handleClick();
            game.input.activePointer.reset();
        }

    }
}

function bulletReachedTarget(bullet){
    bullet.kill();
}

function handleClick() {
    var originalPointedField = Helper.getOriginalShiftedField();
    var pointedSprite = Picker.getMovingUnitSprite(originalPointedField);

    //if we click on sprite
    if (pointedSprite) {

        //if we had one sprite marked before
        if (movingSprite && oppositeArmyPool.exists(pointedSprite)) {
            movingSprite.attack(pointedSprite);
        } else {
            if (myArmyPool.exists(pointedSprite))
                Helper.selectSprite(pointedSprite);
        }

    } else {

        //if we had one sprite marked before
        if (movingSprite) {
            //we move if
            if (myArmyPool.exists(movingSprite) && Helper.buttonsWereNotClicked()){
                var pointedField = Helper.getShiftedField();
                Sender.sendUserUpdate(pointedField, null, null);
            }

            Helper.deselectSprite();
        }

    }
}