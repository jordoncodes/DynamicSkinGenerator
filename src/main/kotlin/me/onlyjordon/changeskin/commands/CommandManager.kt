package me.onlyjordon.changeskin.commands

interface CommandManager {
    fun updateCommandMap()

    fun removeCommands()

    fun addCommand(command: Command)

    fun removeCommand(command: Command)
}
