package me.onlyjordon.changeskin.commands

import org.bukkit.command.CommandSender
import org.bukkit.permissions.Permission

abstract class SimpleCommand : Command {
    constructor(name: String?) : super(name)

    constructor(name: String?, permission: String?) : super(name, permission)

    constructor(name: String?, permission: Permission) : super(name, permission)

    constructor(name: String?, permission: String?, aliases: List<String?>?) : super(name, permission, aliases)

    constructor(name: String?, permission: String?, aliases: Array<String?>?) : super(name, permission, aliases)

    override fun execute(sender: CommandSender, command: org.bukkit.command.Command?, label: String?, args: Array<String>): Boolean {
        return execute(sender, args)
    }

    protected abstract fun execute(sender: CommandSender?, args: Array<String>?): Boolean
}
