package me.onlyjordon.changeskin.commands

import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.permissions.Permission

abstract class PlayerOnlyCommand : Command {
    constructor(name: String?) : super(name)

    constructor(name: String?, permission: String?) : super(name, permission)

    constructor(name: String?, permission: Permission) : super(name, permission)

    constructor(name: String?, permission: String?, aliases: List<String?>?) : super(name, permission, aliases)

    constructor(name: String?, permission: String?, aliases: Array<String?>?) : super(name, permission, aliases)

    protected abstract fun execute(player: Player?, args: Array<String>?): Boolean

    override fun execute(sender: CommandSender, command: org.bukkit.command.Command?, label: String?, args: Array<String>): Boolean {
        if (sender is Player) {
            val player = sender
            try {
                return execute(player, args)
            } catch (e: Exception) {
                player.sendMessage("There was an error doing this command. Please try again later.")
                throw RuntimeException("Error while running command /$name", e)
            }
        } else {
            sender.sendMessage("Only players can use this command!")
            return false
        }
    }
}
