StopLava by ArmEagle
====================

This Minecraft, Bukkit plugin prevents players from placing lava blocks or
emptying/filling buckets. It supports Permissions using the node
*StopLava.canUseLava* or reverts back to only allowing use by Ops.

When cancelling a PlayerBucketFillEvent, or PlayerBucketEmptyEvent the client
is not notified of the event actually being cancelled. So the player will see
the bucket being filled, while it isn't really. As will be clear when the
player (dis)connects and gets the update.

This is now 'fixed' by calling Player.updateInventory(). But that is a temporary
workaround by the developers of Bukkit.

Bug report created for this: https://bukkit.atlassian.net/browse/BUKKIT-1339 


Source
------
Source can be found at https://github.com/ArmEagle/StopLava

License
-------
This work is licensed under an Attribution-ShareAlike 3.0 Unported (CC BY-SA 3.0)
http://creativecommons.org/licenses/by-sa/3.0/
