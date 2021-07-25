# CameraAPI
## What is this?
You can control player's camera on player's screen with this API
## How it works?(Working in progress)
## Example code
```java
public void work(Player player, Player player2) {
    
    CameraAPI cameraApi = CameraApiManager.getInstance();
    
    cameraApi.camera(player.getLocation().add(0, 5, 0), player);
    // With multi players
    //cameraApi.camera(player.getLocation().add(0, 5, 0), player, player2);
    
    // Reset camera
    cameraApi.reset(player, player2);
}
```