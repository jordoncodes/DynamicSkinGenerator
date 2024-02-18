package me.onlyjordon.changeskin.commands

import org.bukkit.Bukkit
import org.bukkit.command.CommandMap
import org.bukkit.command.SimpleCommandMap

class SimpleCommandManager : CommandManager {
    private var commands: MutableList<Command>? = null

    fun initialise() {
        commands = ArrayList()
    }

    override fun removeCommands() {
        var map: SimpleCommandMap? = null
        var knownCommands: MutableMap<String?, org.bukkit.command.Command?>? = null
        try {
            val cmdMap = Bukkit.getPluginManager().javaClass.getDeclaredField("commandMap")
            cmdMap.isAccessible = true
            map = cmdMap[Bukkit.getPluginManager()] as SimpleCommandMap
            val knownCmds = SimpleCommandMap::class.java.getDeclaredField("knownCommands")
            knownCmds.isAccessible = true
            knownCommands = knownCmds[map] as MutableMap<String?, org.bukkit.command.Command?>
            try {
                for (command in commands!!) {
                    command.unregister(map)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } catch (e: Error) {
                e.printStackTrace()
            }
            for (cmd in commands!!) {
                try {
                    knownCommands!!.remove(cmd.name)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            knownCmds[map] = knownCommands
        } catch (e: Exception) {
            e.printStackTrace()
        } catch (e: Error) {
            e.printStackTrace()
        }
    }

    override fun addCommand(command: Command) {
        commands!!.add(command)
    }

    override fun removeCommand(command: Command) {
        commands!!.remove(command)
    }

    override fun updateCommandMap() {
        removeCommands()
        var map: CommandMap? = null
        try {
            val cmdMap = Bukkit.getPluginManager().javaClass.getDeclaredField("commandMap")
            cmdMap.isAccessible = true
            map = cmdMap[Bukkit.getPluginManager()] as CommandMap
        } catch (e: Exception) {
            e.printStackTrace()
        }
        for (cmd in commands!!) {
            try {
                if (map == null) {
                    return
                }
                map.register(cmd.name, "core", cmd)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun disable() {
        removeCommands()
    }
}
