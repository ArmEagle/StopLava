StopLava by ArmEagle
====================

This Minecraft, Bukkit plugin prevents players from placing lava blocks or
emptying/filling buckets. It supports Permissions using the node
*StopLava.canUseLava* or reverts back to only allowing use by Ops.

When cancelling a PlayerBucketFillEvent, or PlayerBucketEmptyEvent the client
is not notified of the event actually being cancelled. So the player will see
the bucket being filled, while it isn't really. As will be clear when the
player (dis)connects and gets the update.

For now this plugin is forcing a clumsy update to the client. Perhaps there
are better ways to do this, but that seems to require use of the CraftBukkit
API besides the Bukkit API.

I submitted [a feature request](http://leaky.bukkit.org/issues/896) to have
CraftBukkit send the client these updates automatically. 


Source
------
Source can be found at https://github.com/ArmEagle/StopLava

License
-------
This work is licensed under an Attribution-ShareAlike 3.0 Unported (CC BY-SA 3.0)
http://creativecommons.org/licenses/by-sa/3.0/
